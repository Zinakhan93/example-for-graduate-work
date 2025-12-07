package ru.skypro.homework.dto;
import lombok.*;
/**
 * DTO для представления комментария к объявлению.
 * Содержит информацию о комментарии, включая данные об авторе, содержании и времени создания.
 * Класс используется для передачи данных о комментариях между слоями приложения,
 * например, между контроллером и клиентом.
*/
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer author;
    private String authorImage;
    private String authorFirstName;
    private Long createdAt;
    private Integer pk;
    private String text;
}
/**
 * Конструктор со всеми параметрами.
 * Создаётся автоматически с помощью аннотации {@link AllArgsConstructor}.
 * Конструктор без параметров.
 * Создаётся автоматически с помощью аннотации {@link NoArgsConstructor}.
 * Инициализирует поля значениями по умолчанию (null для объектов, 0 для примитивов).
 * Геттер и сетер генерируется автоматически Lombok
 *
 * @param author          уникальный идентификатор автора комментария
 * @param authorImage     ссылка на аватар автора комментария
 * @param authorFirstName имя автора комментария
 * @param createdAt       дата создания комментария в миллисекундах
 * @param pk              уникальный идентификатор комментария
 * @param text            текст комментария
 * Дата и время создания комментария в миллисекундах.
 * Используется стандартная эпоха Unix (1 января 1970 года).
 * Может быть преобразовано в объект {@link java.util.Date} или {@link java.time.Instant}.
 */