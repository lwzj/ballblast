package com.lsj.ballblast.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.Set;

/**
 * 描述
 *
 * @author Y.S.K
 * @date 2018/9/5 13:49
 */
@Mapper
public interface AppMapper {
    String selectSh();

    void updateGuide(@Param("uid") Integer uid, @Param("guideType") String guideType);

    void insertButtonClick(@Param("uid") Integer uid, @Param("buttonName") String buttonName, @Param("pageName") String pageName, @Param("duration")
            Integer duration, @Param("version") String version);

    void updateActiveTime(@Param("uids") Set<Integer> activeUids);

    //    void updateGuide(@Param("date") LocalDateTime date, @Param("guideType") String guideType, @Param("number") Integer number);
    void gameLaunch(@Param("channelId") String channelId, @Param("date") LocalDate date, @Param("num") Integer num);

    void delete(@Param("uid") Integer uid);


}
