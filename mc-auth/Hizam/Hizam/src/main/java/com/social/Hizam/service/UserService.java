package com.social.Hizam.service;


import com.social.Hizam.model.User;
import com.social.Hizam.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private void saveUser(User user)
    {
        userRepository.save(user);
    }

    public void create(User user)
    {

        if(userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("This email already exists");
        }

        saveUser(user);
    }

    public User getByEmail(String username)
    {
        return userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    public UserDetailsService userDetailsService()
    {
       return this::getByEmail;
    }




}
