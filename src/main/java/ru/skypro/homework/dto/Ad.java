package ru.skypro.homework.dto;

import lombok.*;

//@Data
//@Getter
//@Setter
//@AllArgsConstructor // создает конструктор
//@NoArgsConstructor // создает пустой конструктор
//базовая информация об объявлении
public class Ad {
    private Integer author; // ID автора
    private String image ;  // ссылка на картинку объявления
    private Integer pk ;     // ID объявления
    private Integer price;  // Цена объявления
    private String title ;  // Заголовок объявления

    public Ad(Integer author, String image, Integer pk, Integer price, String title) {
        this.author = author;
        this.image = image;
        this.pk = pk;
        this.price = price;
        this.title = title;
    }

    public Integer getAuthor() {
        return author;
    }

    public String getImage() {
        return image;
    }

    public Integer getPk() {
        return pk;
    }

    public Integer getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public Ad() {

    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
