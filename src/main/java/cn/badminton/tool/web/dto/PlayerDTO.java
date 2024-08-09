package cn.badminton.tool.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@EqualsAndHashCode
@EqualsAndHashCode(exclude = {"everyonePlayTimes"})
@Builder
public class PlayerDTO {

    private Long uid;

    private String openid;

    private String nickName;

    private String avatarUrl;

    private Integer level;

    /**
     * 需要出场的次数
     */
    private Integer everyonePlayTimes;
}
