package com.social.Hizam.config;

import com.social.Hizam.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityFilterConfiguration {
    private final AuthenticationFilter authenticationFilter;
    private final UserService userService;

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception
   {
       httpSecurity.csrf(AbstractHttpConfigurer::disable)
               .cors(cors->cors.configurationSource(request -> {
                   var corsConfig = new CorsConfiguration();
                   corsConfig.setAllowedOriginPatterns(List.of("*"));
                   corsConfig.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTION"));
                   corsConfig.setAllowedHeaders(List.of("*"));
                   corsConfig.setAllowCredentials(true);
                   return corsConfig;
               }))

               .authorizeHttpRequests(request->request.
                       requestMatchers("/auth/**")
                       .permitAll()
                       .requestMatchers("/swagger-ui","/swagger-resources/*","/v3/api-docs/**")
                       .permitAll()
                       .requestMatchers("/end-point","/admin/**").hasRole("ADMIN")
                       .anyRequest().authenticated())
               .sessionManagement(manager->manager.sessionCreationPolicy(STATELESS))
               .authenticationProvider(authenticationProvider()).addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);
       return httpSecurity.build();
   }


   @Bean
   public AuthenticationProvider authenticationProvider()
   {
       DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();
       daoAuthProvider.setUserDetailsService(userService.userDetailsService());
       daoAuthProvider.setPasswordEncoder(passwordEncoder());
       return daoAuthProvider;
   }


   @Bean
   public PasswordEncoder passwordEncoder()
   {
       return new BCryptPasswordEncoder();
   }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception
    {
        return config.getAuthenticationManager();
    }
}
