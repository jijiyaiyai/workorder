package com.fr.workorder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 唯一标识用户的ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    private String userId;

    private String userName;

    /**
     * 0-管理员，1-维修人员，2-申请人
     */
    private Integer userType;

    private String password;

    /**
     * true-已激活
     */
    private Boolean activated;

    /**
     * 单位的ID
     */
    private String companyId;

    /**
     * 部门ID，1-电力电子部，2-水道土木部，3-网络部，4-行政人力部，5-研发中心，6-施工部
     */
    private Integer departmentId;

    private String phone;

    private String email;


}
