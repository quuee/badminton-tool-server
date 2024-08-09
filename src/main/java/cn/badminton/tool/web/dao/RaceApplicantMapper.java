package cn.badminton.tool.web.dao;

import cn.badminton.tool.web.entity.RaceApplicantEntity;
import cn.badminton.tool.web.entity.WXUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RaceApplicantMapper extends BaseMapper<RaceApplicantEntity> {

    List<WXUserEntity> selectByRaceId(@Param("raceId")Long raceId);
}
