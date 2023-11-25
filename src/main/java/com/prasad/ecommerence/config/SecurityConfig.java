package com.prasad.ecommerence.config;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;


import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

        // to throw exception to the exception handle from the jwtAuthfilter
        @Autowired
        @Qualifier("handlerExceptionResolver")
        private HandlerExceptionResolver handlerExceptionResolver;

      


        @Bean
        public JwtAthFilter jwtAthFilter() {
                return new JwtAthFilter(handlerExceptionResolver);
        }

 

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                
                

                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .cors(cors -> cors.configurationSource((new CorsConfigurationSource() {

                                        @Override
                                        @Nullable
                                        public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                                CorsConfiguration cfg = new CorsConfiguration();
                                                cfg.setAllowedOrigins(Arrays.asList(
                                                        "http://localhost:3000"  // to access the backend from the this endpoint other end points cannot be accesed
                                                        ));
                                                cfg.setAllowedMethods(java.util.Collections.singletonList("*"));
                                                cfg.setAllowCredentials(true);
                                                cfg.setAllowedHeaders(java.util.Collections.singletonList("*"));
                                                cfg.setExposedHeaders(Arrays.asList( "Authorization"));
                                                cfg.setMaxAge(3600L);
                                                return cfg;
                                        }

                                       

                                        
                                        
                                })))
                                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                                                .requestMatchers("/api/**").authenticated()
                                                .anyRequest().permitAll())
                                .sessionManagement(sessionManagement -> sessionManagement
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .addFilterBefore(jwtAthFilter(), UsernamePasswordAuthenticationFilter.class);
                return http.build();

        }

        
}
