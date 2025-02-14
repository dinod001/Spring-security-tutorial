package com.example.security.Service;

import com.example.security.entity.UserEntity;
import com.example.security.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntityData =userRepository.findByUsername(username);
        System.out.println("user details service accessed");
        if(userEntityData==null) throw new UsernameNotFoundException(username);
        UserDetails user=User.builder()
                .username(userEntityData.getUsername())
                .password(userEntityData.getPassword())
                .build();
        return user;
    }
}
