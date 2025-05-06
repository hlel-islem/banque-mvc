package com.fst.banque.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests(auth -> auth
                .requestMatchers("/login", "/register", "/css/**").permitAll() // Connexion, inscription et ressources statiques
                .requestMatchers("/comptes/**", "/ajouter/**", "/details/**").authenticated() // Pages pour utilisateurs authentifiés
                .requestMatchers("/depot/**", "/retrait/**").hasAuthority("ROLE_ADMIN") // Seulement l'admin peut déposer ou retirer
                .anyRequest().denyAll() // Autres pages interdites
            )
            .formLogin(form -> form
                .loginPage("/login") // Page de login personnalisée
                .defaultSuccessUrl("/comptes", true) // Redirection après connexion réussie
                .permitAll() // Permet l'accès à la page de connexion
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout") // Redirection après déconnexion
                .permitAll() // Permet l'accès à la déconnexion
            )
            .csrf().disable(); // Désactiver CSRF pour simplification (à revoir en production)

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Utilisation de BCrypt pour encoder les mots de passe
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // Gestionnaire d'authentification
    }
}

