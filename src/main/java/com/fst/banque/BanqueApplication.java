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

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BanqueApplication {

    private final RoleRepository roleRepository;

    public BanqueApplication(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(BanqueApplication.class, args);
    }

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepo, RoleRepository roleRepo, PasswordEncoder encoder) {
        return args -> {
            // Créer les rôles s'ils n'existent pas
            Role roleUser = roleRepo.findByName("ROLE_USER").orElseGet(() ->
                roleRepo.save(new Role(null, "ROLE_USER"))
            );

            Role roleAdmin = roleRepo.findByName("ROLE_ADMIN").orElseGet(() ->
                roleRepo.save(new Role(null, "ROLE_ADMIN"))
            );

            // Créer l'utilisateur "admin"
            if (userRepo.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(encoder.encode("admin123"));
                admin.setRoles(Set.of(roleAdmin));
                userRepo.save(admin);
            }

            // Créer l'utilisateur "user"
            if (userRepo.findByUsername("user").isEmpty()) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(encoder.encode("user123"));
                user.setRoles(Set.of(roleUser));
                userRepo.save(user);
            }

            System.out.println("✅ Utilisateur admin (admin/admin123)");
            System.out.println("✅ Utilisateur user (user/user123)");
        };
    }
}


