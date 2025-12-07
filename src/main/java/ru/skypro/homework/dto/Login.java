package ru.skypro.homework.dto;
import lombok.*;
/**
 * DTO ля аутентификации пользователя.
 * Используется для передачи учетных данных пользователя при входе в систему.
 * Содержит логин и пароль, необходимые для проверки подлинности пользователя.
*/
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Login {
    private String username;
    private String password;
}
/**
 * Конструктор с параметром и без создаются автоматически с помощью @AllArgsConstructor, @NoArgsConstructor
 * Геттеры и сеттеры создается автоматически @Getter, @Setter
 * @param username имя пользователя для аутентификации
 * @param password пароль пользователя для аутентификации
 */