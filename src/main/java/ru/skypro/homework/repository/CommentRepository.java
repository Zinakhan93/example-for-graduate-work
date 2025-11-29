package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.entity.CommentEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Integer> {

    // Поиск всех комментариев объявления
    // SELECT * FROM comments WHERE ad_id = ?
    List<CommentEntity> findByAdPk(Integer adId);

    //Поиск комментария по ID объявления и ID комментария
    // SELECT * FROM comments WHERE ad_id = ? AND pk = ?
    Optional<CommentEntity> findByAdPkAndPk(Integer adId, Integer commentId);

      // Удаление комментария по ID объявления и ID комментария
    // DELETE FROM comments WHERE ad_id = ? AND pk = ?
    void deleteByAdPkAndPk(Integer adId, Integer commentId);
}