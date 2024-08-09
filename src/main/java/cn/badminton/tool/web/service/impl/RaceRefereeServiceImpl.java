package cn.badminton.tool.web.service.impl;

import cn.badminton.tool.web.dao.RaceRefereeMapper;
import cn.badminton.tool.web.entity.RaceRefereeEntity;
import cn.badminton.tool.web.service.RaceRefereeService;
import cn.badminton.tool.web.vo.RaceRefereeVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaceRefereeServiceImpl extends ServiceImpl<RaceRefereeMapper, RaceRefereeEntity> implements RaceRefereeService {

    public List<RaceRefereeVO> getReferees(Long raceId){
        return baseMapper.selectRefereeByRaceId(raceId);
    }
}
