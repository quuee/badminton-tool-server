package cn.badminton.tool.web.vo;

import lombok.Data;


@Data
public class RaceRefereeVO {

    private Long uid;

    private String openid;

    private String nickName;

    private String avatarUrl;

    private Integer gender;

    private Integer level;

    private boolean master;
}
