package cn.badminton.tool.web.vo;

import cn.badminton.tool.web.entity.WXUserEntity;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RaceBattleVO {

    private Long bid;

    private Long raceId;
    private Long raceMainType;

    private Integer roundNum;

    private WXUserEntity player1;
    private WXUserEntity player2;
//    private Long playerUid1;
//    private Long playerUid2;
    private Integer firstPartnerScore;

    private WXUserEntity player3;
    private WXUserEntity player4;
//    private Long playerUid3;
//    private Long playerUid4;
    private Integer secondPartnerScore;

    private Integer battleState;

//    private Long trialUid;
    private RaceRefereeVO referee;

}
