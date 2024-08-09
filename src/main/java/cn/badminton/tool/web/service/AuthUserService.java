package cn.badminton.tool.web.service;

import cn.badminton.tool.web.entity.WXUserEntity;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AuthUserService extends IService<WXUserEntity> {

    /**
     * 微信 用户登录时 新增或保存
     * @param user
     * @return
     */
    boolean registerOrLogin(WXUserEntity user);

    WXUserEntity getByOpenid(String openid);

}
