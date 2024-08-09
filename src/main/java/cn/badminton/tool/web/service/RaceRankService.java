package cn.badminton.tool.web.service;

import cn.badminton.tool.web.vo.RaceRankVO;

import java.util.List;

public interface RaceRankService {


    List<RaceRankVO> getRaceRanks(Long raceId);
}
