package cn.badminton.tool.web.service.impl;

import cn.badminton.tool.web.dao.RaceBattleMapper;
import cn.badminton.tool.web.entity.RaceBattleEntity;
import cn.badminton.tool.web.service.RaceBattleService;
import cn.badminton.tool.web.vo.BattleStatisticsVO;
import cn.badminton.tool.web.vo.RaceBattleVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaceBattleServiceImpl extends ServiceImpl<RaceBattleMapper, RaceBattleEntity> implements RaceBattleService {

    @Override
    public BattleStatisticsVO getBattleStatistics(Long uid,Integer raceMainType) {
        return baseMapper.selectBattleStatistics(uid,raceMainType);
    }

    @Override
    public Page<RaceBattleVO> getRaceHistory(Long playerId,Integer pageNo,Integer pageLimit) {
        Page<RaceBattleVO> raceBattleVOPage = new Page<>(pageNo, pageLimit);
        return baseMapper.selectByPlayerId(raceBattleVOPage,playerId);
    }

    @Override
    public List<RaceBattleVO> getRaceBattles(Long raceId) {
        List<RaceBattleVO> raceBattleVOS = baseMapper.selectByRaceId(raceId);
        return raceBattleVOS;
    }
}
