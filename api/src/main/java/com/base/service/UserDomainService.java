package com.base.service;

import com.base.entity.User;
import com.base.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDomainService {
    private final UserRepository userRepository;

    @Transactional
    public User getUser(long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(userRepository.save(new User(userId)));
    }
}
