package cn.badminton.tool.web.params;

import lombok.Data;

@Data
public class BattleScoreParam {

//    private Long raceId;

    private Long bid;

    private Long refereeUid;

    private Integer score1;

    private Integer score2;

}
