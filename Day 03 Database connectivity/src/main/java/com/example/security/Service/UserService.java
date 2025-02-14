package com.example.security.Service;

import com.example.security.entity.UserEntity;
import com.example.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity save(UserEntity userEntity) {
        UserEntity userEntityData =new UserEntity(
                userEntity.getUsername(), passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntityData);

    }
}
