package com.iesfranciscodelosrios.security;

import com.iesfranciscodelosrios.security.filters.UserRegisterUuidFilter;
import com.iesfranciscodelosrios.security.filters.JwtAuthenticationFilter;
import com.iesfranciscodelosrios.security.filters.JwtAuthorizationFilter;
import com.iesfranciscodelosrios.security.jwt.JwtUtils;
import com.iesfranciscodelosrios.service.UserDetailServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableWebMvc
public class SecurityConfig {

    @Autowired
    UserDetailServiceImp userDetailService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    JwtAuthorizationFilter authorizationFilter;

    /**
     * Configures the security filter chain.
     *
     * @param httpSecurity The HttpSecurity object.
     * @param authenticationManager The AuthenticationManager object.
     * @return The SecurityFilterChain object.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(jwtUtils);
        jwtAuthenticationFilter.setAuthenticationManager(authenticationManager);

        return httpSecurity
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
//                    auth.requestMatchers("/institution/**").permitAll();
//                    auth.anyRequest().authenticated();
                    auth.anyRequest().permitAll();
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .addFilter(jwtAuthenticationFilter)
                .addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(userRegisterUuidFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    /**
     * Configures the CORS configuration source.
     *
     * @return The CorsConfigurationSource object.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH",
                "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type",
                "x-auth-token"));
        configuration.setExposedHeaders(Arrays.asList("x-auth-token"));
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    /**
     * Configures the password encoder.
     *
     * @return The PasswordEncoder object.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * Creates a UserRegisterUuidFilter object.
     *
     * @return The UserRegisterUuidFilter object.
     */
    private UserRegisterUuidFilter userRegisterUuidFilter() {
        return new UserRegisterUuidFilter(userDetailService);
    }

    /**
     * Configures the authentication manager.
     *
     * @param httpSecurity The HttpSecurity object.
     * @param passwordEncoder The PasswordEncoder object.
     * @return The AuthenticationManager object.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    AuthenticationManager authenticationManager(HttpSecurity httpSecurity, PasswordEncoder passwordEncoder) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder)
                .and().build();
    }

}
