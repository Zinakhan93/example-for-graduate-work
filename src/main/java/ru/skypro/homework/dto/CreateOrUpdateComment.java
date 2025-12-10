package ru.skypro.homework.dto;

import lombok.*;
/**
 * DTO для создания или обновления комментария.
 * Содержит только текст комментария - единственное поле, которое пользователь может изменить.
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateComment {
        private String text ;
}
/**
 * Конструктор с параметром текста комментария.
 * Создаётся автоматически с помощью аннотации {@link AllArgsConstructor}.
 * @param text текст комментария
 * Конструктор без параметров.
 * Создаётся автоматически с помощью аннотации {@link NoArgsConstructor}.
 * Геттер и сеттер генерируется автоматически Lombok.*/

