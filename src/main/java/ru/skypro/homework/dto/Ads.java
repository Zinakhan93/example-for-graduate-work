package ru.skypro.homework.dto;

import lombok.*;

import java.util.List;

/**
 * DTO (Data Transfer Object) для представления пагинированного списка объявлений.
 * Используется для передачи данных о списке объявлений между слоями приложения.
 * Содержит информацию об общем количестве объявлений и текущей странице результатов.
 */
@Data
@Getter
@Setter
@AllArgsConstructor // создает конструктор
@NoArgsConstructor // создает пустой конструктор
public class Ads {
    private Integer count = 0;
    private List<Ad> results = List.of();
}
/**
 * Конструктор со всеми параметрами.
 * Создаётся автоматически с помощью аннотации {@link AllArgsConstructor}.
 *
 * @param count общее количество объявлений
 * @param results список объявлений для текущей страницы
 * <p>
 * Конструктор без параметров.
 * Создаётся автоматически с помощью аннотации {@link NoArgsConstructor}.
 * Инициализирует поля значениями по умолчанию.
 */