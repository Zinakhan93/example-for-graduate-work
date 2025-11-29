package ru.skypro.homework.service;

import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;

public interface CommentService {
    // Объявление методов для работы с комментариями
    Comments getComments(Integer adId); // Получить комментарии объявления
    Comment addComment(Integer adId, CreateOrUpdateComment comment, String username); // Добавить комментарий
    void deleteComment(Integer adId, Integer commentId); // Удалить комментарий
    Comment updateComment(Integer adId, Integer commentId, CreateOrUpdateComment comment); // Обновить комментарий
}
