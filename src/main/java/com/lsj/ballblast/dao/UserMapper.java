package com.lsj.ballblast.dao;

import com.lsj.ballblast.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User findOneByOpenId(@Param("openId") String openId);

    int insert(@Param("user") User user);
}
