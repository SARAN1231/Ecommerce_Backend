package com.saran.ECommerce.services.Image;

import com.saran.ECommerce.Exceptions.ResourceNotFoundException;
import com.saran.ECommerce.Mapper.ImageMapper;
import com.saran.ECommerce.Repository.ImageRepository;
import com.saran.ECommerce.dtos.ImageDto;
import com.saran.ECommerce.models.Image;
import com.saran.ECommerce.models.Product;
import com.saran.ECommerce.services.product.IproductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service

public class ImageService implements IimageService{

    private final ImageRepository imageRepository;
    private final IproductService productService;
    private final ImageMapper imageMapper;

    public ImageService(ImageRepository imageRepository, IproductService productService, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.productService = productService;
        this.imageMapper = imageMapper;
    }


    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Image not found for this id :: " + id));
    }

    @Override
    public  List<ImageDto>  saveImage(List<MultipartFile> files, Long ProductId) {
        Product product = productService.getProductById(ProductId);
        List<ImageDto> savedImages = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String builtDownloadUrl = "/api/v1/images/image/download/";

                String downloadUrl = builtDownloadUrl + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);

                savedImage.setDownloadUrl(builtDownloadUrl+savedImage.getId());
                imageRepository.save(savedImage);

                savedImages.add(imageMapper.toImageDto(savedImage));
            }
            catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImages;
    }



    @Override
    public void updateImage(MultipartFile file, Long ImageId) {
        Image image = getImageById(ImageId);
        try{
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes())); // converting multipart file type to blob type
            imageRepository.save(image);
        }
        catch(IOException | SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public void deleteImage(Long ImageId) {
        Image image = getImageById(ImageId);
        if(image != null){
            imageRepository.delete(image);
        }
        else{
            throw new ResourceNotFoundException("Image not found for this id :: " + ImageId);
        }
    }
}
