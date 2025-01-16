package com.saran.ECommerce.Mapper;

import com.saran.ECommerce.dtos.ImageDto;
import com.saran.ECommerce.models.Image;
import org.springframework.stereotype.Service;

@Service
public class ImageMapper {

    public ImageDto toImageDto(Image image) {
        return new ImageDto(image.getId(), image.getFileName(), image.getDownloadUrl());
    }
}
