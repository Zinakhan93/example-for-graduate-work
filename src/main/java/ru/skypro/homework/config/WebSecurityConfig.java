package ru.skypro.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.skypro.homework.dto.Role;

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
                .cors() // Включаем CORS для кросс-доменных запросов
                .and()
                .httpBasic(withDefaults()); // Используем Basic аутентификацию
        return http.build();
    }




    @Bean
    public PasswordEncoder passwordEncoder() {
        // Используем BCrypt для безопасного хранения паролей
        return new BCryptPasswordEncoder();
    }

}
