package com.saran.ECommerce.services.Image;

import com.saran.ECommerce.dtos.ImageDto;
import com.saran.ECommerce.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IimageService {
    Image getImageById(Long id);
    List<ImageDto>  saveImage(List<MultipartFile> files, Long ProductId);
    void  updateImage(MultipartFile file,Long ImageId);
    void deleteImage(Long ImageId);
}
