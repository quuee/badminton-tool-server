package cn.badminton.tool.web.controller;

import cn.badminton.tool.support.auth.WXAppletAuthenticationToken;
import cn.badminton.tool.support.common.RaceStateEnum;

import cn.badminton.tool.web.entity.RaceApplicantEntity;
import cn.badminton.tool.web.entity.RaceMainEntity;
import cn.badminton.tool.web.params.*;
import cn.badminton.tool.web.service.RaceApplicantService;
import cn.badminton.tool.web.service.RaceBattleService;
import cn.badminton.tool.web.service.RaceRefereeService;
import cn.badminton.tool.web.service.RaceService;
import cn.badminton.tool.web.vo.RaceBattleVO;
import cn.badminton.tool.web.vo.RaceRankVO;
import cn.badminton.tool.web.vo.RaceRefereeVO;
import cn.badminton.tool.web.vo.RaceVO;
import cn.badminton.tool.support.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("raceInfo")
public class RaceInfoController {

    @Autowired
    private RaceService raceService;

    @Autowired
    private RaceApplicantService applicantService;

    @Autowired
    private RaceRefereeService refereeService;

    @Autowired
    private RaceBattleService battleService;

    /**
     * 单个比赛信息
     * @return
     */
    @RequestMapping(value = "{raceId}",method = RequestMethod.GET)
    public Result<RaceVO> raceInfo(@PathVariable("raceId") Long raceId){
        RaceVO raceInfo = raceService.getRaceInfo(raceId);
        return Result.ok(raceInfo);
    }

    /**
     * 取消比赛  比赛状态改已完成
     * @param raceId
     * @return
     */
    @RequestMapping(value = "cancelRace",method = RequestMethod.PUT)
    public Result<Boolean> cancelRace(@RequestParam("raceId") Long raceId){
//        boolean b = raceService.removeById(raceId);
        boolean b = raceService.cancelRace(raceId);
        return Result.ok(b);
    }

    /**
     * 删除比赛
     * @return
     */
    @RequestMapping(value = "deleteRace",method = RequestMethod.DELETE)
    public Result<Boolean> deleteRace(@RequestParam("raceId") Long raceId){
        boolean b = raceService.removeById(raceId);
        return Result.ok(b);
    }

    /**
     * 编辑比赛
     * @param raceFormParam
     * @return
     */
    @RequestMapping(value = "editRace",method = RequestMethod.POST)
    public Result<RaceMainEntity> editRace(@RequestBody RaceFormParam raceFormParam){
        RaceMainEntity byId = raceService.getById(raceFormParam.getRaceId());
        if(Objects.equals(byId.getRaceState(), RaceStateEnum.racing.getValue())){
            return Result.error("比赛正在进行中");
        }
        RaceMainEntity r = raceService.editRace(raceFormParam);
        return Result.ok(r);
    }

    /**
     * 开启比赛
     * @param startRaceParam
     * @return
     */
    @RequestMapping(value = "startRace",method = RequestMethod.POST)
    public Result<RaceVO> startRace(@RequestBody StartRaceParam startRaceParam) {
        raceService.startRace(startRaceParam.getRaceId(),
                startRaceParam.getPlayerSession(),
                startRaceParam.getBattleGenerateAlgorithm(),
                startRaceParam.getFieldNum());

        return Result.ok();
    }

    /**
     * 报名/退赛
     * @param raceId
     * @return
     */
    @RequestMapping(value = "applyRetire/{raceId}",method = RequestMethod.POST)
    public Result applyRetire(@PathVariable("raceId") Long raceId){
        // 只有开启报名状态的比赛才可以报名
        RaceMainEntity mainEntity = raceService.getById(raceId);
        if(!Objects.equals(mainEntity.getRaceState(), RaceStateEnum.applyState.getValue())){
            return Result.error("当前比赛不是处于报名中");
        }
        WXAppletAuthenticationToken principal = (WXAppletAuthenticationToken) SecurityContextHolder.getContext().getAuthentication().getDetails();
        LambdaQueryWrapper<RaceApplicantEntity> raceIdQuery = new LambdaQueryWrapper<RaceApplicantEntity>()
                .eq(RaceApplicantEntity::getRaceId, raceId)
                .eq(RaceApplicantEntity::getUid,principal.getUid());
        List<RaceApplicantEntity> list = applicantService.list(raceIdQuery);
        for (RaceApplicantEntity raceApplicantEntity : list) {
            if(raceApplicantEntity.getUid().compareTo(principal.getUid()) == 0){
                boolean remove = applicantService.remove(raceIdQuery);
                if(remove){
                    return Result.ok("退赛成功");
                }
            }
        }

        RaceApplicantEntity build = RaceApplicantEntity.builder().raceId(raceId).uid(principal.getUid()).build();
        applicantService.save(build);
        return Result.ok("报名成功");
    }

