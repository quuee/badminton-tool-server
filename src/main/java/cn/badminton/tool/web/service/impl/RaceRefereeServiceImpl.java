package cn.badminton.tool.web.service.impl;

import cn.badminton.tool.web.dao.RaceMainMapper;
import cn.badminton.tool.web.dao.RaceRefereeMapper;
import cn.badminton.tool.web.entity.RaceMainEntity;
import cn.badminton.tool.web.entity.RaceRefereeEntity;
import cn.badminton.tool.web.params.RaceRefereeParam;
import cn.badminton.tool.web.service.RaceRefereeService;
import cn.badminton.tool.web.vo.RaceRefereeVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaceRefereeServiceImpl extends ServiceImpl<RaceRefereeMapper, RaceRefereeEntity> implements RaceRefereeService {

    @Autowired
    private RaceMainMapper raceMainMapper;

    public List<RaceRefereeVO> getReferees(Long raceId){
        return baseMapper.selectRefereeByRaceId(raceId);
    }

    @Override
    public boolean applyReferee(RaceRefereeParam param) {
        RaceMainEntity raceMain = raceMainMapper.selectById(param.getRaceId());
        Long selectCount = baseMapper.selectCount(new LambdaQueryWrapper<RaceRefereeEntity>().eq(RaceRefereeEntity::getRaceId, param.getRaceId()).eq(RaceRefereeEntity::getUid, param.getUid()));
        int insert = 0;
        if (selectCount <= 0) {
            RaceRefereeEntity build = RaceRefereeEntity.builder().raceId(param.getRaceId()).uid(param.getUid()).master(raceMain.isDefaultReferee()).build();
            insert = baseMapper.insert(build);
        }
        return insert > 0;
    }

    @Override
    public boolean applyRefereeAgree(Long rid) {
        RaceRefereeEntity raceRefereeEntity = baseMapper.selectById(rid);
        raceRefereeEntity.setMaster(true);
        int i = baseMapper.updateById(raceRefereeEntity);
        return i > 0;
    }
}
