package com.example.webstore.service.impl;

import com.example.webstore.dao.GoodsRepository;
import com.example.webstore.domain.CarouselBo;
import com.example.webstore.service.ICarouselService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CarouselServiceImpl implements ICarouselService {

    @Autowired
    private GoodsRepository goodsRepo;

    @Override
    public List<CarouselBo> getGoodshaveCarousel() {
        return goodsRepo.getGoodshaveCarousel();
    }
}