    /**
     * 管理员删除已报名的选手
     * @param deletePlayerParam
     * @return
     */
    @RequestMapping(value = "deletePlayer",method = RequestMethod.DELETE)
    public Result deletePlayer(@RequestBody DeletePlayerParam deletePlayerParam){
        HashMap<String, Object> colMap = new HashMap<>();
        colMap.put("race_id",deletePlayerParam.getRaceId());
        colMap.put("uid",deletePlayerParam.getPlayerId());
        applicantService.removeByMap(colMap);
        return Result.ok("删除成功");
    }


    /**
     * 暂停报名/恢复报名
     * @return
     */
    public Result<RaceVO> pauseAndResumeApplicant(){
        return Result.ok();
    }

    /**
     * 获取比赛对阵信息
     * @param raceId
     * @return
     */
    @RequestMapping(value = "getBattles",method = RequestMethod.GET)
    public Result<List<RaceBattleVO>> getRaceBattles(@RequestParam("raceId")Long raceId){
        List<RaceBattleVO> raceBattles = battleService.getRaceBattles(raceId);
        return Result.ok(raceBattles);
    }

    /**
     * 获取排名信息
     * @param raceId
     * @return
     */
    @RequestMapping(value = "getRanks",method = RequestMethod.GET)
    public Result<List<RaceRankVO>> getRaceRank(@RequestParam("raceId")Long raceId){
        List<RaceRankVO> raceRank = raceService.getRaceRanks(raceId);
        return Result.ok(raceRank);
    }

    /**
     * 获取本次比赛裁判信息
     * @param raceId
     * @return
     */
    @RequestMapping(value = "getReferees",method = RequestMethod.GET)
    public Result<List<RaceRefereeVO>> getReferees(@RequestParam("raceId")Long raceId){
        List<RaceRefereeVO> refereeVOS = refereeService.getReferees(raceId);
        return Result.ok(refereeVOS);
    }

    /**
     * 申请为裁判
     * @param param
     * @return
     */
    @RequestMapping(value = "applyReferee",method = RequestMethod.POST)
    public Result<List<RaceRefereeVO>> applyReferee(@RequestBody RaceRefereeParam param){
        boolean f = raceService.applyReferee(param);
        List<RaceRefereeVO> refereeVOS = refereeService.getReferees(param.getRaceId());
        return Result.ok(refereeVOS);
    }

    @RequestMapping(value = "deleteReferee",method = RequestMethod.DELETE)
    public Result deleteReferee(@RequestBody DeleteRefereeParam deleteRefereeParam){
        HashMap<String, Object> colMap = new HashMap<>();
        colMap.put("race_id",deleteRefereeParam.getRaceId());
        colMap.put("uid",deleteRefereeParam.getRefereeId());
        refereeService.removeByMap(colMap);
        return Result.ok("删除成功");
    }

    @Deprecated
    @RequestMapping(value = "changeBattleRecordState",method = RequestMethod.POST)
    public Result changeBattleRecordState(@RequestBody BattleTrailerParam param){
        boolean f = raceService.changeBattleRecordState(param);
        return Result.ok(f);
    }

    /**
     * 比赛记分
     * @param param
     * @return
     */
    @RequestMapping(value = "editBattleScore",method = RequestMethod.POST)
    public Result editBattleScore(@RequestBody BattleScoreParam param){
        boolean f = raceService.editBattleScore(param);
        return Result.ok(f);
    }

    @RequestMapping(value = "raceHistory",method = RequestMethod.GET)
    public Result<Page<RaceBattleVO>> raceHistory(@RequestParam("playerId")Long playerId,@RequestParam(value = "pageNo",defaultValue = "1")Integer pageNo,
                                                  @RequestParam(value = "pageLimit",defaultValue = "10")Integer pageLimit){
        // 分页
        Page<RaceBattleVO> raceHistory = battleService.getRaceHistory(playerId,pageNo,pageLimit);
        return Result.ok(raceHistory);
    }
}
