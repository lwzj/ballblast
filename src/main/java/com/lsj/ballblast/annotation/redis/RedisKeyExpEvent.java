package com.lsj.ballblast.annotation.redis;

import lombok.Getter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

/**
 * 描述
 *
 * @author Y.S.K
 * @date 2018/8/20 18:15
 */
@Getter
@ToString
public class RedisKeyExpEvent extends ApplicationEvent {
    private String redisMessage;

    public RedisKeyExpEvent(Object source, String redisMessage) {
        super(source);
        this.redisMessage = redisMessage;
    }
}
