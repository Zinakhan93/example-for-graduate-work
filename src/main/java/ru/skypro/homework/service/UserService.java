package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;

import java.io.IOException;

public interface UserService {
    User getCurrentUser();
    UpdateUser updateUser(UpdateUser updateUser);
    void updateUserImage(MultipartFile image) throws IOException;
    void setPassword(NewPassword newPassword);
}
