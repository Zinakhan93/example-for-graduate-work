package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.build.Plugin;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.service.AdService;

import java.io.IOException;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads")
@Tag(name = "Объявления")
// управление объявлениями:
public class AdsController {

    private final AdService adService; // Сервис для работы с объявлениями


    @GetMapping
    @Operation(summary = "Получение всех объявлений")
    public ResponseEntity<Ads> getAllAds() {
        // Вызываем сервис для получения всех объявлений
        Ads ads = adService.getAllAds();
        // Возвращаем ответ со статусом 200 OK и данными
        return ResponseEntity.ok(ads);
    }


    //@PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)// Обработка POST запросов с multipart данными
    @Operation (summary= "Добавления объявления")
    public ResponseEntity<Ad> addAd(@RequestPart("properties") CreateOrUpdateAd properties,
                                    @RequestPart("image") MultipartFile image,
                                    Authentication authentication) throws IOException {
        // Authentication содержит информацию о текущем аутентифицированном пользователе
        String username = authentication.getName(); // Получаем email пользователя

        // Вызываем сервис для создания объявления
        Ad ad = adService.addAd(properties, image, username);
        // Возвращаем ответ со статусом 201 Created и созданным объявлением
        return ResponseEntity.status(201).body(ad);
    }


    @GetMapping("/{id}") // {id} - path variable (переменная пути)
    @Operation (summary= "Получение информации об объявлении")
    public ResponseEntity<ExtendedAd> getAds(@PathVariable("id") Integer id) {
        // @PathVariable извлекает значение {id} из URL
        // Вызываем сервис для получения объявления по ID
        ExtendedAd ad = adService.getAd(id);
        // Возвращаем ответ со статусом 200 OK и данными объявления
        return ResponseEntity.ok(ad);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление объявления")
    public ResponseEntity<?> removeAd(@PathVariable("id") Integer id) {
        // Вызываем сервис для удаления объявления
        adService.removeAd(id);
        // Возвращаем ответ со статусом 204 No Content (успешное удаление)
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Обновление информации об объявлении")
    public ResponseEntity<Ad> updateAds(@PathVariable("id") Integer id,
                                        @RequestBody CreateOrUpdateAd updateAd) {
        // @RequestBody преобразует JSON из тела запроса в Java объект
        // Вызываем сервис для обновления объявления
        Ad ad = adService.updateAd(id, updateAd);
        // Возвращаем ответ со статусом 200 OK и обновленным объявлением
        return ResponseEntity.ok(ad);
    }
    @GetMapping("/me") // GET /ads/me - объявления текущего пользователя
    @Operation(summary = "Получение объявлений авторизованного пользователя")
    public ResponseEntity<Ads> getAdsMe(Authentication authentication) {
        // Получаем email текущего пользователя
        String username = authentication.getName();
        // Вызываем сервис для получения объявлений текущего пользователя
        Ads ads = adService.getAdsMe(username);
        // Возвращаем ответ со статусом 200 OK и списком объявлений
        return ResponseEntity.ok(ads);
    }

    @PatchMapping(value = "/{id}/image", consumes = "multipart/form-data")
    @Operation (summary = "Обновление картинки объявления")
    public ResponseEntity<byte[]> updateImage(@PathVariable("id") Integer id,
                                              @RequestParam("image") MultipartFile image) throws IOException {
        // Вызываем сервис для обновления изображения объявления
        byte[] imageData = adService.updateAdImage(id, image);
        // Возвращаем ответ со статусом 200 OK и данными изображения
        return ResponseEntity.ok(imageData);

    }
}
