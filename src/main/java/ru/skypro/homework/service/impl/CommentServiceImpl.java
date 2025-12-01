package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.CommentEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.CommentRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

@Service // Помечаем класс как Spring Service компонент
@RequiredArgsConstructor // Lombok: создает конструктор для всех final полей
public class CommentServiceImpl  implements CommentService {
    private final CommentRepository commentRepository; // Репозиторий для работы с комментариями
    private final AdRepository adRepository; // Репозиторий для работы с объявлениями
    private final UserRepository userRepository; // Репозиторий для работы с пользователями
    private final CommentMapper commentMapper; // Маппер для преобразования между Entity и DTO

    @Override
    public Comments getComments(Integer adId) {
        // Получаем все комментарии для указанного объявления
        List<CommentEntity> commentEntities = commentRepository.findByAdPk(adId);

        // Создаем DTO для ответа
        Comments comments = new Comments();
        // Устанавливаем количество комментариев
        comments.setCount(commentEntities.size());
        // Преобразуем каждое Entity в DTO и собираем в список
        comments.setResults(commentEntities.stream()
                .map(commentMapper::toDto)
                .collect(Collectors.toList()));

        return comments;
    }

    @Override
    public Comment addComment(Integer adId, CreateOrUpdateComment comment, String username) {
        // Находим объявление по ID
        AdEntity ad = adRepository.findById(adId)
                .orElseThrow(() -> new RuntimeException("Объявление не найдено"));

        // Находим пользователя по email
        UserEntity author = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Преобразуем DTO в Entity
        CommentEntity commentEntity = commentMapper.toEntity(comment);
        // Устанавливаем автора комментария
        commentEntity.setAuthor(author);
        // Устанавливаем объявление, к которому относится комментарий
        commentEntity.setAd(ad);

        // Сохраняем комментарий в базу данных
        CommentEntity savedComment = commentRepository.save(commentEntity);

        // Преобразуем сохраненное Entity обратно в DTO для ответа
        return commentMapper.toDto(savedComment);
    }
    // Проверяем, что пользователь является автором комментария или администратором
    @PreAuthorize("hasRole('ADMIN') or @commentServiceImpl.isCommentAuthor(authentication.name, #adId, #commentId)")
    @Override
    public void deleteComment(Integer adId, Integer commentId) {
        // Проверяем существование комментария
        CommentEntity comment = commentRepository.findByAdPkAndPk(adId, commentId)
                .orElseThrow(() -> new RuntimeException("Комментарий не найден"));

        // Удаляем комментарий из базы данных
        commentRepository.delete(comment);
    }

    // Проверяем, что пользователь является автором комментария или администратором
    @PreAuthorize("hasRole('ADMIN') or @commentServiceImpl.isCommentAuthor(authentication.name, #adId, #commentId)")
    @Override
    public Comment updateComment(Integer adId, Integer commentId, CreateOrUpdateComment comment) {
        // Находим комментарий по ID объявления и ID комментария
        CommentEntity commentEntity = commentRepository.findByAdPkAndPk(adId, commentId)
                .orElseThrow(() -> new RuntimeException("Комментарий не найден"));

        // Обновляем текст комментария из DTO
        commentMapper.updateEntityFromDto(comment, commentEntity);

        // Сохраняем обновленный комментарий
        CommentEntity updatedComment = commentRepository.save(commentEntity);

        // Преобразуем в DTO и возвращаем
        return commentMapper.toDto(updatedComment);
    }

    // Вспомогательный метод для проверки авторства комментария
    // Используется в аннотации @PreAuthorize
    public boolean isCommentAuthor(String username, Integer adId, Integer commentId) {
        // Находим пользователя по email
        UserEntity user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Находим комментарий по ID объявления и ID комментария
        CommentEntity comment = commentRepository.findByAdPkAndPk(adId, commentId)
                .orElseThrow(() -> new RuntimeException("Комментарий не найден"));

        // Проверяем, что автор комментария совпадает с текущим пользователем
        return comment.getAuthor().getId().equals(user.getId());
    }




}
