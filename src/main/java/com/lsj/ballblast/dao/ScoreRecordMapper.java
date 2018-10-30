package com.lsj.ballblast.dao;

import com.lsj.ballblast.model.ScoreRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ScoreRecordMapper {
    void insret(@Param("scoreRecord") ScoreRecord scoreRecord);

    ScoreRecord findByUid(@Param("uid") Integer uid);
}
