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
public class Approval implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "approval_id", type = IdType.AUTO)
    private Integer approvalId;

    private Integer workOrderId;

    private Date approvalTime;

    private String approverId;

    private String approvalCompany;

    /**
     * 需要的单位层级，1-省/直辖市，2-市，3-区/县，4-镇
     */
    private Integer approvalLevel;

    /**
     * true-通过
     */
    private Boolean approvalResult;

    /**
     * 备注
     */
    private String approvalRemark;


}
