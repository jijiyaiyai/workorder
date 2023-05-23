package com.fr.workorder.service.impl;

import com.fr.workorder.entity.Report;
import com.fr.workorder.mapper.ReportMapper;
import com.fr.workorder.service.ReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements ReportService {

}
