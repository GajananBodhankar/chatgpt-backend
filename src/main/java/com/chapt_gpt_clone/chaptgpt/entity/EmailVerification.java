package com.chapt_gpt_clone.chaptgpt.entity;

import com.chapt_gpt_clone.chaptgpt.enums.VerificationType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailVerification extends  Auditable {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Please enter a valid email address")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "OTP cannot be blank")
    @Pattern(
            regexp = "^\\d{6}$",
            message = "OTP must be exactly 6 digits"
    )
    @Column(nullable = false)
    private String otp;

    @NotNull(message = "Verification type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VerificationType type;

    @NotNull(message = "Expiration time is required")
    @Future(message = "Expiration time must be in the future")
    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @Column(nullable = false)
    private boolean used = false;
}
