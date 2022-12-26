package me.code.fulldemo.security;

import me.code.fulldemo.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            UserService userService,
            AuthenticationManager authManager
    ) throws Exception {
        return http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JWTLoginFilter(authManager))
                .addFilterAfter(new JWTVerifyFilter(userService), JWTLoginFilter.class)
                .authorizeRequests()
                .antMatchers("/test")
                .permitAll()
                .antMatchers("/user/register")
                .permitAll()
                .antMatchers(HttpMethod.PUT, "/product/")
                .hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/product/")
                .hasRole("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.applyPermitDefaultValues();
        configuration.addAllowedMethod("PUT");
        configuration.addAllowedMethod("DELETE");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedOrigin("http://localhost:3000");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
