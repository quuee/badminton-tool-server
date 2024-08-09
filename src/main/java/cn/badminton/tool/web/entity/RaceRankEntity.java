//package cn.badminton.tool.web.entity;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableField;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import lombok.*;
//
///**
// * 记录排名
// * 在裁判记录完比赛后，更新该表
// */
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@TableName("tb_race_rank")
//public class RaceRankEntity {
//
//    @TableId(value = "rid",type = IdType.AUTO)
//    private Long rid;
//
//    @TableField("race_id")
//    private Long raceId;
//
//    @TableField("uid")
//    private Long uid;
//
//    @TableField("victories")
//    private Integer victories;
//
//    @TableField("failures")
//    private Integer failures;
//
//    /**
//     * 积分
//     * 胜 得2分
//     * 负 得0分
//     */
//    @TableField("odds")
//    private Integer odds;
//
//    /**
//     * 净胜分
//     * 胜利方得分-失败方得分
//     * 如A VS B，比分 21:17
//     * A胜分4，B胜分-4
//     */
//    @TableField("odds_difference")
//    private Integer oddsDifference;
//
//
//}
