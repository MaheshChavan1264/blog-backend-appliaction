package com.blog.blogbackend.services.impl;

import com.blog.blogbackend.entities.Role;
import com.blog.blogbackend.entities.User;
import com.blog.blogbackend.exceptions.ResourceNotFoundException;
import com.blog.blogbackend.payloads.UserDto;
import com.blog.blogbackend.repositories.RoleRepository;
import com.blog.blogbackend.repositories.UserRepository;
import com.blog.blogbackend.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.blog.blogbackend.config.AppConstants.ADMIN_USER;
import static com.blog.blogbackend.config.AppConstants.NORMAL_USER;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelmapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        //encoded the password
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        //roles
        Role role = this.roleRepository.findById(ADMIN_USER).get();
        user.getRoles().add(role);
        User newUser = this.userRepository.save(user);
        return this.userToDto(newUser);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        Role role = this.roleRepository.findById(NORMAL_USER).get();
        user.getRoles().add(role);
        User savedUser = this.userRepository.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id: ", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
        user.setAbout(userDto.getAbout());
        this.userRepository.save(user);
        return this.userToDto(user);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id: ", userId));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = this.userRepository.findAll();
        return users.stream().map(this::userToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id: ", userId));
        this.userRepository.delete(user);

    }

    private User dtoToUser(UserDto userDto) {
        return this.modelmapper.map(userDto, User.class);

    }

    public UserDto userToDto(User user) {

        return this.modelmapper.map(user, UserDto.class);

    }
}
