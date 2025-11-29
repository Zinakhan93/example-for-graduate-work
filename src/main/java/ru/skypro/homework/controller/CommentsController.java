package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentService;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads") // Базовый путь для комментариев тоже /ads
@Tag(name = "Комментарии")

public class CommentsController {
    private final CommentService commentService; // Сервис для работы с комментариями

    @GetMapping("/{id}/comments")
    @Operation (summary = "Получение комментариев объявления")
    public ResponseEntity<Comments> getComments(@PathVariable("id") Integer id) {
        // Вызываем сервис для получения комментариев объявления
        Comments comments = commentService.getComments(id);
        // Возвращаем ответ со статусом 200 OK и списком комментариев
        return ResponseEntity.ok(comments);
    }
    @PostMapping("/{id}/comments")
    @Operation (summary = "Добавление комментария к объявлению")
    public ResponseEntity<Comment> addComment(@PathVariable("id") Integer id,
                                              @RequestBody CreateOrUpdateComment comment,
                                              Authentication authentication) {
        // Получаем email текущего пользователя
        String username = authentication.getName();
        // Вызываем сервис для добавления комментария
        Comment createdComment = commentService.addComment(id, comment, username);
        // Возвращаем ответ со статусом 200 OK и созданным комментарием
        return ResponseEntity.ok(createdComment);
    }


    @DeleteMapping("/{adId}/comments/{commentId}") // Две переменные пути
    @Operation (summary = "Удаление коментария")
    public ResponseEntity<?> deleteComment(@PathVariable("adId") Integer adId,
                                           @PathVariable("commentId") Integer commentId) {
        // Вызываем сервис для удаления комментария
        commentService.deleteComment(adId, commentId);
        // Возвращаем ответ со статусом 200 OK
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{adId}/comments/{commentId}")
    @Operation (summary = "Обновление комментария")
    public ResponseEntity<Comment> updateComment(@PathVariable("adId") Integer adId,
                                                 @PathVariable("commentId") Integer commentId,
                                                 @RequestBody CreateOrUpdateComment comment) {
        // Вызываем сервис для обновления комментария
        Comment updatedComment = commentService.updateComment(adId, commentId, comment);
        // Возвращаем ответ со статусом 200 OK и обновленным комментарием
        return ResponseEntity.ok(updatedComment);
    }
}
