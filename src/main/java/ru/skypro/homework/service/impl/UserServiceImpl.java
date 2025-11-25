package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UserService;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;  // Репозиторий для работы с пользователями
    private final UserMapper userMapper;          // Маппер для преобразования
    private final PasswordEncoder passwordEncoder; // Кодировщик паролей

    @Override
    public User getCurrentUser() {
        // Получаем Entity текущего пользователя
        UserEntity userEntity = getCurrentUserEntity();
        // Преобразуем в DTO и возвращаем
        return userMapper.toDto(userEntity);
    }

    @Override
    public UpdateUser updateUser(UpdateUser updateUser) {
        // Получаем текущего пользователя
        UserEntity userEntity = getCurrentUserEntity();
        // Обновляем поля Entity из DTO
        userMapper.updateEntityFromDto(updateUser, userEntity);
        // Сохраняем изменения в БД
        userRepository.save(userEntity);
        // Возвращаем DTO (можно вернуть обновленного пользователя)
        return updateUser;
    }

    @Override
    public void updateUserImage(MultipartFile image) throws IOException {
        // TODO: реализовать сохранение файла на диск/в облако
        // Пока просто сохраняем ссылку
        UserEntity userEntity = getCurrentUserEntity();
        // Генерируем путь к изображению
        userEntity.setImageUrl("/images/users/" + userEntity.getId() + ".jpg");
        userRepository.save(userEntity);
    }

    @Override
    public void setPassword(NewPassword newPassword) {
        UserEntity userEntity = getCurrentUserEntity();

        // Проверяем что текущий пароль верный
        if (!passwordEncoder.matches(newPassword.getCurrentPassword(), userEntity.getPassword())) {
            throw new IllegalArgumentException("Текущий пароль неверен");
        }

        // Устанавливаем и кодируем новый пароль
        userEntity.setPassword(passwordEncoder.encode(newPassword.getNewPassword()));
        userRepository.save(userEntity);
    }

    // Вспомогательный метод для получения текущего пользователя
    private UserEntity getCurrentUserEntity() {
        // Получаем информацию об аутентификации из Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // В Spring Security имя пользователя - это email
        String email = authentication.getName();
        // Ищем пользователя в БД по email
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }
}
