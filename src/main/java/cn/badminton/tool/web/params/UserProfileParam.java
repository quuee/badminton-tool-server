package cn.badminton.tool.web.params;

import lombok.Data;

@Data
public class UserProfileParam {

    private String nickName;

    private String avatarUrl;

    private Integer gender = 0;

    private Integer level;
}
