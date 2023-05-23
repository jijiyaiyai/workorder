package com.fr.workorder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Loginticket implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ticket_id", type = IdType.AUTO)
    private Integer ticketId;

    private String userId;

    private String ticket;

    /**
     * 1-有效，0-无效
     */
    private Boolean status;

    /**
     * 令牌到期时间
     */
    private Date expiredTime;


}
