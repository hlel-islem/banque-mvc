package com.fst.banque.services;

import com.fst.banque.entities.User;
import com.fst.banque.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),         
                user.getPassword(),         
                user.getRoles().stream()    
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))  
                    .collect(Collectors.toList())  
        );
    }
}
