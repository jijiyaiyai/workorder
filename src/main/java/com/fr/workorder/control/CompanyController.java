package com.fr.workorder.control;

import com.fr.workorder.entity.Company;
import com.fr.workorder.entity.Workorder;
import com.fr.workorder.service.CompanyService;
import com.fr.workorder.utils.GlobalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    CompanyService companyService;

    // 用于初始化某个省的数据，仅供开发人员使用，非常危险，使用完应禁用
//    @GetMapping("/init/{province}")
//    public String init(@PathVariable("province")String province) throws IOException, ClassNotFoundException {
//
//        ObjectInputStream objectInputStream = new ObjectInputStream(new ClassPathResource(province+"tree").getInputStream());
//        Company c = (Company) objectInputStream.readObject();
//        while (c != null) {
//            companyService.insert(c);
//            c = (Company) objectInputStream.readObject();
//        }
//        objectInputStream.close();
//
//        return GlobalUtil.getJSONString(0);
//    }

}
