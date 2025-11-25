package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.AdEntity;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<AdEntity, Integer> {

    // Находит все объявления по ID автора
    // SELECT * FROM ads WHERE author_id = ?
    List<AdEntity> findByAuthorId(Integer authorId);

    // Поиск по заголовку (игнорируя регистр)
    // SELECT * FROM ads WHERE LOWER(title) LIKE LOWER(%?%)
    List<AdEntity> findByTitleContainingIgnoreCase(String title);
}