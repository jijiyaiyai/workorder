package com.fr.workorder.service.impl;

import com.fr.workorder.entity.Process;
import com.fr.workorder.mapper.ProcessMapper;
import com.fr.workorder.service.ProcessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class ProcessServiceImpl extends ServiceImpl<ProcessMapper, Process> implements ProcessService {

}
