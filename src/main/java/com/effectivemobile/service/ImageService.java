package com.effectivemobile.service;

import com.effectivemobile.repository.ImageRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.effectivemobile.entity.Image;
import com.effectivemobile.mapper.ImageMapper;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ImageService {

     ImageRepository repository;
     ImageMapper imageMapper;

    @Transactional
    public List<Image> uploadImages(List<MultipartFile> files) {
        List<Image> images = files.stream().map(imageMapper::toImage).toList();
        return repository.saveAll(images);
    }
}
