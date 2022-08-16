package com.project.bm_internsip_grad_project.services;


import com.project.bm_internsip_grad_project.DTOs.RegistrationDTO;
import com.project.bm_internsip_grad_project.DTOs.TotalUser;
import com.project.bm_internsip_grad_project.entities.Role;
import com.project.bm_internsip_grad_project.entities.User;
import com.project.bm_internsip_grad_project.repositories.RoleRepo;
import com.project.bm_internsip_grad_project.repositories.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service @Slf4j @Transactional
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    RoleRepo roleRepo;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public User saveUser(RegistrationDTO registrationDTO)
    {
        log.info(registrationDTO.toString());
        if(registrationDTO.getEmail() == null || userRepo.existsByEmail(registrationDTO.getEmail()) ||
                registrationDTO.getPassword() == null || registrationDTO.getUsername() == null)
        {
            log.info("something wrong with user");
            return null;
        }

        log.info("saving user");
        User user = new User();
        modelMapper.map(registrationDTO, user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    public Role saveRole(Role role)
    {
        return roleRepo.save(role);
    }

    public void addRoleToUser(String username, String roleName)
    {
        log.info("adding role" + roleName + " to user " + username);
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(roleName);
        user.getRoles().add(role);
    }

    public User getUser(String email)
    {
        if(userRepo.existsByEmail(email))
            return userRepo.findByEmail(email);
        else
            return null;
    }

    public List<User> getUsers()
    {
        return userRepo.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = new User();
        try
        {
            user = userRepo.findByEmail(email);
        }
        catch (Exception e)
        {
            throw new UsernameNotFoundException("duplicate email");
        }
        if(user == null)
        {
            throw new UsernameNotFoundException("User not found");
        }
        log.info("user {} found", email);


        return new TotalUser(user.getEmail(),
                user.getPassword(), true,true,true,true,
                mapToGrantedAuthorities(user.getRoles()), user.getUsername(), user.getBalance());
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<Role> roles) {
        if(roles != null && !roles.isEmpty()) {
            return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        }
        return new ArrayList<GrantedAuthority>();
    }

}
