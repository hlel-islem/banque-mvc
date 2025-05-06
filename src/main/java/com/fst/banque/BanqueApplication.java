package com.fst.banque;

import com.fst.banque.entities.Role;
import com.fst.banque.entities.User;
import com.fst.banque.repositories.RoleRepository;
import com.fst.banque.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BanqueApplication {

    public static void main(String[] args) {
        SpringApplication.run(BanqueApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
        return args -> {
            if (userRepo.count() == 0) {

                
                Role adminRole = roleRepo.findByName("ROLE_ADMIN").orElseGet(() -> {
                    Role newRole = new Role(null, "ROLE_ADMIN");
                    return roleRepo.save(newRole);
                });

               
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin123")); 
                admin.getRoles().add(adminRole);

                userRepo.save(admin);

                System.out.println("Utilisateur admin créé : username = admin, password = admin123");
            }
        };
    }
}
