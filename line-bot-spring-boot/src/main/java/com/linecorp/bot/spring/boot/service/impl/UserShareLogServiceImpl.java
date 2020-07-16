package com.linecorp.bot.spring.boot.service.impl;

import com.linecorp.bot.spring.boot.mapper.UserShareLogMapper;
import com.linecorp.bot.spring.boot.mapper.extra.UserShareLogExtraMapper;
import com.linecorp.bot.spring.boot.pojo.dto.UserShareInfoDto;
import com.linecorp.bot.spring.boot.pojo.entity.UserShareLog;
import com.linecorp.bot.spring.boot.pojo.entity.UserShareLogExample;
import com.linecorp.bot.spring.boot.service.IUserShareLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author zhenbaolei
 * @Date 2020/7/6 9:26
 * @Version 1.0
 */
@Service
public class UserShareLogServiceImpl implements IUserShareLogService {

    @Autowired
    private UserShareLogMapper userShareLogMapper;

    @Autowired
    private UserShareLogExtraMapper userShareLogExtraMapper;

    @Override
    public int saveUserShareLog(UserShareLog userShareLog) {
        return userShareLogMapper.insert(userShareLog);
    }

    @Override
    public int updateUserShareLog(UserShareLog userShareLog) {
        return userShareLogMapper.updateByPrimaryKey(userShareLog);
    }

    @Override
    public List<UserShareLog> queryUserShareLogByUserId(String userId) {
        UserShareLogExample example = new UserShareLogExample();
        example.createCriteria().andFromUserIdEqualTo(userId);
        return userShareLogMapper.selectByExample(example);
    }

    @Override
    public List<UserShareLog> queryUserShareLogByUserIdAndShareUri(String userId, String shareUri) {
        UserShareLogExample example = new UserShareLogExample();
        example.createCriteria().andFromUserIdEqualTo(userId).andShareUriEqualTo(shareUri);
        return userShareLogMapper.selectByExample(example);
    }

    @Override
    public List<UserShareLog> queryUserShareLogByUserIdAndShareUriAndToUserId(String fromUserId, String shareUri, String toUserId) {
        UserShareLogExample example = new UserShareLogExample();
        example.createCriteria()
                .andFromUserIdEqualTo(fromUserId)
                .andShareUriEqualTo(shareUri)
                .andToUserIdEqualTo(toUserId);
        return userShareLogMapper.selectByExample(example);
    }

    @Override
    public List<UserShareLog> queryUserShareLogShareUriAndToUserId(String shareUri, String toUserId) {
        UserShareLogExample example = new UserShareLogExample();
        example.createCriteria()
                .andShareUriEqualTo(shareUri)
                .andToUserIdEqualTo(toUserId);
        return userShareLogMapper.selectByExample(example);
    }

    @Override
    public List<UserShareInfoDto> queryUserShareLogByFromUserId(String fromUserId) {
        return userShareLogExtraMapper.queryUserShareLogByUserId(fromUserId);
    }
}
