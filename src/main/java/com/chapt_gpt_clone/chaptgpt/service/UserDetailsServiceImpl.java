package com.chapt_gpt_clone.chaptgpt.service;

import com.chapt_gpt_clone.chaptgpt.entity.Users;
import com.chapt_gpt_clone.chaptgpt.repository.UserRepository;
import com.chapt_gpt_clone.chaptgpt.security.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository
            userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users users
                = userRepository.findByEmail(email).orElseThrow();
        return new UserPrincipal(users);
    }
}
