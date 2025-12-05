package ru.skypro.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import javax.sql.DataSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
// Включаем поддержку аннотаций для проверки прав доступа на уровне методов
 @EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login",
            "/register",
            "/uploads/**",
//            "/ads", добавили для публичного доступа к объявлениям
//            "/ads/**" Добавили для публичного доступа к конкретным объявлениям
    };

    // Заменяем InMemoryUserDetailsManager на JdbcUserDetailsManager для хранения пользователей в БД
    @Bean
    public UserDetailsManager userDetailsService(DataSource dataSource) {
        // Создаем JdbcUserDetailsManager, который будет работать с нашей БД PostgreSQL
        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

        // Настраиваем SQL запросы для работы с пользователями
        // Запрос для поиска пользователя по имени (email)
        userDetailsManager.setUsersByUsernameQuery(
                "SELECT email, password, enabled FROM users WHERE email = ?");

        // Запрос для получения ролей пользователя
        userDetailsManager.setAuthoritiesByUsernameQuery(
                "SELECT email, role FROM users WHERE email = ?");

        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable() // Отключаем CSRF защиту, так как используем REST API
                .authorizeHttpRequests(
                        authorization ->
                                authorization
                                        .mvcMatchers(AUTH_WHITELIST) // URL из белого списка
                                        .permitAll() // Разрешаем доступ без аутентификации
                                        .mvcMatchers("/ads/**", "/users/**") // Все остальные URL
                                        .authenticated()) // Требуют аутентификации
                .cors() .configurationSource(corsConfigurationSource())// Включаем CORS для кросс-доменных запросов
                .and()
                .httpBasic(withDefaults()); // Используем Basic аутентификацию
        return http.build();
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Разрешаем оба протокола (http и https)
        configuration.setAllowedOrigins(Arrays.asList(
                "http://localhost:3000",
                "http://localhost:8080",
                "https://localhost:8080",
                "http://localhost",
                "https://localhost"
        ));

        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }



    @Bean
    public PasswordEncoder passwordEncoder() {
        // Используем BCrypt для безопасного хранения паролей
        return new BCryptPasswordEncoder();
    }

}
