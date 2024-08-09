package cn.badminton.tool.web.dto;

import java.util.Objects;

public class PayloadDTO {

    // 主题
    private String sub;
    //签发时间
    private Long iat;

    //用户名称
//    private String nickname;

    private String openid;

    public PayloadDTO() {
    }

    public PayloadDTO(String sub, Long iat, String openid) {
        this.sub = sub;
        this.iat = iat;
        this.openid = openid;
    }

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public Long getIat() {
        return iat;
    }

    public void setIat(Long iat) {
        this.iat = iat;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PayloadDTO that = (PayloadDTO) o;
        return Objects.equals(sub, that.sub) && Objects.equals(iat, that.iat) && Objects.equals(openid, that.openid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sub, iat, openid);
    }
}
