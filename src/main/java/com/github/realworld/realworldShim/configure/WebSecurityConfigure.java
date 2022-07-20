package com.github.realworld.realworldShim.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import static org.springframework.http.HttpMethod.POST;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigure implements WebMvcConfigurer {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf()
                .disable()
            .cors()
                .configurationSource(corsConfigurationSource())
                .and()
            .formLogin()
                .disable()

            .authorizeRequests()
                .antMatchers(POST,"/api/users")
                    .permitAll()
                .antMatchers("/", "/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/api-docs")
                    .permitAll()
                .antMatchers("/h2-console/**")
                    .permitAll()
                .anyRequest().authenticated();

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/swagger-resources", "/webjars/**", "/h2-console/**");
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.setAllowedOrigins(List.of("http://localhost:8080", "https://editor.swagger.io"));
        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        cors.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }
}
