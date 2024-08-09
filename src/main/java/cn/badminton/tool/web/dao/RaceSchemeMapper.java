package cn.badminton.tool.web.dao;

import cn.badminton.tool.web.entity.RaceSchemeEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public interface RaceSchemeMapper extends BaseMapper<RaceSchemeEntity> {

    RaceSchemeEntity selectRaceSchemeById(Long schemeId);
}
