package com.fr.workorder.mapper;

import com.fr.workorder.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    User selectByEmail(String email);

    String selectLastId(String companyIdAndDepartmentId);

    void updateActivated(String userId, boolean b);
}
