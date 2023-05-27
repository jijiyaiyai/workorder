package com.fr.workorder.service.impl;

import com.fr.workorder.entity.Company;
import com.fr.workorder.entity.Loginticket;
import com.fr.workorder.entity.User;
import com.fr.workorder.mapper.CompanyMapper;
import com.fr.workorder.mapper.UserMapper;
import com.fr.workorder.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fr.workorder.utils.Constants;
import com.fr.workorder.utils.MailClient;
import com.fr.workorder.utils.RedisKeyUtil;
import com.fr.workorder.utils.GlobalUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.util.*;


@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, Constants {

    @Autowired
    UserMapper userMapper;

    @Autowired
    CompanyMapper companyMapper;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    private MailClient mailClient;

    @Value("${workorder.path.domain}") //获取网站的域名
    private String domain;

    @Value("${server.servlet.context-path}") //获取项目的应用路径
    private String contextPath;

    /**
      获取登录的ticket
     */
    public Loginticket findLoginTicket(String ticket){
        String redisKey = RedisKeyUtil.generateTicketKey(ticket);
        return (Loginticket) redisTemplate.opsForValue().get(redisKey);
    }

    /**
     获取用户权限标识，转化为字符串
     */
    public Collection<? extends GrantedAuthority> getAuthorities(String userId) {
        User user = this.getById(userId);

        List<GrantedAuthority> list = new ArrayList<>();
        list.add((GrantedAuthority) () -> {
            switch (user.getUserType()) {
                case 0:
                    return AUTHORITY_ADMIN;
                case 1:
                    return AUTHORITY_FIXER;
                default:
                    return AUTHORITY_APPLIER;
            }
        });
        return list;
    }

    /**
     生成下一个userId
     */
    @Transactional
    public String generateNextUserId(String companyId, int departmentId){
        // 加上%模糊查询
        String res = userMapper.selectLastId(companyId+departmentId+"%");
        if(res == null)return companyId+departmentId+"0001";
        // 读取最后四位数，并且+1
        String[] split = res.split(companyId+departmentId);
        int id = Integer.parseInt(split[0]);
        return companyId+departmentId+String.format("%04d",id+1);
    }

    /**
     传入User对象（也就是在表单那里封装好，提交上来）然后进行各项空值的判断等等
     */
    public Map<String, Object> register(
            String userName, String password, String userEmail,
            String userType, String companyName, int departmentId
    ) throws MessagingException {
        Map<String, Object> map = new HashMap<>();

        // 空值处理
        if (StringUtils.isBlank(userName)) {
            map.put("nameMsg", "姓名不能为空!");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }
        if (StringUtils.isBlank(userEmail)) {
            map.put("emailMsg", "邮箱不能为空!");
            return map;
        }
        if (StringUtils.isBlank(userType)) {
            map.put("typeMsg", "用户类型不能为空!");
            return map;
        }
        int type = 0;
        switch (userType){
            case "admin": break;
            case "fixer": type = 1; break;
            case "applier": type = 2; break;
            default:
                map.put("typeMsg", "用户类型非法");
                return map;
        }
        if (StringUtils.isBlank(companyName)) {
            map.put("companyMsg", "公司不能为空!");
            return map;
        }
        Company company = companyMapper.selectByCompanyName(companyName);
        if(company == null){
            map.put("companyMsg", "公司不存在!");
            return map;
        }
        if(departmentId > 6 || departmentId < 0){
            map.put("departmentMsg", "该部门不存在!");
            return map;
        }

        // 验证账号
        User u = userMapper.selectByEmail(userEmail);
        if (u != null) {
            map.put("userMsg", "该邮箱已被注册!");
            return map;
        }

        // 注册用户
        User newUser = new User();
        newUser.setUserId(generateNextUserId(company.getCompanyId(), departmentId));
        newUser.setUserName(userName);
        newUser.setUserType(type);
        //往他的密码中加盐生成md5码
        newUser.setPassword(GlobalUtil.md5(password + salt));
        newUser.setCompanyId(company.getCompanyId());
        newUser.setDepartmentId(departmentId);
        newUser.setActivated(false);
        //激活码
        newUser.setActivationCode(GlobalUtil.generateUUID());
        userMapper.insert(newUser);

        // 激活邮件
        // 生成激活链接
        // http://localhost:8080/workorder/activation/101/code
        String url = domain + contextPath + "/activation/" +
                newUser.getUserId() + "/" + newUser.getActivationCode();
        String content = "您正在注册workorder系统，点击以下链接即可激活账号："+url;
        mailClient.sendMail(newUser.getEmail(), "运维工单处理系统官方邮件-激活账号", content);

        return map;
    }

    /**
      激活
     */
    public int activation(String userId, String code) {
        User user = userMapper.selectById(userId);
        //查询当前这个用户，如果已经存在说明是重复激活了
        if (user.getActivated()) {
            return ACTIVATION_REPEAT;
        }
        //不然就对比激活码，相同才能激活
        else if (user.getActivationCode().equals(code)) {
            userMapper.updateActivated(userId, true);
            return ACTIVATION_SUCCESS;
        } else {
            return ACTIVATION_FAILURE;
        }
    }

    /**
      登录
     */
    public Map<String, Object> login(String userEmail, String password, int expiredSeconds) {
        Map<String, Object> map = new HashMap<>();

        // 空值处理
        if (StringUtils.isBlank(userEmail)) {
            map.put("userEmailMsg", "邮箱不能为空!");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }
        // 验证账号
        User user = userMapper.selectByEmail(userEmail);
        if (user == null) {
            map.put("userMsg", "该账号不存在!");
            return map;
        }

        // 验证状态
        if (!user.getActivated()) {
            map.put("userMsg", "该账号未激活!");
            return map;
        }

        // 验证密码
        // 对密码加盐然后md5加密
        password = GlobalUtil.md5(password + salt);
        if (!user.getPassword().equals(password)) {
            map.put("passwordMsg", "密码不正确!");
            return map;
        }

        // 生成登录凭证
        Loginticket loginTicket = new Loginticket();
        loginTicket.setUserId(user.getUserId());
        loginTicket.setTicket(GlobalUtil.generateUUID());
        loginTicket.setStatus(true);
        loginTicket.setExpiredTime(new Date(System.currentTimeMillis() + expiredSeconds * 1000L));

        String redisKey = RedisKeyUtil.generateTicketKey(loginTicket.getTicket());
        redisTemplate.opsForValue().set(redisKey,loginTicket); // redis会序列化ticket对象

        map.put("ticket", loginTicket.getTicket());

        return map;
    }

}
