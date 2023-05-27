package com.fr.workorder.control;

import com.fr.workorder.entity.User;
import com.fr.workorder.entity.Workorder;
import com.fr.workorder.service.WorkorderService;
import com.fr.workorder.utils.GlobalUtil;
import com.fr.workorder.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/workorder")
public class WorkorderController {

    @Autowired
    WorkorderService workorderService;

    @Autowired
    HostHolder hostHolder;

    @GetMapping("/{workorderId}")
    public String findWorkorder(@PathVariable("workorderId")String WorkOrderId){
        Map<String,Object> data = new HashMap<>();
        Workorder wo = workorderService.getById(WorkOrderId);
        // 查找不到这样的工单
        if(wo == null){
            return GlobalUtil.getJSONString(1);
        }
        data.put("workorder", wo);
        return GlobalUtil.getJSONString(0,null,data);
    }

    @PostMapping("")
    public String submitWorkorder(
            @RequestParam("workOrderType") Integer workOrderType,
            @RequestParam("workOrderTitle")String workOrderTitle,
            @RequestParam("workOrderDetails")String workOrderDetails, @RequestParam("suggestLevel")Integer suggestLevel,
            @RequestParam("suggestDepartment")Integer suggestDepartment,
            @RequestParam("submitTime")Date submitTime, @RequestParam("dueTime")Date dueTime){
        User user = hostHolder.getUser();
        Workorder workorder = new Workorder(null, user.getUserId(),workOrderType, 1, workOrderTitle, workOrderDetails, suggestLevel, suggestDepartment, submitTime, dueTime,null,false);
        workorderService.save(workorder);
        return GlobalUtil.getJSONString(0);
    }
}
