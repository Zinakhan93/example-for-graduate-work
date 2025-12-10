package ru.skypro.homework.dto;

import lombok.*;

import static ru.skypro.homework.dto.Role.USER;
/**
 * DTO (Data Transfer Object) для представления пользователя системы.
 * Содержит полную информацию о пользователе, включая идентификационные данные,
 * контактную информацию, роль в системе и ссылку на аватар.
 * @see Role
 * @see Register
 * @see UpdateUser
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer id;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
    private String image;

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
/**
 * Конструктор с параметром и без создаются автоматически с помощью @AllArgsConstructor, @NoArgsConstructor
 * @param id уникальный идентификатор пользователя
 * @param email email пользователя (логин)
 * @param firstName имя пользователя
 * @param lastName фамилия пользователя
 * @param phone номер телефона пользователя
 * @param role роль пользователя в системе
 * @param image ссылка на аватар пользователя
 */