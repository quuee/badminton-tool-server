package cn.badminton.tool.web.controller;

import cn.badminton.tool.support.Result;
import cn.badminton.tool.web.params.DeleteRefereeParam;
import cn.badminton.tool.web.params.RaceRefereeParam;
import cn.badminton.tool.web.service.RaceRefereeService;
import cn.badminton.tool.web.vo.RaceRefereeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("referee")
public class RaceRefereeController {

    @Autowired
    private RaceRefereeService refereeService;

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
        boolean f = refereeService.applyReferee(param);
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
}
