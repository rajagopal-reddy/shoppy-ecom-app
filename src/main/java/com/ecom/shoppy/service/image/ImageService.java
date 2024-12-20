package com.ecom.shoppy.service.image;

import com.ecom.shoppy.dto.ImageDto;
import com.ecom.shoppy.exception.ResourceNotFoundException;
import com.ecom.shoppy.model.Image;
import com.ecom.shoppy.model.Product;
import com.ecom.shoppy.repository.ImageRepository;
import com.ecom.shoppy.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository::delete, () -> {
            throw new ResourceNotFoundException("No image found with id: " + id);
        });
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);

        List<ImageDto> savedImageDtos = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                Image savedImage = imageRepository.save(image);

                // Set download URL after saving the image
                String downloadUrl = "api/v1/images/image/download/" + savedImage.getId();
                savedImage.setDownloadUrl(downloadUrl);
                imageRepository.save(savedImage); // Update the image with download URL

                ImageDto imageDto = new ImageDto();
                imageDto.setImageId(savedImage.getId());
                imageDto.setImageName(savedImage.getFileName());
                imageDto.setDownloadUrl(downloadUrl);
                savedImageDtos.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException("Error saving image: " + e.getMessage(), e);
            }
        }

        return savedImageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
//            image.setImage(file.getBytes());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Error updating image: " + e.getMessage(), e);
        }
    }
}