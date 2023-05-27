package com.fr.workorder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.print.DocFlavor;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Workorder implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "work_order_id", type = IdType.AUTO)
    private Integer workOrderId;

    private String userId;

    /**
     * 1-故障维修，2-其他需求
     */
    private Integer workOrderType;

    /**
     * 1-已提交，2-审核不通过，3-审核通过，4-已派单
     */
    private Integer workOrderStatus;

    private String workOrderTitle;

    private String workOrderDetails;

    /**
     * 需要的单位层级，1-省/直辖市，2-市，3-区/县，4-镇
     */
    private Integer suggestLevel;

    /**
     * 建议派出的部门，1-电力电子部，2-水道土木部，3-网络部
     */
    private Integer suggestDepartment;

    private Date submitTime;

    /**
     * 要求完成的时间，一般默认3天
     */
    private Date dueTime;

    /**
     * 实际完成的时间
     */
    private Date completeTime;

    /**
     * true-超时
     */
    private Boolean overdue;

    public Workorder(){

    }

    public Workorder(Integer workOrderId, String userId, Integer workOrderType, Integer workOrderStatus, String workOrderTitle, String workOrderDetails, Integer suggestLevel, Integer suggestDepartment, Date submitTime, Date dueTime, Date completeTime, Boolean overdue) {
        this.workOrderId = workOrderId;
        this.userId = userId;
        this.workOrderType = workOrderType;
        this.workOrderStatus = workOrderStatus;
        this.workOrderTitle = workOrderTitle;
        this.workOrderDetails = workOrderDetails;
        this.suggestLevel = suggestLevel;
        this.suggestDepartment = suggestDepartment;
        this.submitTime = submitTime;
        this.dueTime = dueTime;
        this.completeTime = completeTime;
        this.overdue = overdue;
    }
}
