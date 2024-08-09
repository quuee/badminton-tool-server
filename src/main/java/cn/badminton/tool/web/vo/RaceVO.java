package cn.badminton.tool.web.vo;

import cn.badminton.tool.support.auth.WXAppletAuthenticationToken;
import cn.badminton.tool.web.entity.RaceSchemeEntity;
import cn.badminton.tool.web.entity.WXUserEntity;
import lombok.Data;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.List;

@Data
public class RaceVO {

    private Long raceId;

    private String raceTitle;

    private Integer raceState;

    private Date raceDateTime;

    private Integer raceMainType;

    private Long schemeId;

    private Integer raceBOX;

    private Integer raceScoreMode;

    private Long organizerUid;

    private Integer predictApplyNum;

    private Integer everyPlayTimes;

    private Integer genderLimit;

    private Integer fieldNum;

    private String address;

    private String addContext;

    private boolean defaultReferee;

    private Date createTime;

    private Integer battleGenerateAlgorithm;


    /**
     * 比赛类型
     */
    private RaceSchemeEntity raceScheme;

    /**
     * 组织者
     */
    private WXUserEntity organizer;


    /**
     * 报名人数
     */
    private List<WXUserEntity> applicants;

    /*
     * 排名
     */
//    private List<RaceRankEntity> rank;

    /**
     * 对阵数据
     */
//    private List<RaceBattleEntity> battles;


    private boolean organization;

    public boolean getApplied() {
        WXAppletAuthenticationToken principal = (WXAppletAuthenticationToken) SecurityContextHolder.getContext().getAuthentication().getDetails();
        for (WXUserEntity applicant : applicants) {
            int i = applicant.getUid().compareTo(principal.getUid());
            if (i == 0) {
                return true;
            }
        }
        return false;
    }
}
