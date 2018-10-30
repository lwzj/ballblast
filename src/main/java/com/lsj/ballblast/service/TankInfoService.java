package com.lsj.ballblast.service;

import com.lsj.ballblast.dto.Result;
import com.lsj.ballblast.model.TankInfo;
import org.springframework.stereotype.Service;

@Service
public interface TankInfoService {
    int insret(Integer uid);

    TankInfo findByUid(Integer uid);

    Result passLevel(Integer uid, Integer gold, Integer score, Integer time);

    Result death(Integer uid, Integer gold, Integer score, Integer time);

    Result upPower(Integer uid);

    Result upSpeed(Integer uid);

    Result upGrade(Integer uid);
}
