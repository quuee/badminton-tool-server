package cn.badminton.tool.web.vo;

import cn.badminton.tool.web.entity.WXUserEntity;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaceRankVO {

    private Long rid;

    private Long raceId;

//    private Long uid;
    private WXUserEntity player;

    private Integer victories;
    private Integer failures;

    /**
     * 积分
     * 胜 得2分
     * 负 得0分
     */
    private Integer odds;

    /**
     * 净胜分
     * 胜利方得分-失败方得分
     * 如A VS B，比分 21:17
     * A胜分4，B胜分-4
     */
    private Integer oddsDifference;
}
