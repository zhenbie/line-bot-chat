package com.linecorp.bot.spring.boot.mapper.extra;

import com.linecorp.bot.spring.boot.pojo.dto.UserShareInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description TODO
 * @Author zhenbaolei
 * @Date 2020/7/6 12:53
 * @Version 1.0
 */
public interface UserShareLogExtraMapper {

    /**
     * 根据分享人的userId获取分享日志
     *
     * @param fromUserId
     * @return
     */
    List<UserShareInfoDto> queryUserShareLogByUserId(@Param("fromUserId") String fromUserId);
}
