package cn.badminton.tool.support.auth;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class WXAppletAuthenticationToken extends AbstractAuthenticationToken {

    private String openid;

//    private String sessionKey;

    private String nickName;

    private String avatarUrl;

    private Long uid;

    /**
     * 生成未认证的AuthenticationToken
     * @param openid
     */
    public WXAppletAuthenticationToken(String openid,String nickName,String avatarUrl) {
        super(null);
        this.openid = openid;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
        super.setAuthenticated(false);
    }

    /**
     * 生成已认证的AuthenticationToken
     * @param openid
     * @param uid
     * @param nickName
     * @param avatarUrl
     */
    public WXAppletAuthenticationToken(String openid, Long uid, String nickName, String avatarUrl) {
        super(null);
        this.openid = openid;
        this.uid = uid;
        this.nickName = nickName;
        this.avatarUrl = avatarUrl;
        super.setAuthenticated(true);
    }

    public WXAppletAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    @Override
    public Object getCredentials() {
        return this.openid;
    }

    @Override
    public Object getPrincipal() {
        return this.nickName;
    }


    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

//    public String getSessionKey() {
//        return sessionKey;
//    }

//    public void setSessionKey(String sessionKey) {
//        this.sessionKey = sessionKey;
//    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public WXAppletAuthenticationToken getDetails() {
        return this;
    }
}
