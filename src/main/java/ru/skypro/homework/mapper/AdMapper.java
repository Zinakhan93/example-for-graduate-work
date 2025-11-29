package ru.skypro.homework.mapper;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.dto.ExtendedAd;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.entity.AdEntity;
import ru.skypro.homework.entity.UserEntity;

@Component
public class AdMapper {
    public AdEntity toEntity(CreateOrUpdateAd dto) {
        AdEntity entity = new AdEntity();
        entity.setTitle(dto.getTitle());
        entity.setPrice(dto.getPrice());
        entity.setDescription(dto.getDescription());
        return entity;
    }

    public Ad toDto(AdEntity entity) {
        Ad dto = new Ad();
        dto.setPk(entity.getPk());
        // Берем ID автора из связанной сущности
        dto.setAuthor(entity.getAuthor().getId());
        dto.setTitle(entity.getTitle());
        dto.setPrice(entity.getPrice());
        dto.setImage(entity.getImageUrl());
        return dto;
    }

    public ExtendedAd toExtendedDto(AdEntity entity) {
        ExtendedAd dto = new ExtendedAd();
        dto.setPk(entity.getPk());
        dto.setTitle(entity.getTitle());
        dto.setPrice(entity.getPrice());
        dto.setDescription(entity.getDescription());
        dto.setImage(entity.getImageUrl());

        // Заполняем расширенную информацию об авторе
        if (entity.getAuthor() != null) {
            dto.setAuthorFirstName(entity.getAuthor().getFirstName());
            dto.setAuthorLastName(entity.getAuthor().getLastName());
            dto.setEmail(entity.getAuthor().getEmail());
            dto.setPhone(entity.getAuthor().getPhone());
        }

        return dto;
    }
    public void updateEntityFromDto(CreateOrUpdateAd dto, AdEntity entity) {
        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            entity.setTitle(dto.getTitle());
        }
        if (dto.getPrice() != null) {
            entity.setPrice(dto.getPrice());
        }
        if (dto.getDescription() != null && !dto.getDescription().isBlank()) {
            entity.setDescription(dto.getDescription());
        }
    }

}
