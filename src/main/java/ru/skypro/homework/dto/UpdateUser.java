package ru.skypro.homework.dto;
import lombok.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
/**
 * DTO для обновления данных пользователя.
 * Используется для передачи данных при редактировании профиля пользователя.
 * Содержит поля, которые пользователь может изменить в своем профиле.
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUser {
    @Size(min = 2, max = 16, message = "Имя должно быть от 2 до 16 символов")
    private String firstName;
    @Size(min = 2, max = 16, message = "Фамилия должна быть от 2 до 16 символов")
    private String lastName;  // Новая фамилия
    @Pattern(regexp = "^\\+7 \\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}$",
            message = "Телефон должен быть в формате +7 (XXX) XXX-XX-XX")

    private String phone ;

}
/**
 * Конструктор с параметром и без создаются автоматически с помощью @AllArgsConstructor, @NoArgsConstructor
 * Геттеры и сеттеры создается автоматически @Getter, @Setter
 * @param firstName новое имя пользователя
 * @param lastName новая фамилия пользователя
 * @param phone новый номер телефона пользователя
 */