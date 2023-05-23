package com.fr.workorder.service.impl;

import com.fr.workorder.entity.Workorder;
import com.fr.workorder.mapper.WorkorderMapper;
import com.fr.workorder.service.WorkorderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class WorkorderServiceImpl extends ServiceImpl<WorkorderMapper, Workorder> implements WorkorderService {

}
