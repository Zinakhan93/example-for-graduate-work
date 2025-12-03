package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.skypro.homework.dto.Login;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.service.AuthService;

@Slf4j// Логирование
@CrossOrigin(value = "http://localhost:3000")// Разрешаем запросы с фронтенда
@RestController// Указываем что это REST контроллер
@RequiredArgsConstructor  // Lombok - автоматически создает конструктор с final полями
//аутентификация и регистрация:
public class AuthController {

    private final AuthService authService;// Внедряем сервис аутентификации

    @PostMapping("/login")
    @Operation(tags = {"Авторизация "}, summary = "авторизация  пользователя")
    public ResponseEntity<Void> login(@RequestBody Login login) {
        log.info("Login attempt for user: {}", login.getUsername());

        if (authService.login(login.getUsername(), login.getPassword())) {
            log.info("User {} logged in successfully", login.getUsername());
            return ResponseEntity.ok().build();
        } else {
            log.warn("Failed login attempt for user: {}", login.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @PostMapping("/register")
    @Operation(tags = {"Регистрация"}, summary = "Регистрация пользователя")
    public ResponseEntity<String> register(@RequestBody Register register) {
        if (authService.register(register)) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User registered successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User already exists");
        }
    }
}
