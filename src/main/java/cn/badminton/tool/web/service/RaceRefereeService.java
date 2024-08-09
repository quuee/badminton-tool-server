package cn.badminton.tool.web.service;

import cn.badminton.tool.web.entity.RaceRefereeEntity;
import cn.badminton.tool.web.vo.RaceRefereeVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface RaceRefereeService extends IService<RaceRefereeEntity> {

    List<RaceRefereeVO> getReferees(Long raceId);
}
