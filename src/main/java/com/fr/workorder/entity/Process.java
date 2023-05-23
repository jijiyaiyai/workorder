package com.fr.workorder.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author ${author}
 * @since 2023-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Process implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "process_id", type = IdType.AUTO)
    private Integer processId;

    private Integer workOrderId;

    private Date processTime;

    /**
     * 维修人员ID
     */
    private String processorId;

    private String companyId;

    /**
     * 1-电力电子部，2-水道土木部，3-网络部
     */
    private Integer departmentId;

    /**
     * 1-处理完毕，2-需要协助
     */
    private Integer processResult;

    private String processRemark;


}
