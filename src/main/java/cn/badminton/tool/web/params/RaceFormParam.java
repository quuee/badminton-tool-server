package cn.badminton.tool.web.params;

import lombok.Data;

@Data
public class RaceFormParam {
    private Long raceId;
    private String raceTitle;
    private Integer raceMainType;
    private Long schemeId;
    private String genderLimit;
    private Integer applicats;
    private Integer raceBOX;
    private Integer raceScoreMode;
    private String raceCalender;
    private String raceTime;
    private String raceAddress;
    private String addContext;
}
