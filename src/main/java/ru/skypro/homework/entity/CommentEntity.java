package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.Id;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor // создает конструктор
@NoArgsConstructor // создает пустой конструктор
@Table(name = "comments")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pk;

    @Column(nullable = false, length = 64)
    private String text;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // Связь с пользователем (много комментариев - один пользователь)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private UserEntity author;

    // Связь с объявлением (много комментариев - одно объявление)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ad_id", nullable = false)
    private AdEntity ad;
}
