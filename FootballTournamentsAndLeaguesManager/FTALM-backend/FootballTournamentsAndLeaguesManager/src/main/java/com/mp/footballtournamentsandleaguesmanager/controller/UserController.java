package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.model.User;
import com.mp.footballtournamentsandleaguesmanager.service.AuthService;
import com.mp.footballtournamentsandleaguesmanager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> userLogin(@RequestBody User user){
        boolean isValid = authService.loginValidation(user.getUserName(), user.getEmailAddress(), user.getPassword());
        return isValid ? ResponseEntity.ok(true) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @GetMapping("/findUserByName/{userName}")
    public ResponseEntity<User> getUserByUserName(@PathVariable("userName") String userName){
        User user = userService.findUserByUserName(userName);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/findUserByEmail/{emailAddress}")
    public ResponseEntity<User> getUserByEmailAddress(@PathVariable("emailAddress") String emailAddress){
        User user = userService.findUserByEmailAddress(emailAddress);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/find/pass/{userName}")
    public String getPasswordByUserName(@PathVariable("userName") String userName){
        User user = userService.findPasswordByUserName(userName);
        return user.getPassword();
    }
}
