package com.project.bm_internsip_grad_project.controllers;


import com.project.bm_internsip_grad_project.DTOs.LoginDTO;
import com.project.bm_internsip_grad_project.DTOs.RegistrationDTO;
import com.project.bm_internsip_grad_project.DTOs.RoleToUserDTO;
import com.project.bm_internsip_grad_project.entities.Role;
import com.project.bm_internsip_grad_project.entities.User;
import com.project.bm_internsip_grad_project.repositories.RoleRepo;
import com.project.bm_internsip_grad_project.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegistrationDTO registrationDTO)
    {
        User user = userService.saveUser(registrationDTO);
        if(user == null)
            return ResponseEntity.badRequest().body("Could not register user");
//        userService.addRoleToUser(registrationDTO.getUsername(), "User");

        return ResponseEntity.ok("User registered successfully");
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers()
    {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role>saveRole(@RequestBody Role role)
    {
        return ResponseEntity.ok().body(userService.saveRole(role));
    }

    @PostMapping("/role/addToUser")
    public ResponseEntity<?>saveRole(@RequestBody RoleToUserDTO roleToUserDTO)
    {
        userService.addRoleToUser(roleToUserDTO.getUserName(), roleToUserDTO.getRoleName());
        return ResponseEntity.ok().build();
    }

}
