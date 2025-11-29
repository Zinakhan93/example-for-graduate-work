package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
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

    private final UserDetailsManager manager;        // Spring Security менеджер пользователей
    private final PasswordEncoder encoder;          // Кодировщик паролей
    private final UserRepository userRepository;   // Наш репозиторий для UserEntity
    private final UserMapper userMapper;          // Маппер для преобразования

    public AuthServiceImpl(UserDetailsManager manager, PasswordEncoder encoder, UserRepository userRepository, UserMapper userMapper) {
        this.manager = manager;
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public boolean login(String userName, String password) {
        // Проверяем существует ли пользователь в Spring Security
        if (!manager.userExists(userName)) {
            return false;
        }
        // Загружаем данные пользователя
        UserDetails userDetails = manager.loadUserByUsername(userName);
        // Сравниваем введенный пароль с закодированным в БД
        return encoder.matches(password, userDetails.getPassword());
    }

    @Override
    public boolean register(Register register) {
        // Проверяем нет ли уже пользователя с таким email
        if (manager.userExists(register.getUsername())) {
            return false;
        }

        // Создаем пользователя в Spring Security
        manager.createUser(
                User.builder()
                        .passwordEncoder(this.encoder::encode) // Используем кодировщик для пароля
                        .password(register.getPassword())
                        .username(register.getUsername())
                        .roles(register.getRole().name())     // Роль из DTO
                        .build());

        // Сохраняем пользователя в нашу БД через репозиторий
        UserEntity userEntity = userMapper.toEntity(register);
        // Кодируем пароль перед сохранением в нашу БД
        userEntity.setPassword(encoder.encode(register.getPassword()));
        userRepository.save(userEntity);

        return true;
    }

}
