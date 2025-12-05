package ru.skypro.homework.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileService {
    private String uploadDir; // Измените final на обычное поле

    // Аннотируем сеттер с @Value
    @Value("${file.upload.dir}")
    public void setUploadDir(String uploadDir) {
        System.out.println("=== FileService.setUploadDir() ===");
        System.out.println("Setting uploadDir to: '" + uploadDir + "'");

        this.uploadDir = uploadDir;

        if (this.uploadDir == null || this.uploadDir.isBlank()) {
            // Если значение не установлено в properties, используем значение по умолчанию
            this.uploadDir = "./uploads";
            System.out.println("Using default uploadDir: " + this.uploadDir);
        }

        createUploadDir(); // Создаем папку после установки значения
    }

    // Уберите конструктор или оставьте пустой
    public FileService() {
        System.out.println("FileService instance created");
    }

    // Создает папку, если она не существует
    private void createUploadDir() {
        System.out.println("Creating upload directory: " + uploadDir);
        Path path = Paths.get(uploadDir);

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                System.out.println("Directory created: " + path.toAbsolutePath());
            } else {
                System.out.println("Directory already exists: " + path.toAbsolutePath());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to create upload directory: " + uploadDir, e);
        }
    }

    public String saveFile(MultipartFile file) throws IOException {
        System.out.println("=== FileService.saveFile() ===");
        System.out.println("Current uploadDir: " + uploadDir);

        String extension = getFileExtension(file.getOriginalFilename());
        String fileName = UUID.randomUUID().toString() + extension;

        Path filePath = Paths.get(uploadDir, fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        System.out.println("File saved: " + fileName);
        System.out.println("Path: " + filePath.toAbsolutePath());

        return "/uploads/" + fileName;
    }

    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return ".jpg";
        }
        return filename.substring(filename.lastIndexOf("."));
    }
}
