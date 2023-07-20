package com.example.webstore.service;

import com.example.webstore.dao.model.FeeRate;

public interface IFeeService {
    public FeeRate getByGrade(int grade);
}
