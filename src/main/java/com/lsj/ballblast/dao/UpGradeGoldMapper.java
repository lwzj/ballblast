package com.lsj.ballblast.dao;

import com.lsj.ballblast.model.UpgradeGold;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UpGradeGoldMapper {
    UpgradeGold findGoldByLevel(@Param("level") Integer level);
}
