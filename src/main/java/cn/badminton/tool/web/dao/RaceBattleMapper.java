package cn.badminton.tool.web.dao;

import cn.badminton.tool.web.entity.RaceBattleEntity;
import cn.badminton.tool.web.vo.BattleStatisticsVO;
import cn.badminton.tool.web.vo.RaceBattleVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RaceBattleMapper extends BaseMapper<RaceBattleEntity> {

    List<RaceBattleVO> selectByRaceId(Long raceId);

//    List<WXUserEntity> selectTrialByRaceId(Long raceId);

    /**
     * 查询个人战绩统计信息
     */
    BattleStatisticsVO selectBattleStatistics(@Param("userId") Long userId, @Param("raceMainType")Integer raceMainType);

    Page<RaceBattleVO> selectByPlayerId(@Param("page") Page<RaceBattleVO> page, @Param("playerId") Long playerId);
}
