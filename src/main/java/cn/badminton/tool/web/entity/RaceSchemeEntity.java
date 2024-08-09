package cn.badminton.tool.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 比赛具体方案
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@TableName("tb_race_scheme")
public class RaceSchemeEntity {

    /**
     * 生成对阵除了随机乱斗，还可以一个一个抽签配对上场，或者组合固定搭档再匹配对阵
     * 只是操作界面上不一样，实际算法差不多，只是将某步骤提前做好再对阵
     */

    @TableId(value = "scheme_id",type = IdType.AUTO)
    private Long schemeId;

    /**
     * 主 比赛类型
     */
    private Integer raceMainType;

    /**
     * 具体比赛方案
     * 双打：八人转、超八转、混双转、固定搭、自由转、单循环、双循环、前后转、擂台赛、晋级赛
     * 单打：单打转、自由转、单循环、双循环、擂台赛、晋级赛
     * 对抗：小队转、接力赛、自由配
     * 团体：分档赛、混合赛
     */
    @TableField("race_scheme")
    private String raceScheme;

    @TableField("simple_desc")
    private String simpleDesc;

    @TableField("detail_desc")
    private String detailDesc;

    @TableField("generate_class_name")
    private String generateClassName;

    /**
     * 最少参与人数
     */
    @TableField("min_players")
    private Integer minPlayers;

    /**
     * 每片场地可容纳的合理人数
     */
    @TableField("field_accommodate_player_num")
    private Integer fieldAccommodatePlayerNum;


}
