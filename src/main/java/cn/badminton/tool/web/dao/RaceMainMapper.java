package cn.badminton.tool.web.dao;

import cn.badminton.tool.web.entity.RaceMainEntity;
import cn.badminton.tool.web.vo.RaceVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Date;


public interface RaceMainMapper extends BaseMapper<RaceMainEntity> {

    Page<RaceVO> selectRaceByUid(@Param("page")Page page, @Param("uid") Long uid, @Param("today") Date today, @Param("tenDaysAgo") Date tenDaysAgo);

    RaceVO selectRaceById(Long raceId);
}
