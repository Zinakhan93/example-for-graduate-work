package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users") // Базовый путь для всех методов контроллера
@Tag(name = "Пользователи", description = "API для управления пользователями")
public class UserController {

    private final UserService userService;

    @PostMapping("/set_password")
    @Operation(summary = "Обновление пароля")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword newPassword) {
        try {
            userService.setPassword(newPassword);
            return ResponseEntity.ok().build();  // 200 OK
        } catch (IllegalArgumentException e) {
            // Если пароль неверный - 403 Forbidden
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping("/me")// GET запрос для получения информации о текущем пользователе
    @Operation (summary = "Получение информации об авторизованном пользователе")
    public ResponseEntity<User> getUser() {
        // Получаем данные текущего пользователя через сервис
        User user = userService.getCurrentUser();
        return ResponseEntity.ok(user);  // 200 OK с данными пользователя
    }

    @PatchMapping("/me") // PATCH для частичного обновления
    @Operation (summary = "Обновление информации об авторизованном пользователе" )
    public ResponseEntity<UpdateUser> updateUser(@RequestBody UpdateUser updateUser) {
        // Обновляем пользователя и возвращаем обновленные данные
        UpdateUser updatedUser = userService.updateUser(updateUser);
        return ResponseEntity.ok(updatedUser);
    }

    @PatchMapping(value = "/me/image", consumes = "multipart/form-data")
    @Operation (summary = "Обновление аватара авторизованного пользователя")
    public ResponseEntity<?> updateUserImage(@RequestParam("image") MultipartFile image) {
        try {
            userService.updateUserImage(image);
            return ResponseEntity.ok().build();  // 200 OK
        } catch (IOException e) {
            // Ошибка при работе с файлом - 500 Internal Server Error
            return ResponseEntity.status(500).build();
        }
    }

}
