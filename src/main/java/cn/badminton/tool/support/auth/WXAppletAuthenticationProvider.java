package cn.badminton.tool.support.auth;

import cn.badminton.tool.web.entity.WXUserEntity;
import cn.badminton.tool.web.service.AuthUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class WXAppletAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    @Qualifier("authUserService")
    private AuthUserService authUserService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        WXAppletAuthenticationToken token = null;
        if (authentication instanceof WXAppletAuthenticationToken) {
            token = (WXAppletAuthenticationToken) authentication;
        }

//        WXUserEntity user = new WXUserEntity();
//        user.setOpenid(token.getOpenid());
//        user.setNickName(token.getNickName());
//        user.setAvatarUrl(token.getAvatarUrl());
        WXUserEntity user = WXUserEntity.builder()
                .openid(token.getOpenid())
                .nickName(token.getNickName())
                .avatarUrl(token.getAvatarUrl())
                .uid(token.getUid())
                .build();
        authUserService.registerOrLogin(user);

        return new WXAppletAuthenticationToken(user.getOpenid(), user.getUid(), user.getNickName(), user.getAvatarUrl());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return WXAppletAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
