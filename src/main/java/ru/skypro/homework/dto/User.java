package ru.skypro.homework.dto;

import lombok.*;

import static ru.skypro.homework.dto.Role.USER;

@Data

@AllArgsConstructor // создает конструктор
@NoArgsConstructor  // создает пустой конструктор
public class User {
    private Integer id;         // ID пользователя
    private String email;        // Email (логин)
    private String firstName;    // Имя
    private String lastName;     // Фамилия
    private String phone;        // Телефон
    private Role role;         // Роль
    private String image;// Ссылка на аватар

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
