package com.fr.workorder.Utils;

public class RedisKeyUtil {
    private static final String SPLIT = ":";
    private static final String PREFIX_TICKET = "ticket";


    //生成登录凭证的key
    public static String generateTicketKey(String ticket) {
        return PREFIX_TICKET + SPLIT + ticket;
    }

}
