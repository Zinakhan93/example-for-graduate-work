package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder encoder;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthServiceImpl(PasswordEncoder encoder,
                           UserRepository userRepository,
                           UserMapper userMapper) {
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public boolean login(String userName, String password) {
        // Находим пользователя в нашей таблице
        UserEntity userEntity = userRepository.findByEmail(userName)
                .orElse(null);

        if (userEntity == null) {
            return false; // пользователь не найден
        }

        // Проверяем пароль
        return encoder.matches(password, userEntity.getPassword());
    }

    @Override
    public boolean register(Register register) {
        // Проверяем существует ли пользователь
        if (userRepository.existsByEmail(register.getUsername())) {
            return false; // пользователь уже существует
        }

        // Создаем UserEntity
        UserEntity userEntity = userMapper.toEntity(register);

        // Кодируем пароль
        userEntity.setPassword(encoder.encode(register.getPassword()));

        // Устанавливаем статус и роль
        userEntity.setEnabled(true);
        userEntity.setRole(register.getRole());

        // Сохраняем в БД
        userRepository.save(userEntity);

        return true; // ← ВАЖНО: возвращаем true при успешной регистрации
    }
}

