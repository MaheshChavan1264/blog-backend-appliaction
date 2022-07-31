package com.blog.blogbackend.controller;

import com.blog.blogbackend.payloads.ApiResponse;
import com.blog.blogbackend.payloads.UserDto;
import com.blog.blogbackend.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    //GET - get all users
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> getAllUsers = this.userServiceImpl.getAllUsers();
        return new ResponseEntity<>(getAllUsers, HttpStatus.OK);
    }

    //Post create user
    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createUserDto = this.userServiceImpl.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    //PUT - update user
    @PutMapping("/users/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable(name = "userId") int userId) {
        UserDto updatedUser = this.userServiceImpl.updateUser(userDto, userId);
        return ResponseEntity.ok(updatedUser);
    }

    //ADMIN
    //DELETE - user
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable(name = "userId") int userId) {
        this.userServiceImpl.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
    }

    //GET - get user by id
    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "userId") int userId) {
        return ResponseEntity.ok(this.userServiceImpl.getUserById(userId));
    }
}
