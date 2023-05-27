package com.fr.workorder.utils;

public interface Constants {

    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS = 0;

    /**
     * 重复激活
     */
    int ACTIVATION_REPEAT = 1;

    /**
     * 激活失败
     */
    int ACTIVATION_FAILURE = 2;

    // 三种用户：管理员、维修人员、申请人
    String AUTHORITY_ADMIN = "admin";
    String AUTHORITY_FIXER = "fixer";
    String AUTHORITY_APPLIER = "applier";

    /**
     * 默认状态的登录凭证的超时时间
     */
    int DEFAULT_EXPIRED_SECONDS = 3600 * 12;

    /*
     * 密码加盐
     */
    String salt = "73737";

    // 请求成功被处理
    int success = 0;
    // 请求被成功处理，但请求的内容（登录、注册等）失败了
    int failed = 1;

}
