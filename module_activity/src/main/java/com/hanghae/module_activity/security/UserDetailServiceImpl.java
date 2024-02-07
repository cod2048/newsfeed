package com.hanghae.module_activity.security;

import com.hanghae.module_activity.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String userEmail) {
        return userRepository.findByEmail(userEmail)
                .map(UserDetailsImpl::from)
                .orElseThrow(() -> new IllegalArgumentException("no user"));
    }
}
