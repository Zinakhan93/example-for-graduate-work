package ru.skypro.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import ru.skypro.homework.dto.Role;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
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

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        // Создаем тестового пользователя в памяти приложения
        UserDetails user =
                User.builder()
                        .username("user@gmail.com")
                        .password("password")
                        .passwordEncoder(passwordEncoder::encode)// Кодируем пароль
                        .roles(Role.USER.name())// Назначаем роль
                        .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .authorizeHttpRequests(
                        authorization ->
                                authorization
                                        .mvcMatchers(AUTH_WHITELIST)// Разрешаем доступ без аутентификации
                                        .permitAll()
                                        .mvcMatchers("/ads/**", "/users/**")// Требуем аутентификацию
                                        .authenticated())
                .cors()
                .and()
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
