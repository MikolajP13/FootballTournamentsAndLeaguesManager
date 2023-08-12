package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.exception.UserNotFoundException;
import com.mp.footballtournamentsandleaguesmanager.model.User;
import com.mp.footballtournamentsandleaguesmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public User addUser(User user){
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setEnabled(true); //TODO only without email verification
        return userRepository.save(user);
    }

    public User findUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName).orElse(null);
                //orElseThrow(() -> new UserNotFoundException("User " + userName + " was not found"));
    }

    public User findUserByEmailAddress(String emailAddress){
        return userRepository.findUserByEmailAddress(emailAddress).orElse(null);
        //orElseThrow(() -> new UserNotFoundException("User " + emailAddress + " was not found"));
    }

    public User findPasswordByUserName(String userName){
        return userRepository.findPasswordByUserName(userName);
    }
}
