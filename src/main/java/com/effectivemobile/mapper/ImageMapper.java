package com.effectivemobile.mapper;

import com.effectivemobile.entity.Image;
import org.mapstruct.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Mapper(componentModel = "spring")
public abstract class ImageMapper {

    public Image toImage(MultipartFile file) {
        try {
            return Image.builder()
                    .filename(file.getOriginalFilename())
                    .contentType(file.getContentType())
                    .imageData(file.getBytes())
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
