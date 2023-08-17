package com.financialtransactionsapi.controllers;

import com.financialtransactionsapi.domain.user.User;
import com.financialtransactionsapi.dtos.UserDTO;
import com.financialtransactionsapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
public class UserController {
  @Autowired
  private UserService userService;
  @PostMapping
  public ResponseEntity<User> createUser(@RequestBody UserDTO data){
    User user = this.userService.create(data);
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }
  
  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    List<User> users = this.userService.getAll();
    return new ResponseEntity<>(users,HttpStatus.OK);
  }
}
