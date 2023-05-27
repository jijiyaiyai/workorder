package com.fr.workorder.service.impl;

import com.fr.workorder.entity.Loginticket;
import com.fr.workorder.mapper.LoginticketMapper;
import com.fr.workorder.service.LoginticketService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fr.workorder.utils.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginticketServiceImpl extends ServiceImpl<LoginticketMapper, Loginticket> implements LoginticketService {

    @Autowired
    RedisTemplate redisTemplate;

    public Loginticket findLoginTicket(String ticket){
        String redisKey = RedisKeyUtil.generateTicketKey(ticket);
        return (Loginticket) redisTemplate.opsForValue().get(redisKey);
    }
}
