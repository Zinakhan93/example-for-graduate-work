package ru.skypro.homework.dto;

import lombok.*;

//@Data
//@Getter
//@Setter
//@AllArgsConstructor // создает конструктор
//@NoArgsConstructor // создает пустой конструктор
//создание/обновление объявления:
public class CreateOrUpdateAd {
    private String title;       // Заголовок объявления
    private Integer price ;       // Цена
    private String description; // Описание

    public CreateOrUpdateAd(String title, Integer price, String description) {
        this.title = title;
        this.price = price;
        this.description = description;
    }

    public CreateOrUpdateAd() {
    }

    public String getTitle() {
        return title;
    }

    public Integer getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
