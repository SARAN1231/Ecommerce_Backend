package com.saran.ECommerce.Controllers;

import com.saran.ECommerce.Exceptions.ResourceNotFoundException;
import com.saran.ECommerce.Responses.ApiResponse;
import com.saran.ECommerce.dtos.ImageDto;
import com.saran.ECommerce.models.Image;
import com.saran.ECommerce.services.Image.IimageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final IimageService imageService;

    public ImageController(IimageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImage(@RequestParam List<MultipartFile> files, @RequestParam long productId) {
        try {
            List<ImageDto> imageDtos = imageService.saveImage(files, productId);
            return new ResponseEntity<>( new ApiResponse("upload Successful",imageDtos),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("upload Failed",e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable long imageId) {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage());

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }


    @PutMapping("/update/{imageId}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable long imageId,@RequestParam MultipartFile file) {

        try {
            Image image = imageService.getImageById(imageId);
            if(image != null) {
                ImageDto Updatedimage = imageService.updateImage(file,imageId);
                return new ResponseEntity<>(new ApiResponse("update Image Successful",Updatedimage),HttpStatus.OK);
            }

        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse("update Image Failed","No Image Found"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse("update Image Failed","Error"),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null) {
                imageService.deleteImage(imageId);
                return new ResponseEntity<>(new ApiResponse("delete Image Successful",null),HttpStatus.OK);
            }

        }
        catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse("delete Image Failed","No Image Found"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse("delete Image Failed","Error"),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
