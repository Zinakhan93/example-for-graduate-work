package ru.skypro.homework.dto;

/**
 * DTO для создания или обновления объявления.
 * Используется для приема данных от клиента при создании нового объявления
 * или обновлении существующего.
 * <p>
 * Содержит только те поля, которые могут быть изменены пользователем
 * при создании или редактировании объявления.*/

public class CreateOrUpdateAd {
    private String title;
    private Integer price ;
    private String description;

    /**
     * Конструктор со всеми параметрами для создания объекта с заполненными полями.
     *
     * @param title заголовок объявления
     * @param price цена товара/услуги в объявлении
     * @param description подробное описание объявления
     */
    public CreateOrUpdateAd(String title, Integer price, String description) {
        this.title = title;
        this.price = price;
        this.description = description;
    }
    /**
     * Конструктор без параметров.
     * Создает пустой объект, который может быть заполнен через сеттеры.
     * Геттеры и сеттеры прописаны
     */
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
