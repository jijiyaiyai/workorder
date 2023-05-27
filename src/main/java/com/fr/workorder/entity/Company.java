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
public class Company implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 前两位是省/直辖市的拼音首字母（大写），3、4位是市编号，5、6位是区县编号，7、8位是镇级编号
     */
    @TableId(value = "company_id")
    private String companyId;

    /**
     * 统一以“xx省xx市xx区xx镇分公司命名”，没有哪一级就去掉哪一级。
示例：北京市海淀区分公司，广东省东莞市大朗镇分公司，广东省深圳市宝安区分公司
     */
    private String companyName;

    /**
     * 1-省/直辖市，2-市，3-区/县，4-镇
     */
    private Integer companyLevel;

    private String province;

    private String city;

    private String district;

    private String town;

    private String parentCompanyId;

    private String childCompanyId;

    public Company(){

    }
    public Company(String companyId, String companyName, Integer companyLevel, String province, String city, String district, String town, String parentCompanyId, String childCompanyId) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.companyLevel = companyLevel;
        this.province = province;
        this.city = city;
        this.district = district;
        this.town = town;
        this.parentCompanyId = parentCompanyId;
        this.childCompanyId = childCompanyId;
    }
}
