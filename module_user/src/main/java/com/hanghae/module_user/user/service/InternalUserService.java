package com.hanghae.module_user.user.service;

import com.hanghae.module_user.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class InternalUserService {
    private final UserRepository userRepository;


    public InternalUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean checkUserExists(Long principalId) {
        return userRepository.existsById(principalId);
    }
}
