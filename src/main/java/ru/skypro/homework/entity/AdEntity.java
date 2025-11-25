package ru.skypro.homework.entity;

import lombok.*;
import javax.persistence.Id;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor // создает конструктор
@NoArgsConstructor // создает пустой конструктор
@Table(name = "ads")  // Таблица "ads" в БД
public class AdEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // Используем pk вместо id для соответствия DTO
    private Integer pk;

    @Column(nullable = false, length = 32)
    private String title;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false, length = 64)
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    // Время создания объявления, по умолчанию - текущее время
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Связь "многие к одному": много объявлений - один пользователь
    // FetchType.LAZY - автор загружается только когда к нему обращаются
    @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn - колонка для связи с таблицей users
    @JoinColumn(name = "author_id", nullable = false)
    @ToString.Exclude
    private UserEntity author;

    // Связь "один ко многим": одно объявление - много комментариев
    @OneToMany(mappedBy = "ad", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<CommentEntity> comments;

}
