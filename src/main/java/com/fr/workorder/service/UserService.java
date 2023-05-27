package com.fr.workorder.service;

import com.fr.workorder.entity.Loginticket;
import com.fr.workorder.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;


public interface UserService extends IService<User> {

    Loginticket findLoginTicket(String ticket);

    Collection<? extends GrantedAuthority> getAuthorities(String userId);
}
