package cn.badminton.tool.web.service;

import cn.badminton.tool.web.entity.RaceMainEntity;

import cn.badminton.tool.web.params.RaceFormParam;

import cn.badminton.tool.web.vo.RaceVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;



public interface RaceService extends IService<RaceMainEntity> {

    Page<RaceVO> getRaceList(Long uid, Integer pageNo, Integer pageLimit);

    RaceMainEntity createRace(RaceFormParam raceFormParam);

    RaceMainEntity editRace(RaceFormParam raceFormParam);

    RaceVO getRaceInfo(Long raceId);

    /**
     *
     * @param raceId 比赛id
     * @param playerSession 每位选手上场次数
     * @param battleGenerate 对阵生成方式：随机、势均力敌
     * @param fieldNum 场地数
     * @return
     */
    RaceVO startRace(Long raceId,Integer playerSession,Integer battleGenerate,Integer fieldNum);


    boolean cancelRace(Long raceId);


}
