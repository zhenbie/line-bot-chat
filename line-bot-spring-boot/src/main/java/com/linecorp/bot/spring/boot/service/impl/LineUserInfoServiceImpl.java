package com.linecorp.bot.spring.boot.service.impl;

import com.linecorp.bot.spring.boot.mapper.LineUserInfoMapper;
import com.linecorp.bot.spring.boot.pojo.entity.LineUserInfo;
import com.linecorp.bot.spring.boot.pojo.entity.LineUserInfoExample;
import com.linecorp.bot.spring.boot.service.ILineUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description TODO
 * @Author zhenbaolei
 * @Date 2020/7/6 9:25
 * @Version 1.0
 */
@Service
public class LineUserInfoServiceImpl implements ILineUserInfoService {

    @Autowired
    private LineUserInfoMapper lineUserInfoMapper;

    @Override
    public int saveLineUserInfo(LineUserInfo lineUserInfo) {
        return lineUserInfoMapper.insert(lineUserInfo);
    }

    @Override
    public int updateLineUserInfo(LineUserInfo lineUserInfo) {
        return lineUserInfoMapper.updateByPrimaryKey(lineUserInfo);
    }

    @Override
    public LineUserInfo getLineUserInfoByUserId(String userId) {
        LineUserInfoExample example = new LineUserInfoExample();
        example.createCriteria().andUserIdEqualTo(userId);
        List<LineUserInfo> lineUserInfoList = lineUserInfoMapper.selectByExample(example);
        if (lineUserInfoList.size() > 0) {
            return lineUserInfoList.get(0);
        }
        return null;
    }
}
