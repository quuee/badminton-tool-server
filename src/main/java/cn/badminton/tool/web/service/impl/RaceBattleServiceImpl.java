package cn.badminton.tool.web.service.impl;

import cn.badminton.tool.support.common.BattleStateEnum;
import cn.badminton.tool.web.dao.RaceBattleMapper;
import cn.badminton.tool.web.entity.RaceBattleEntity;
import cn.badminton.tool.web.entity.RaceRefereeEntity;
import cn.badminton.tool.web.params.BattleScoreParam;
import cn.badminton.tool.web.params.BattleTrailerParam;
import cn.badminton.tool.web.service.RaceBattleService;
import cn.badminton.tool.web.vo.BattleStatisticsVO;
import cn.badminton.tool.web.vo.RaceBattleVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

//    @Override
//    @Transactional
//    public boolean changeBattleRecordState(BattleTrailerParam param) {
//        RaceBattleEntity build = RaceBattleEntity.builder().bid(param.getBid()).battleState(BattleStateEnum.racing.getValue()).refereeUid(param.getUid()).build();
//        int i = battleMapper.updateById(build);
//
//        RaceBattleEntity raceBattleEntity = battleMapper.selectById(param.getBid());
//        LambdaQueryWrapper<RaceRefereeEntity> eq = new LambdaQueryWrapper<RaceRefereeEntity>().eq(RaceRefereeEntity::getRaceId, raceBattleEntity.getRaceId()).eq(RaceRefereeEntity::getUid, param.getUid());
//        List<RaceRefereeEntity> raceTrialEntities = refereeMapper.selectList(eq);
//        if (raceTrialEntities.isEmpty()) {
//            RaceRefereeEntity trialEntity = RaceRefereeEntity.builder().raceId(raceBattleEntity.getRaceId()).uid(param.getUid()).build();
//            refereeMapper.insert(trialEntity);
//        }
//
//        return i > 0;
//    }

    @Override
    public boolean editBattleScore(BattleScoreParam param) {
        RaceBattleEntity raceBattleEntity = baseMapper.selectById(param.getBid());
        raceBattleEntity.setFirstPartnerScore(param.getScore1());
        raceBattleEntity.setSecondPartnerScore(param.getScore2());
        raceBattleEntity.setRefereeUid(param.getRefereeUid());
        raceBattleEntity.setBattleState(BattleStateEnum.finish.getValue());
        int i = baseMapper.updateById(raceBattleEntity);

        return i > 0;
    }
}
