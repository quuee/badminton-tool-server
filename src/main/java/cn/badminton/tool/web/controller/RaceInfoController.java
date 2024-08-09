package cn.badminton.tool.web.controller;

import cn.badminton.tool.support.auth.WXAppletAuthenticationToken;
import cn.badminton.tool.support.common.RaceStateEnum;

import cn.badminton.tool.web.entity.RaceApplicantEntity;
import cn.badminton.tool.web.entity.RaceMainEntity;
import cn.badminton.tool.web.params.*;
import cn.badminton.tool.web.service.RaceApplicantService;
import cn.badminton.tool.web.service.RaceService;
import cn.badminton.tool.web.vo.RaceVO;
import cn.badminton.tool.support.Result;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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






}
