package ru.skypro.homework.dto;
import lombok.*;
/**
 * DTO для регистрации нового пользователя.
 * Используется для передачи данных при создании новой учетной записи пользователя.
 * Содержит всю необходимую информацию для регистрации в системе.
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Register {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}
/**
 * Конструктор с параметром и без создаются автоматически с помощью @AllArgsConstructor, @NoArgsConstructor
 * Геттеры и сеттеры создается автоматически @Getter, @Setter
 * @param username имя пользователя (логин)
 * @param password пароль пользователя
 * @param firstName имя пользователя
 * @param lastName фамилия пользователя
 * @param phone номер телефона
 * @param role роль пользователя в системе
 */