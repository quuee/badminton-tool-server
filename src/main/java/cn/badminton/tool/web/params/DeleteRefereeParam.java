package cn.badminton.tool.web.params;

import lombok.Data;

@Data
public class DeleteRefereeParam {

    private Long raceId;

    private Long refereeId;
}
