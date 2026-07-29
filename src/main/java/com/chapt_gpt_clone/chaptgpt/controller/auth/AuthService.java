package com.chapt_gpt_clone.chaptgpt.controller.auth;

import com.chapt_gpt_clone.chaptgpt.Exceptions.*;
import com.chapt_gpt_clone.chaptgpt.config.EmailProducer;
import com.chapt_gpt_clone.chaptgpt.dtoMapper.*;
import com.chapt_gpt_clone.chaptgpt.entity.EmailVerification;
import com.chapt_gpt_clone.chaptgpt.entity.Users;
import com.chapt_gpt_clone.chaptgpt.enums.VerificationType;
import com.chapt_gpt_clone.chaptgpt.repository.RefreshTokenRepository;
import com.chapt_gpt_clone.chaptgpt.repository.UserRepository;
import com.chapt_gpt_clone.chaptgpt.security.JwtService;
import com.chapt_gpt_clone.chaptgpt.service.EmailService;
import com.chapt_gpt_clone.chaptgpt.service.RateLimitService;
import com.chapt_gpt_clone.chaptgpt.service.RefreshTokenService;
import com.chapt_gpt_clone.chaptgpt.utils.OtpGenerator;
import io.github.bucket4j.Bucket;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final EmailVerificationrepository emailVerificationrepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpGenerator otpGenerator;
    private final EmailService emailService;
    private final JwtService jwtService;
    private final EmailProducer emailProducer;
    private final RateLimitService rateLimitService;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public void signup(SignupRequest loginSignupRequest) throws EmailAlreadyExistsException {
        if (userRepository.existsByEmail(loginSignupRequest.email())) {
            throw new EmailAlreadyExistsException("Email already exists.");
        }

        emailVerificationrepository.deleteByEmail(loginSignupRequest.email());
        String otp = otpGenerator.generate();
        Users users = new Users(loginSignupRequest.first_name(), loginSignupRequest.last_name(), loginSignupRequest.email(), false);
        userRepository.save(users);
        emailProducer.publish(EmailMessage.builder()
                .to(loginSignupRequest.email())
                .template("Sign-up")
                .verificationType(VerificationType.SIGNUP)
                .variables(Map.of("firstName", loginSignupRequest.first_name(), "otp", otp))
                .build());
    }

    @Transactional
    public void login(LoginRequest loginRequest) throws UserDoesNotExistsException {
        if (!userRepository.existsByEmail(loginRequest.email())) {
            throw new UserDoesNotExistsException("USer does not exists, please signup.");
        }
        Bucket bucket =
                rateLimitService.resolveBucket(loginRequest.email());
        if (!bucket.tryConsume(1)) {
            throw new TooManyOtpRequestsException();
        }
        emailVerificationrepository.deleteByEmail(loginRequest.email());
        String otp = otpGenerator.generate();
        emailProducer.publish(EmailMessage.builder()
                .to(loginRequest.email())
                .template("login")
                .variables(Map.of("otp", otp))
                .verificationType(VerificationType.LOGIN)
                .build());
    }

    @Transactional
    public JwtResponse verifyEmailOtp(VerifySignupRequest verifySignupRequest) {
        EmailVerification emailVerification = emailVerificationrepository
                .findTopByEmailAndUsedFalseOrderByCreatedAtDesc(verifySignupRequest.email())
                .orElseThrow();
        System.out.println("emailVerification" + emailVerification.toString() + "fsfds   " + LocalDateTime.now() + " dsadsad " + emailVerification.getExpiresAt());
        if (emailVerification.isUsed()) {
            throw new OtpAlreadyUsedException("The otp is already used, please request for new otp.");
        }
        if (LocalDateTime.now().isAfter(emailVerification.getExpiresAt())) {
            throw new OtpExpiredException("The otp is expired, please request for new otp.");
        }
        if (!passwordEncoder.matches(verifySignupRequest.otp(), emailVerification.getOtp())) {
            throw new InvalidOtpException("The otp is invalid, please enter a valid otp.");
        }
        emailVerificationrepository.markOtpUsed(emailVerification.getEmail(), emailVerification.getOtp());
        userRepository.markEmailVerified(verifySignupRequest.email());
        Optional<Users> users = userRepository.findByEmail(verifySignupRequest.email());
        if (users.isPresent()) {
            String refreshToken = refreshTokenService.create(users.get());
            return new JwtResponse(jwtService.generateToken(users.get()), refreshToken, jwtService.getExpiration());
        }
        throw new UserDoesNotExistsException("USer does not exists, please signup.");
    }

}
