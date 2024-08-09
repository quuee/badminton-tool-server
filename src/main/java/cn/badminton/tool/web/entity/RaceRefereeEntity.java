package cn.badminton.tool.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

/**
 * 比赛裁判
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tb_race_referee")
public class RaceRefereeEntity {

    @TableId(value = "rid",type = IdType.AUTO)
    private Long rid;

    @TableField("race_id")
    private Long raceId;

    @TableField("uid")
    private Long uid;

    /**
     * false 申请中
     * true 通过
     */
    @TableField("master")
    private boolean master;
}
