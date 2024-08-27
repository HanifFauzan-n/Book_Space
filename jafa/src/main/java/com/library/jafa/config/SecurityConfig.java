package com.library.jafa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.library.jafa.exception.CustomeAccesDeniedException;
import com.library.jafa.exception.CustomeUnAuthorizeException;
import com.library.jafa.security.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        PasswordEncoder getPasswordEncoded() {
                return new BCryptPasswordEncoder();
        }

        @Autowired
        JwtFilter jwtFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                http
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .exceptionHandling(ex -> ex.authenticationEntryPoint(new CustomeUnAuthorizeException())
                                                .accessDeniedHandler(new CustomeAccesDeniedException()))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/auth/**",
                                                                "/forgot-password/get-token",
                                                                "/forgot-password/reset",
                                                                "/v3/api-docs/**",
                                                                "/book/find-all-book",
                                                                "/swagger-ui/**")
                                                .permitAll()
                                                .requestMatchers("/officer/register-officer", "/officer/remove-officer")
                                                .hasAuthority("ADMIN")
                                                .requestMatchers("/member/update-member")
                                                .hasAnyAuthority("MEMBER", "OFFICER")
                                                .requestMatchers("/member/**", "/officer/**",
                                                                "/officer/upload-officer-photo/**", "/book/**","/officer/return-book/**","/officer/cetak_laporan/**","/officer/send/**")
                                                .hasAnyAuthority("ADMIN", "OFFICER")
                                                .anyRequest().authenticated())
                                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

}
