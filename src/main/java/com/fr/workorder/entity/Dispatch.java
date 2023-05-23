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
public class Dispatch implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "dispatch_id", type = IdType.AUTO)
    private Integer dispatchId;

    private Integer workOrderId;

    private Date dispatchTime;

    /**
     * 派单人员id
     */
    private String dispatcherId;

    /**
     * 接受派单的单位ID
     */
    private String receiverCompanyId;

    /**
     * 接收派单的部门id，1-电力电子部，2-水道土木部，3-网络部
     */
    private Integer receiverDepartmentId;


}
