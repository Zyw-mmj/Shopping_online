package com.example.webstore.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.webstore.dao.model.FeeRate;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeeMapper extends BaseMapper<FeeRate> {
    @Select("SELECT * FROM fee_rate WHERE grade = #{grade};")
    FeeRate selectByGrade(@Param("grade") int grade);
}
