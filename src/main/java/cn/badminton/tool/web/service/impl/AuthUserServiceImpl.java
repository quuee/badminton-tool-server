package cn.badminton.tool.web.service.impl;

import cn.badminton.tool.web.dao.WXUserMapper;
import cn.badminton.tool.web.entity.WXUserEntity;
import cn.badminton.tool.web.service.AuthUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service("authUserService")
public class AuthUserServiceImpl extends ServiceImpl<WXUserMapper, WXUserEntity> implements AuthUserService {

    @Override
    public boolean registerOrLogin(WXUserEntity user) {
        if(!StringUtils.hasText(user.getOpenid())){
            return false;
        }
        //判断是否存在
        LambdaQueryWrapper<WXUserEntity> wrapper = new LambdaQueryWrapper<WXUserEntity>().eq(WXUserEntity::getOpenid, user.getOpenid());
        WXUserEntity selectOne = baseMapper.selectOne(wrapper);
        if (selectOne == null) {
            selectOne.setNickName(user.getNickName());
            selectOne.setAvatarUrl(user.getAvatarUrl());
            selectOne.setRegisterDate(new Date());
            selectOne.setLastLoginDate(new Date());
            baseMapper.insert(selectOne);
        }else {
            selectOne.setLastLoginDate(new Date());
            baseMapper.updateById(selectOne);
        }
        return true;
    }

    @Override
    public WXUserEntity getByOpenid(String openid) {
        LambdaQueryWrapper<WXUserEntity> wrapper = new LambdaQueryWrapper<WXUserEntity>().eq(WXUserEntity::getOpenid, openid);
        WXUserEntity selectOne = baseMapper.selectOne(wrapper);
        return selectOne;
    }
}
