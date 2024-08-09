package cn.badminton.tool.web.dao;

import cn.badminton.tool.web.entity.RaceRefereeEntity;
import cn.badminton.tool.web.vo.RaceRefereeVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RaceRefereeMapper extends BaseMapper<RaceRefereeEntity> {

    List<RaceRefereeVO> selectRefereeByRaceId(Long raceId);
    RaceRefereeVO selectRefereeByUidRaceId(@Param("uid") Long uid,@Param("raceId")Long raceId);

}
