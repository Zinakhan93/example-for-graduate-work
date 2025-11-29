package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;

// @Component - Spring Bean, который можно внедрять через @Autowired
@Component
public class UserMapper {
    // Преобразование Register DTO в UserEntity для сохранения в БД
    public UserEntity toEntity(Register register) {
        UserEntity entity = new UserEntity();
        // В DTO поле называется username, в Entity - email
        entity.setEmail(register.getUsername());
        // Пароль будет закодирован в сервисе
        entity.setPassword(register.getPassword());
        entity.setFirstName(register.getFirstName());
        entity.setLastName(register.getLastName());
        entity.setPhone(register.getPhone());
        entity.setRole(register.getRole());
        return entity;
    }


    // Преобразование UserEntity в User DTO для отправки клиенту
    public User toDto(UserEntity entity) {
        User dto = new User();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setPhone(entity.getPhone());
        dto.setRole(entity.getRole());
        // imageUrl из Entity становится image в DTO
        dto.setImage(entity.getImageUrl());
        return dto;

    }

    // Частичное обновление Entity из DTO (только измененные поля)
    public void updateEntityFromDto(UpdateUser dto, UserEntity entity) {
        // Обновляем только если новое значение не null и не пустое
        if (dto.getFirstName() != null && !dto.getFirstName().isBlank()) {
            entity.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null && !dto.getLastName().isBlank()) {
            entity.setLastName(dto.getLastName());
        }
        if (dto.getPhone() != null && !dto.getPhone().isBlank()) {
            entity.setPhone(dto.getPhone());
        }
    }
}
