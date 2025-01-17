package com.saran.ECommerce.services.Image;

import com.saran.ECommerce.dtos.ImageDto;
import com.saran.ECommerce.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IimageService {
    Image getImageById(long id);
    List<ImageDto>  saveImage(List<MultipartFile> files, long ProductId);
    ImageDto  updateImage(MultipartFile file,long ImageId);
    void deleteImage(long ImageId);
}
