package com.saran.ECommerce.Controllers;

import com.saran.ECommerce.Exceptions.ResourceNotFoundException;
import com.saran.ECommerce.Responses.ApiResponse;
import com.saran.ECommerce.dtos.ImageDto;
import com.saran.ECommerce.models.Image;
import com.saran.ECommerce.services.Image.IimageService;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/api/v1/images/image")
@RequiredArgsConstructor
public class ImageController {

    private final IimageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse> saveImage(@RequestParam List<MultipartFile> files,@RequestParam Long id) {
        try {
            List<ImageDto> imageDtos = imageService.saveImage(files, id);
            return new ResponseEntity<>( new ApiResponse("upload Successful",imageDtos),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse("upload Failed",e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   @GetMapping("/download/{imageId}")
   public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1,(int)image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
   }

    @PutMapping("/update/{imageId}")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId,@RequestParam MultipartFile file) {

        try {
            Image image = imageService.getImageById(imageId);
            if(image != null) {
                imageService.updateImage(file,imageId);
                return new ResponseEntity<>(new ApiResponse("update Image Successful",null),HttpStatus.OK);
            }

        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(new ApiResponse("update Image Failed","No Image Found"),HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new ApiResponse("update Image Failed","Error"),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
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
