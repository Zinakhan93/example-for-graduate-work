package ru.skypro.homework.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.UserEntity;

import java.util.Optional;

// @Repository - помечаем как Spring Bean для работы с БД
@Repository
// Наследуемся от JpaRepository<UserEntity, Integer>:
// UserEntity - тип сущности, Integer - тип первичного ключа
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    // Spring Data автоматически создает запрос по имени метода:
    // SELECT * FROM users WHERE email = ?
    Optional<UserEntity> findByEmail(String email);

    // Проверка существования: SELECT COUNT(*) > 0 FROM users WHERE email = ?
    boolean existsByEmail(String email);
}