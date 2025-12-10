package ru.skypro.homework.dto;
import lombok.*;
/**
 * DTO для представления расширенной информации об объявлении.
 * Содержит полные данные об объявлении, включая контактную информацию автора.
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExtendedAd {
    private Integer pk ;
    private String authorFirstName;
    private String authorLastName ;
    private String description ;
    private String email ;
    private String image ;
    private String phone ;
    private Integer price ;
    private String title ;
}
/**
 * Конструктор c парамером и без создается автоматически @AllArgsConstructor, @NoArgsConstructor
 * Геттеры и сеттеры создается автоматически @Getter, @Setter
 * @param pk ID  объявления
 * @param authorFirstName имя автора объявления
 * @param authorLastName фамилия автора объявления
 * @param description подробное описание объявления
 * @param email email автора
 * @param image ссылка на изображение объявления
 * @param phone телефон автора
 * @param price цена товара/услуги
 * @param title заголовок объявления
 */