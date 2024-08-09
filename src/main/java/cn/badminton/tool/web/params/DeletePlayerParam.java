package cn.badminton.tool.web.params;

import lombok.Data;

@Data
public class DeletePlayerParam {

    private Long raceId;

    private Long playerId;
}
