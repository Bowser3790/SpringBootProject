package com.tpe.security.service;//package com.tpe.security.service;
//
//import com.tpe.domain.User;
//import com.tpe.exception.ResourceNotFoundException;
//import com.tpe.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//
import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.exception.ResourceNotFoundException;
import com.tpe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.nio.file.ReadOnlyFileSystemException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UserDetailServiceImp  implements UserDetailsService {
    /*
    In this class we are going to convert:
    1. user entity to UserDetail
    2. Role entity to granted Authority
     */

    @Autowired
    private UserRepository userRepository;
    @Override // full explanation at 1:46:00 on video 7 security intro for Spring MVC
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { // UserDetails
        User foundUser = userRepository.findByUserName(username).orElseThrow(
                ()-> new ResourceNotFoundException("user not found with name: " + username)
        );
        if(foundUser!=null){
            return new org.
                    springframework.
                    security.
                    core.
                    userdetails.
                    User(
                    foundUser.getUserName(),
                    foundUser.getPassword(),
                    buildGrantedAuthorities(foundUser.getRoles())
            );
        }else {
            throw new UsernameNotFoundException("user not found with name: " + username);
        }
    }
    private static List<SimpleGrantedAuthority> buildGrantedAuthorities(final Set<Role> roles){
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(Role role: roles){
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }
        return authorities;
    }
}
