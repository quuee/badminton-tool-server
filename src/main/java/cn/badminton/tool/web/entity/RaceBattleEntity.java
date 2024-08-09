package cn.badminton.tool.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 双打：playerUid1 playerUid2 playerUid3 playerUid4
 * 单打：playerUid1 playerUid1
 * 一局定胜负、三局两胜、五局三胜 ：再加几组firstPartnerScore secondPartnerScore
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName("tb_race_battle")
public class RaceBattleEntity {

    @TableId(value = "bid",type = IdType.AUTO)
    private Long bid;

    @TableField("race_id")
    private Long raceId;

    @TableField("race_main_type")
    private Integer raceMainType;

    // 轮数：第几轮
    @TableField("round_num")
    private Integer roundNum;

    @TableField("player_uid1")
    private Long playerUid1;
    @TableField("player_uid2")
    private Long playerUid2;
    @TableField("first_partner_score")
    private Integer firstPartnerScore;

    @TableField("player_uid3")
    private Long playerUid3;
    @TableField("player_uid4")
    private Long playerUid4;
    @TableField("second_partner_score")
    private Integer secondPartnerScore;

    /**
     * 未开始
     * 比赛中，裁判进入，就是比赛中。其他人进不去。组织者可以进入。直到裁判记完分
     * 已完成
     */
    @TableField("battle_state")
    private Integer battleState;

    /**
     * 裁判
     */
    @TableField("referee_uid")
    private Long refereeUid;
}
