package ru.skypro.homework.dto;

/**
 * DTO (Data Transfer Object) для представления объявления.
 * Используется для передачи данных об объявлении между слоями приложения.
 */

public class Ad {
    private Integer author; // ID автора
    private String image;  // ссылка на картинку объявления
    private Integer pk;     // ID объявления
    private Integer price;  // Цена объявления
    private String title;  // Заголовок объявления

    /**
     * Конструктор с параметрами для создания объявления со всеми полями.
     *
     * @param author ID автора объявления
     * @param image  ссылка на изображение объявления
     * @param pk     уникальный идентификатор объявления
     * @param price  цена товара/услуги в объявлении
     * @param title  заголовок объявления
     */
    public Ad(Integer author, String image, Integer pk, Integer price, String title) {
        this.author = author;
        this.image = image;
        this.pk = pk;
        this.price = price;
        this.title = title;
    }

    /**
     * Конструктор без параметров для создания пустого объекта объявления.
     */
    public Ad() {

    }

    /**
     * Возвращает ID автора объявления.
     *
     * @return ID автора объявления
     */
    public Integer getAuthor() {
        return author;
    }

    /**
     * Возвращает ссылку на изображение объявления.
     *
     * @return ссылка на изображение объявления
     */
    public String getImage() {
        return image;
    }

    /**
     * Возвращает уникальный идентификатор объявления.
     *
     * @return уникальный идентификатор объявления
     */
    public Integer getPk() {
        return pk;
    }

    /**
     * Возвращает цену товара/услуги в объявлении.
     *
     * @return цена товара/услуги в объявлении
     */

    public Integer getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    /**
     * Устанавливает ID автора объявления.
     *
     * @param author ID автора объявления
     */
    public void setAuthor(Integer author) {
        this.author = author;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Устанавливает уникальный идентификатор объявления.
     *
     * @param pk уникальный идентификатор объявления
     */

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
