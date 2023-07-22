package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.model.User;
import com.mp.footballtournamentsandleaguesmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean loginValidation(String userName, String password){
        Optional<User> userToAuth = userRepository.findUserByUserName(userName);

        if(userToAuth != null && userToAuth.isPresent()
                && userToAuth.get().getUserName().equals(userName)
                && passwordEncoder.matches(password, userToAuth.get().getPassword())){
            return true;
        }else {
            System.out.println("Invalid username/password");
            return false;
        }
    }
}
