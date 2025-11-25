package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;

import java.io.IOException;

public interface AdService {
    // Объявление методов для работы с объявлениями
    Ads getAllAds(); // Получить все объявления
    Ad addAd(CreateOrUpdateAd properties, MultipartFile image, String username) throws IOException; // Добавить объявление
    ExtendedAd getAd(Integer id); // Получить объявление по ID
    void removeAd(Integer id); // Удалить объявление
    Ad updateAd(Integer id, CreateOrUpdateAd updateAd); // Обновить объявление
    Ads getAdsMe(String username); // Получить объявления текущего пользователя    ТУТ У МЕНЯ ВОЗНИКЛИ ВОПРОСИКИ
    byte[] updateAdImage(Integer id, MultipartFile image) throws IOException; // Обновить изображение объявления
}
