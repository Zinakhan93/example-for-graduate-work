package ru.skypro.homework.dto;

import lombok.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Getter
@Setter
@AllArgsConstructor // создает конструктор
@NoArgsConstructor // пустой конструктор
public class UpdateUser {
    @Size(min = 2, max = 16, message = "Имя должно быть от 2 до 16 символов")
    private String firstName; // Новое имя
    @Size(min = 2, max = 16, message = "Фамилия должна быть от 2 до 16 символов")
    private String lastName;  // Новая фамилия
    @Pattern(regexp = "^\\+7 \\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}$",
            message = "Телефон должен быть в формате +7 (XXX) XXX-XX-XX")

    private String phone ;// Новый телефон

}
