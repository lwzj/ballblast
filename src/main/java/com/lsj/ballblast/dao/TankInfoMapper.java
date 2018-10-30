package com.lsj.ballblast.dao;

import com.lsj.ballblast.model.TankInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TankInfoMapper {
    int insret(@Param("tank") TankInfo tank);

    TankInfo findByUid(@Param("uid") Integer uid);

    void updateLevel(@Param("uid") Integer uid, @Param("gold") Integer gold,@Param("gameLevel") Integer gameLevel);

    void upGrade(@Param("tank") TankInfo tank);
}
