package com.tpe.controller;

import com.tpe.dto.UserRequest;
import com.tpe.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @RequestMapping("/register") // http://localhost:8080/register
    public ResponseEntity<String> register(@Valid @RequestBody UserRequest userRequest){
        userService.save(userRequest);
        String message = "User has created successfully";
        return new ResponseEntity(message, HttpStatus.CREATED);
    }

}
