package ru.skypro.homework.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.homework.dto.Role;


import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor // создает конструктор
@NoArgsConstructor // создает пустой конструктор
@Table(name = "users")
// Реализуем UserDetails для интеграции с Spring Security
public class UserEntity  implements UserDetails {
    @Id
    // Стратегия генерации ID: IDENTITY - база данных сама генерирует уникальные ID
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // @Column - настройки колонки в таблице
    // nullable = false - NOT NULL в БД
    // unique = true - уникальное значение
    // length = 32 - максимальная длина строки
    @Column(nullable = false, unique = true, length = 32)
    private String email; // Это будет username в Spring Security

    // Пароль будет храниться в закодированном виде
    @Column(nullable = false, length = 64)
    private String password;

    // name = "first_name" - имя колонки в БД (snake_case)
    @Column(name = "first_name", nullable = false, length = 16)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 16)
    private String lastName;

    // Телефон может быть null, длина 18 для формата "+7 (XXX) XXX-XX-XX"
    @Column(length = 18)
    private String phone;

    // @Enumerated - как хранить enum в БД: STRING - как строка ('USER', 'ADMIN')
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    // Роль по умолчанию - USER
    private Role role = Role.USER;

    // Ссылка на изображение аватара
    @Column(name = "image_url")
    private String imageUrl;

    // Поле для Spring Security - активирован ли пользователь
    @Column(nullable = true)
    private boolean enabled = true;


    // Связь "один ко многим": один пользователь - много объявлений
    // mappedBy = "author" - поле в классе AdEntity, которое владеет связью
    // cascade = CascadeType.ALL - операции сохраняются каскадно
    // fetch = FetchType.LAZY - данные загружаются только при обращении
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // Исключаем из toString, чтобы избежать бесконечной рекурсии
    @ToString.Exclude
    @JsonIgnore
    private List<AdEntity> ads;

    // Аналогично для комментариев
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<CommentEntity> comments;

    // Методы из интерфейса UserDetails

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Преобразуем нашу роль в формат Spring Security
        // ROLE_USER или ROLE_ADMIN
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        // Для Spring Security username - это email
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        // Аккаунт не просрочен
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Аккаунт не заблокирован
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Пароль не просрочен
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Аккаунт активирован
        return enabled;
    }
}
