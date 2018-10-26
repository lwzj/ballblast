package com.lsj.ballblast.dao;

import com.lsj.ballblast.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findOneByOpenId(String openId);
}
