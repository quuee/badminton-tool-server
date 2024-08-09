package cn.badminton.tool.web.controller;

import cn.badminton.tool.support.Result;
import cn.badminton.tool.web.params.BattleScoreParam;
import cn.badminton.tool.web.service.RaceBattleService;
import cn.badminton.tool.web.vo.RaceBattleVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("battle")
public class RaceBattleController {

    @Autowired
    private RaceBattleService battleService;

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

//    @Deprecated
//    @RequestMapping(value = "changeBattleRecordState",method = RequestMethod.POST)
//    public Result changeBattleRecordState(@RequestBody BattleTrailerParam param){
//        boolean f = raceService.changeBattleRecordState(param);
//        return Result.ok(f);
//    }

    /**
     * 比赛记分
     * @param param
     * @return
     */
    @RequestMapping(value = "editBattleScore",method = RequestMethod.POST)
    public Result editBattleScore(@RequestBody BattleScoreParam param){
        boolean f = battleService.editBattleScore(param);
        return Result.ok(f);
    }

    @RequestMapping(value = "raceHistory",method = RequestMethod.GET)
    public Result<Page<RaceBattleVO>> raceHistory(@RequestParam("playerId")Long playerId, @RequestParam(value = "pageNo",defaultValue = "1")Integer pageNo,
                                                  @RequestParam(value = "pageLimit",defaultValue = "10")Integer pageLimit){
        // 分页
        Page<RaceBattleVO> raceHistory = battleService.getRaceHistory(playerId,pageNo,pageLimit);
        return Result.ok(raceHistory);
    }
}
