package ru.skypro.homework.dto;

import lombok.*;
/**
 * DTO для смены пароля пользователя.
 * Используется для передачи данных при изменении пароля пользователя в системе.
 * Содержит текущий пароль для верификации и новый пароль для установки.
 * @see Login
 * @see Register
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewPassword {
    private String currentPassword ;
    private String newPassword ;
}
/**
 * Конструктор с параметром и без создаются автоматически с помощью @AllArgsConstructor, @NoArgsConstructor
 * Геттеры и сеттеры создается автоматически @Getter, @Setter
 * @param currentPassword текущий пароль пользователя
 * @param newPassword новый пароль пользователя
 */