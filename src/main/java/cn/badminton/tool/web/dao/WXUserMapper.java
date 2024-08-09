package cn.badminton.tool.web.dao;

import cn.badminton.tool.web.entity.WXUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface WXUserMapper extends BaseMapper<WXUserEntity> {

    WXUserEntity selectWxUserByUid(Long uid);
}
