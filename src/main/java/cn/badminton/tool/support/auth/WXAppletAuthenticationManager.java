//package cn.badminton.tool.support.auth;
//
//import com.xxx.xcx01_server.entity.UserEntity;
//import com.xxx.xcx01_server.service.UserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class WXAppletAuthenticationManager implements AuthenticationManager {
//
//    @Autowired
//    private UserService userService;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        WXAppletAuthenticationToken token = null;
//        if(authentication instanceof WXAppletAuthenticationToken){
//            token = (WXAppletAuthenticationToken)authentication;
//        }
//
//        UserEntity user = new UserEntity();
//        user.setOpenid(token.getOpenid());
//        user.setNickName(token.getNickName());
//        user.setAvatarUrl(token.getAvatarUrl());
//        userService.saveOrUpdateWXUser(user);
//
//        return new WXAppletAuthenticationToken(user.getOpenid(),user.getId(),user.getNickName(),user.getAvatarUrl());
//    }
//
//
//}
