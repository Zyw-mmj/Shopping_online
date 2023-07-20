package com.example.webstore.service.impl;

import com.example.webstore.dao.mapper.FeeMapper;
import com.example.webstore.dao.model.FeeRate;
import com.example.webstore.service.IFeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FeeServiceImpl implements IFeeService {

    @Autowired
    private FeeMapper feeRateMapper;

    @Override
    public FeeRate getByGrade(int grade) {
        return feeRateMapper.selectByGrade(grade);
    }
}
