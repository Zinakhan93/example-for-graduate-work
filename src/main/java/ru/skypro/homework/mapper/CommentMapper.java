package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Comment;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.CommentEntity;

import java.time.ZoneOffset;

@Component

public class CommentMapper {
    public CommentEntity toEntity(CreateOrUpdateComment dto) {
        CommentEntity entity = new CommentEntity();
        entity.setText(dto.getText());
        return entity;
    }
    public Comment toDto(CommentEntity entity) {
        Comment dto = new Comment();
        dto.setPk(entity.getPk());
        dto.setText(entity.getText());

        // Конвертируем LocalDateTime в миллисекунды с эпохи Unix
        if (entity.getCreatedAt() != null) {
            dto.setCreatedAt(entity.getCreatedAt().toInstant(ZoneOffset.UTC).toEpochMilli());
        }

        // Заполняем информацию об авторе
        if (entity.getAuthor() != null) {
            dto.setAuthor(entity.getAuthor().getId());
            dto.setAuthorFirstName(entity.getAuthor().getFirstName());
            dto.setAuthorImage(entity.getAuthor().getImageUrl());
        }

        return dto;
    }

    public void updateEntityFromDto(CreateOrUpdateComment dto, CommentEntity entity) {
        if (dto.getText() != null && !dto.getText().isBlank()) {
            entity.setText(dto.getText());
        }
    }
}
