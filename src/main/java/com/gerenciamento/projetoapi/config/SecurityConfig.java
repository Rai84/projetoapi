package com.gerenciamento.projetoapi.config;

import org.springframework.context.annotation.*;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.gerenciamento.projetoapi.service.details.UsuarioDetailsService;
import java.util.List;

@Configuration
public class SecurityConfig {

        @Bean
        public SecurityFilterChain userSecurityFilterChain(HttpSecurity http,
                        AuthenticationManager authenticationManager) throws Exception {

                http
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/js/**", "/css/**", "/img/**", "/webjars/**",
                                                                "/h2-console/**", "/login", "/logout", "/fragments/**")
                                                .permitAll()
                                                .requestMatchers(HttpMethod.GET, "/projetos/listar", "/projetos/novo",
                                                                "/projetos/editar/**")
                                                .authenticated()
                                                .anyRequest().authenticated())
                                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
                                .headers(headers -> headers.frameOptions().sameOrigin())
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .loginProcessingUrl("/login")
                                                .defaultSuccessUrl("/", true)
                                                .failureUrl("/login?error=true")
                                                .permitAll())
                                .logout(logout -> logout
                                                .logoutUrl("/logout")
                                                .logoutSuccessUrl("/login?logout")
                                                .permitAll())
                                .sessionManagement(session -> session.sessionFixation().newSession())
                                .authenticationManager(authenticationManager);

                return http.build();
        }

        @Bean
        public DaoAuthenticationProvider userAuthenticationProvider(UsuarioDetailsService userService,
                        PasswordEncoder encoder) {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setUserDetailsService(userService);
                provider.setPasswordEncoder(encoder);
                return provider;
        }

        @Bean
        public UserDetailsService inMemoryUserDetailsService(PasswordEncoder encoder) {
                UserDetails user = User.withUsername("admin")
                                .password(encoder.encode("123"))
                                .roles("ADMIN")
                                .build();
                return new InMemoryUserDetailsManager(user);
        }

        @Bean
        public AuthenticationManager authenticationManager(
                        DaoAuthenticationProvider daoProvider,
                        UserDetailsService inMemoryUserDetailsService,
                        PasswordEncoder encoder) {

                DaoAuthenticationProvider inMemoryProvider = new DaoAuthenticationProvider();
                inMemoryProvider.setUserDetailsService(inMemoryUserDetailsService);
                inMemoryProvider.setPasswordEncoder(encoder);

                return new ProviderManager(List.of(inMemoryProvider, daoProvider));
        }
}
