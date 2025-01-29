package com.jts.rest_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // InMemoryUserDetailsManager setup with two users
        UserDetails admin = User.withUsername("Amiya")
                .password(passwordEncoder().encode("123")) // Установите правильный пароль
                .roles("ADMIN", "USER") // Убедитесь, что роли указаны правильно
                .build();

        UserDetails user = User.withUsername("Ejaz")
                .password(passwordEncoder().encode("123")) // Убедитесь, что пароли совпадают
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/welcome").permitAll() // Доступно для всех
                        .requestMatchers("/auth/user/**").hasRole("USER") // Требуется роль USER
                        .requestMatchers("/auth/admin/**").hasRole("ADMIN") // Требуется роль ADMIN
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/auth/welcome", true) // Перенаправление по умолчанию
                        .successHandler((request, response, authentication) -> {
                            // Перенаправление в зависимости от роли
                            if (authentication.getAuthorities().stream()
                                    .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                                response.sendRedirect("/auth/admin/adminProfile");
                            } else {
                                response.sendRedirect("/auth/user/userProfile");
                            }
                        })
                )
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/auth/welcome")); // Настройка выхода

        return http.build();
    }

}
