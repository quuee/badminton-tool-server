package cn.badminton.tool.web.params;

import lombok.Data;

@Data
public class StartRaceParam {

    private Long raceId;

    /**
     * 每人N场
     */
    private Integer playerSession;

    private Integer fieldNum;

    /**
     * 具体对阵算法：随机 势均力敌 报名顺序
     */
    private Integer battleGenerateAlgorithm;
}
