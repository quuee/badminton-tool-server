package cn.badminton.tool.web.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * 报名者
 */
@Data
@TableName("tb_race_applicant")
@Builder
public class RaceApplicantEntity {

    @TableId(value = "apply_id",type = IdType.AUTO)
    private Long applyId;

    @TableField("race_id")
    private Long raceId;

    @TableField("uid")
    private Long uid;
}
