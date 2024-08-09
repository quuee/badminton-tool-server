package cn.badminton.tool.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Tolerate;

import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@TableName("tb_wx_user")
@Builder

//@EqualsAndHashCode(exclude = {"registerDate","lastLoginDate","avatarUrl"})
public class WXUserEntity {

    @TableId(value = "uid",type = IdType.AUTO)
    private Long uid;

    @TableField("openid")
    private String openid;

    @TableField("nick_name")
    private String nickName;

    @TableField("avatar_url")
    private String avatarUrl;

    @TableField("register_date")
    private Date registerDate;

    @TableField("last_login_date")
    private Date lastLoginDate;

    @TableField("gender")
    private Integer gender;

    @TableField("level")
    private Integer level;
}
