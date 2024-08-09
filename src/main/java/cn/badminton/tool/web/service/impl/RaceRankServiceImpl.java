package cn.badminton.tool.web.service.impl;

import cn.badminton.tool.support.common.BattleStateEnum;
import cn.badminton.tool.web.dao.RaceBattleMapper;
import cn.badminton.tool.web.dao.RaceMainMapper;
import cn.badminton.tool.web.entity.WXUserEntity;
import cn.badminton.tool.web.service.RaceRankService;
import cn.badminton.tool.web.vo.RaceBattleVO;
import cn.badminton.tool.web.vo.RaceRankVO;
import cn.badminton.tool.web.vo.RaceVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RaceRankServiceImpl implements RaceRankService {

    @Autowired
    private RaceMainMapper raceMainMapper;

    @Autowired
    private RaceBattleMapper battleMapper;

    @Override
    public List<RaceRankVO> getRaceRanks(Long raceId) {
        List<RaceRankVO> raceRankVOS = new ArrayList<>();
        HashMap<Long, RaceRankVO> rankVOHashMap = new HashMap<>();
        RaceVO raceVO = raceMainMapper.selectRaceById(raceId);
        for (WXUserEntity applicant : raceVO.getApplicants()) {
            RaceRankVO raceRankVO = new RaceRankVO();
            raceRankVO.setRaceId(raceId);
            raceRankVO.setPlayer(applicant);
            raceRankVO.setVictories(0);
            raceRankVO.setFailures(0);
            raceRankVO.setOdds(0);
            raceRankVO.setOddsDifference(0);
            rankVOHashMap.put(applicant.getUid(), raceRankVO);
        }
        List<RaceBattleVO> raceBattleVOS = battleMapper.selectByRaceId(raceId);

        for (RaceBattleVO raceBattleVO : raceBattleVOS) {
            if (Objects.equals(raceBattleVO.getBattleState(), BattleStateEnum.finish.getValue())) {
                WXUserEntity player1 = raceBattleVO.getPlayer1();
                WXUserEntity player2 = raceBattleVO.getPlayer2();
                Integer firstPartnerScore = raceBattleVO.getFirstPartnerScore();

                WXUserEntity player3 = raceBattleVO.getPlayer3();
                WXUserEntity player4 = raceBattleVO.getPlayer4();
                Integer secondPartnerScore = raceBattleVO.getSecondPartnerScore();
                if (firstPartnerScore == 0 && secondPartnerScore == 0) {
                    continue;
                }

                RaceRankVO raceRankVO1 = rankVOHashMap.get(player1.getUid());
                RaceRankVO raceRankVO2 = rankVOHashMap.get(player2.getUid());
                RaceRankVO raceRankVO3 = rankVOHashMap.get(player3.getUid());
                RaceRankVO raceRankVO4 = rankVOHashMap.get(player4.getUid());

                if (firstPartnerScore > secondPartnerScore) {
                    raceRankVO1.setVictories(raceRankVO1.getVictories() + 1);
                    raceRankVO1.setOdds(raceRankVO1.getOdds() + 2);
                    raceRankVO1.setOddsDifference(raceRankVO1.getOddsDifference() + (firstPartnerScore - secondPartnerScore));
                    raceRankVO2.setVictories(raceRankVO2.getVictories() + 1);
                    raceRankVO2.setOdds(raceRankVO2.getOdds() + 2);
                    raceRankVO2.setOddsDifference(raceRankVO2.getOddsDifference() + (firstPartnerScore - secondPartnerScore));

                    raceRankVO3.setFailures(raceRankVO3.getFailures() + 1);
                    raceRankVO3.setOddsDifference(raceRankVO3.getOddsDifference() + (secondPartnerScore - firstPartnerScore));
                    raceRankVO4.setFailures(raceRankVO4.getFailures() + 1);
                    raceRankVO4.setOddsDifference(raceRankVO4.getOddsDifference() + (secondPartnerScore - firstPartnerScore));

                } else if (firstPartnerScore.equals(secondPartnerScore)) {

                } else {
                    raceRankVO3.setVictories(raceRankVO3.getVictories() + 1);
                    raceRankVO3.setOdds(raceRankVO3.getOdds() + 2);
                    raceRankVO3.setOddsDifference(raceRankVO3.getOddsDifference() + (secondPartnerScore - firstPartnerScore));
                    raceRankVO4.setVictories(raceRankVO4.getVictories() + 1);
                    raceRankVO4.setOdds(raceRankVO4.getOdds() + 2);
                    raceRankVO4.setOddsDifference(raceRankVO4.getOddsDifference() + (secondPartnerScore - firstPartnerScore));

                    raceRankVO1.setFailures(raceRankVO1.getFailures() + 1);
                    raceRankVO1.setOddsDifference(raceRankVO1.getOddsDifference() + (firstPartnerScore - secondPartnerScore));
                    raceRankVO2.setFailures(raceRankVO2.getFailures() + 1);
                    raceRankVO2.setOddsDifference(raceRankVO2.getOddsDifference() + (firstPartnerScore - secondPartnerScore));
                }

            }

        }


        for (Map.Entry<Long, RaceRankVO> longRaceRankVOEntry : rankVOHashMap.entrySet()) {
            RaceRankVO value = longRaceRankVOEntry.getValue();
            raceRankVOS.add(value);
        }
//        raceRankVOS.stream().sorted(new Comparator<RaceRankVO>() {
//            @Override
//            public int compare(RaceRankVO o1, RaceRankVO o2) {
//                int v = o1.getOdds().compareTo(o2.getOdds());
//                if(v == 0){
//                    v = o1.getOddsDifference().compareTo(o2.getOddsDifference());
//                }
//                return v;
//            }
//        });
        List<RaceRankVO> collect = raceRankVOS.stream().sorted(Comparator.comparing(RaceRankVO::getOdds, Comparator.reverseOrder())
                .thenComparing(RaceRankVO::getOddsDifference, Comparator.reverseOrder())).collect(Collectors.toList());
        return collect;
    }
}
