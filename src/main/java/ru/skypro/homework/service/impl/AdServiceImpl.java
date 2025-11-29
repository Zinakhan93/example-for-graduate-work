package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;
import ru.skypro.homework.mapper.AdMapper;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AdService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service // Помечаем класс как Spring Service компонент
@RequiredArgsConstructor // Lombok: создает конструктор для всех final полей
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository; // Репозиторий для работы с объявлениями в БД
    private final UserRepository userRepository; // Репозиторий для работы с пользователями
    private final AdMapper adMapper; // Маппер для преобразования между Entity и DTO

    @Override
    // метод для получения всех объявлений
    public Ads getAllAds() {
        // Получаем все объявления из базы данных
        List<AdEntity> allAds = adRepository.findAll();

        // Создаем DTO для ответа
        Ads ads = new Ads();
        // Устанавливаем общее количество объявлений
        ads.setCount(allAds.size());
        // Преобразуем каждое Entity в DTO и собираем в список
        ads.setResults(allAds.stream()
                .map(adMapper::toDto) // Используем метод маппера для преобразования
                .collect(Collectors.toList()));

        return ads; // Возвращаем DTO со списком объявлений
    }

    @Override
    //  Метод для добавления объявления
    public Ad addAd(CreateOrUpdateAd properties, MultipartFile image, String username) throws IOException {
        // Находим пользователя по email (username)
        UserEntity author = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Преобразуем DTO в Entity
        AdEntity adEntity = adMapper.toEntity(properties);
        // Устанавливаем автора объявления
        adEntity.setAuthor(author);
        // Сохраняем путь к изображению (в реальном приложении нужно сохранять файл)
        adEntity.setImageUrl("/ads/images/" + System.currentTimeMillis() + ".jpg");

        // Сохраняем объявление в базу данных
        AdEntity savedAd = adRepository.save(adEntity);

        // Преобразуем сохраненное Entity обратно в DTO для ответа
        return adMapper.toDto(savedAd);
    }
    @Override
    // Метод, чтобы получить объявления по ID
    public ExtendedAd getAd(Integer id) {
        // Ищем объявление по ID, если не найдено - выбрасываем исключение
        AdEntity adEntity = adRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Объявление не найдено"));

        // Преобразуем Entity в расширенное DTO
        return adMapper.toExtendedDto(adEntity);
    }




    @Override
    // метод чтобы объявление по ID
    public void removeAd(Integer id) {
        // Проверяем существует ли объявление
        if (!adRepository.existsById(id)) {
            throw new RuntimeException("Объявление не найдено");
        }
        // Удаляем объявление из базы данных
        adRepository.deleteById(id);
    }

    @Override
    // метод для обновления объявления
    public Ad updateAd(Integer id, CreateOrUpdateAd updateAd) {
        // Находим объявление по ID
        AdEntity adEntity = adRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Объявление не найдено"));

        // Обновляем поля объявления из DTO
        adMapper.updateEntityFromDto(updateAd, adEntity);

        // Сохраняем обновленное объявление
        AdEntity updatedAd = adRepository.save(adEntity);

        // Преобразуем в DTO и возвращаем
        return adMapper.toDto(updatedAd);
    }

    @Override
    //Получить объявления текущего пользователя
    public Ads getAdsMe(String username) {
        // Находим пользователя по email
        UserEntity user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        // Получаем все объявления этого пользователя
        List<AdEntity> userAds = adRepository.findByAuthorId(user.getId());

        // Создаем DTO для ответа
        Ads ads = new Ads();
        ads.setCount(userAds.size());
        ads.setResults(userAds.stream()
                .map(adMapper::toDto)
                .collect(Collectors.toList()));

        return ads;
    }

    @Override
    //Обновить изображение объявления
    public byte[] updateAdImage(Integer id, MultipartFile image) throws IOException {
        // Находим объявление по ID
        AdEntity adEntity = adRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Объявление не найдено"));

        // В реальном приложении здесь должна быть логика сохранения файла
        // Пока просто обновляем ссылку
        adEntity.setImageUrl("/ads/images/" + id + "_" + System.currentTimeMillis() + ".jpg");
        adRepository.save(adEntity);

        // Возвращаем массив байтов изображения (в реальном приложении нужно читать файл)
        return image.getBytes();
    }

}
