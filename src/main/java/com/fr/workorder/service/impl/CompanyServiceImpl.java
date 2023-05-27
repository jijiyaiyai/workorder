package com.fr.workorder.service.impl;

import com.fr.workorder.entity.Company;
import com.fr.workorder.mapper.CompanyMapper;
import com.fr.workorder.service.CompanyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {
    @Autowired
    CompanyMapper companyMapper;
    @Override
    public int insert(Company company) {
        return companyMapper.insert(company);
    }
}
