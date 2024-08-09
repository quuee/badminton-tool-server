package cn.badminton.tool.web.controller;

import cn.badminton.tool.support.Result;
import cn.badminton.tool.web.service.RaceRankService;
import cn.badminton.tool.web.vo.RaceRankVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("rank")
public class RaceRankController {

    @Autowired
    private RaceRankService raceRankService;

    /**
     * 获取排名信息
     * @param raceId
     * @return
     */
    @RequestMapping(value = "getRanks",method = RequestMethod.GET)
    public Result<List<RaceRankVO>> getRaceRank(@RequestParam("raceId")Long raceId){
        List<RaceRankVO> raceRank = raceRankService.getRaceRanks(raceId);
        return Result.ok(raceRank);
    }
}
