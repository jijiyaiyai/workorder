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
public class Report implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "report_id", type = IdType.AUTO)
    private Integer reportId;

    /**
     * 报表类型：1-每小时报表，2-每日报表，3-每周报表 
     */
    private Integer reportType;

    /**
     * 生成时间
     */
    private Date reportTime;

    /**
     * 统计的起始时间
     */
    private Date reportStartTime;

    /**
     * 统计的结束时间
     */
    private Date reportEndTime;

    /**
     * 超时的工单数目
     */
    private Integer overtime;

    /**
     * 超时工单的具体ID，以英文逗号分隔
     */
    private String overtimeOrderId;

    /**
     * 完成工单的数目
     */
    private Integer completion;

    /**
     * 完成工单的具体ID，以英文逗号分隔
     */
    private String completionOrderId;


}
