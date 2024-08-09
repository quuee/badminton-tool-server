package cn.badminton.tool.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Date;

/**
 * 比赛主体
 */
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Builder
@TableName("tb_race_main")
public class RaceMainEntity {

    @TableId(value = "race_id",type = IdType.AUTO)
    private Long raceId;

    @TableField("race_title")
    private String raceTitle;

    /**
     * 报名中
     * 暂停报名
     * 比赛中
     * 已结束
     */
    @TableField("race_state")
    private Integer raceState;

    @TableField("race_date_time")
    private Date raceDateTime;

    /**
     * 1双打 2单打 3对抗 4团体
     */
    @TableField("race_main_type")
    private Integer raceMainType;

    /**
     * 单打 双打 团体小组、对抗
     */
    @TableField("scheme_id")
    private Long schemeId;

    /**
     * 一局定胜负、三局两胜、五局三胜
     */
    @TableField("raceBOX")
    private Integer raceBOX;

    /**
     * 11分 15分 21分
     */
    @TableField("race_score_mode")
    private Integer raceScoreMode;

    /**
     * 组织者
     */
    @TableField("organizer_uid")
    private Long organizerUid;

    /**
     * 预计参与人数
     */
    @TableField("predict_apply_num")
    private Integer predictApplyNum;

    /**
     * 根据人数选择 每人上场次数 默认A-1，6、7特殊情况
     */
    @TableField("every_play_times")
    private Integer everyPlayTimes;

    /**
     * 性别限制：不限、男、女
     */
    @TableField("gender_limit")
    private Integer genderLimit;

    /**
     * 场地数：开启比赛时选择
     */
    @TableField("field_num")
    private Integer fieldNum;

    /**
     * 应是 地址+点位
     */
    @TableField("address")
    private String address;

    /**
     * 补充
     */
    @TableField("add_context")
    private String addContext;

    @TableField("create_time")
    private Date createTime;

    /**
     * 默认 空闲选手可申请为裁判
     */
    @TableField("default_referee")
    private boolean defaultReferee;

    /**
     * 对阵算法
     * 随机、报名先手、势均力敌
     */
    @TableField("battle_generate_algorithm")
    private Integer battleGenerateAlgorithm;
}
