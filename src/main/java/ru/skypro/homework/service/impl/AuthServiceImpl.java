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

    private final UserDetailsManager userDetailsManager;    // Теперь это JdbcUserDetailsManager    // Spring Security менеджер пользователей
    private final PasswordEncoder encoder;          // Кодировщик паролей
    private final UserRepository userRepository;   // Наш репозиторий для UserEntity
    private final UserMapper userMapper;          // Маппер для преобразования

    public AuthServiceImpl(UserDetailsManager userDetailsManager, PasswordEncoder encoder, UserRepository userRepository, UserMapper userMapper) {
        this.userDetailsManager = userDetailsManager;
        this.encoder = encoder;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public boolean login(String userName, String password) {
        try {
            // Пытаемся загрузить пользователя через UserDetailsManager
            UserDetails userDetails = userDetailsManager.loadUserByUsername(userName);
            // Сравниваем введенный пароль с паролем из БД
            return encoder.matches(password, userDetails.getPassword());
        } catch (UsernameNotFoundException e) {
            // Пользователь не найден
            return false;
        }
    }

    @Override
    public boolean register(Register register) {
        // Проверяем существует ли пользователь в БД
        if (userRepository.existsByEmail(register.getUsername())) {
            return false;
        }

        // Создаем пользователя в Spring Security (JdbcUserDetailsManager)
        // UserDetailsManager автоматически сохранит пользователя в БД
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(register.getUsername()) // email
                .password(encoder.encode(register.getPassword())) // кодируем пароль
                .roles(register.getRole().name()) // роль пользователя
                .build();

        userDetailsManager.createUser(userDetails);

        // Дополнительно сохраняем пользователя в нашу таблицу users
        // для хранения дополнительных полей (имя, фамилия, телефон и т.д.)
        UserEntity userEntity = userMapper.toEntity(register);
        userEntity.setPassword(encoder.encode(register.getPassword())); // кодируем пароль
        userEntity.setEnabled(true); // активируем пользователя
        userRepository.save(userEntity);
        return false;
    }
}

