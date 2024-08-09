package cn.badminton.tool.web.service;

import cn.badminton.tool.web.entity.RaceBattleEntity;
import cn.badminton.tool.web.vo.BattleStatisticsVO;
import cn.badminton.tool.web.vo.RaceBattleVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RaceBattleService extends IService<RaceBattleEntity> {

    BattleStatisticsVO getBattleStatistics(Long uid,Integer raceMainType);

    List<RaceBattleVO> getRaceBattles(Long raceId);

    Page<RaceBattleVO> getRaceHistory(Long playerId, Integer pageNo, Integer pageLimit);
}
