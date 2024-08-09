package cn.badminton.tool.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "wx")
public class WXProperties {
    private String jscode2session_url;

    private String appid;

    private String secret;

    private String grant_type;

    public String getJscode2session_url() {
        return jscode2session_url;
    }

    public void setJscode2session_url(String jscode2session_url) {
        this.jscode2session_url = jscode2session_url;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }
}
