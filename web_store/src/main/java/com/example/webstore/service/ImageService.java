package com.example.webstore.service;

import com.example.webstore.dao.Entity.ImageEntity;

public interface ImageService {
    ImageEntity saveImagePath(String imagePath);
    public String getImagePath(Long id);
}
