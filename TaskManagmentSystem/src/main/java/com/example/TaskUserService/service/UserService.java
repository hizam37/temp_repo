package com.example.TaskUserService.service;


import com.example.TaskUserService.model.User;
import com.example.TaskUserService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User createUser(User user)
    {
        return userRepository.save(user);
    }


    public User getByUserName(String username)
    {
        return userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    public UserDetailsService userDetailsService()
    {
        return this::getByUserName;
    }



}

