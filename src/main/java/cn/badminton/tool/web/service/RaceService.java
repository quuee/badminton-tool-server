package cn.badminton.tool.web.service;

import cn.badminton.tool.web.entity.RaceMainEntity;
import cn.badminton.tool.web.params.BattleScoreParam;
import cn.badminton.tool.web.params.BattleTrailerParam;
import cn.badminton.tool.web.params.RaceFormParam;
import cn.badminton.tool.web.params.RaceRefereeParam;
import cn.badminton.tool.web.vo.RaceRankVO;
import cn.badminton.tool.web.vo.RaceVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RaceService extends IService<RaceMainEntity> {

    Page<RaceVO> getRaceList(Long uid, Integer pageNo, Integer pageLimit);

    RaceMainEntity createRace(RaceFormParam raceFormParam);

    RaceMainEntity editRace(RaceFormParam raceFormParam);

    RaceVO getRaceInfo(Long raceId);

    /**
     *
     * @param raceId 比赛id
     * @param playerSession 每位选手上场次数
     * @param battleGenerate 对阵生成方式：随机、势均力敌
     * @param fieldNum 场地数
     * @return
     */
    RaceVO startRace(Long raceId,Integer playerSession,Integer battleGenerate,Integer fieldNum);


    List<RaceRankVO> getRaceRanks(Long raceId);

    boolean editBattleScore(BattleScoreParam param);

    boolean changeBattleRecordState(BattleTrailerParam param);

    boolean applyReferee(RaceRefereeParam param);

    boolean applyRefereeAgree(Long rid);

    boolean cancelRace(Long raceId);


}
