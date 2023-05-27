package com.fr.workorder.mapper;

import com.fr.workorder.entity.Company;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyMapper extends BaseMapper<Company> {
    Company selectByCompanyName(String companyName);
}
