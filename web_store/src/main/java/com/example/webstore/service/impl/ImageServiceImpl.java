package com.example.webstore.service.impl;

import com.example.webstore.dao.Entity.ImageEntity;
import com.example.webstore.dao.Entity.ImageRepository;
import com.example.webstore.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    // 存储图片路径
    public ImageEntity saveImagePath(String path) {
        ImageEntity image = new ImageEntity();
        image.setPath(path);
        return imageRepository.save(image);
    }

    // 获取图片路径
    public String getImagePath(Long id) {
        Optional<ImageEntity> imageOptional = imageRepository.findById(id);
        return imageOptional.map(ImageEntity::getPath).orElse(null);
    }
}
