package cn.badminton.tool.web.service.impl;


import cn.badminton.tool.support.auth.WXAppletAuthenticationToken;
import cn.badminton.tool.support.common.BattleStateEnum;
import cn.badminton.tool.support.common.GenderEnum;
import cn.badminton.tool.support.common.RaceBOXEnum;
import cn.badminton.tool.support.common.RaceStateEnum;
import cn.badminton.tool.support.exception.ServerException;
import cn.badminton.tool.tools.DateUtil;
import cn.badminton.tool.tools.racetool.RaceGenerateTool;
import cn.badminton.tool.tools.racetool.RaceToolFactory;
import cn.badminton.tool.web.convert.RaceConvert;
import cn.badminton.tool.web.dao.*;
import cn.badminton.tool.web.dto.BattleDTO;
import cn.badminton.tool.web.dto.PlayerDTO;
import cn.badminton.tool.web.entity.*;
import cn.badminton.tool.web.params.BattleScoreParam;
import cn.badminton.tool.web.params.BattleTrailerParam;
import cn.badminton.tool.web.params.RaceFormParam;
import cn.badminton.tool.web.params.RaceRefereeParam;
import cn.badminton.tool.web.service.RaceService;
import cn.badminton.tool.web.vo.RaceBattleVO;
import cn.badminton.tool.web.vo.RaceRankVO;
import cn.badminton.tool.web.vo.RaceVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RaceServiceImpl extends ServiceImpl<RaceMainMapper, RaceMainEntity> implements RaceService {

    @Autowired
    private RaceConvert raceConvert;

    @Autowired
    private RaceApplicantMapper applicantMapper;

    @Autowired
    private RaceBattleMapper battleMapper;

//    @Autowired
//    private RaceRankMapper rankMapper;

    @Autowired
    private RaceRefereeMapper refereeMapper;

    @Override
    public RaceMainEntity createRace(RaceFormParam raceFormParam) {
        WXAppletAuthenticationToken principal = (WXAppletAuthenticationToken) SecurityContextHolder.getContext().getAuthentication().getDetails();
        Date parseDate = DateUtil.parseDate(raceFormParam.getRaceCalender() + " " + raceFormParam.getRaceTime() + ":00",
                DateUtil.yyyy__MM__dd + " " + DateUtil.HH_mm_ss);

        GenderEnum genderEnum = GenderEnum.valueOfString(raceFormParam.getGenderLimit());
        RaceBOXEnum raceBOXEnum = RaceBOXEnum.valueOf(raceFormParam.getRaceBOX());

        // 创建比赛主体
        RaceMainEntity raceMain = RaceMainEntity.builder()
                .raceTitle(raceFormParam.getRaceTitle())
                .raceDateTime(parseDate)
                .raceMainType(raceFormParam.getRaceMainType())
                .schemeId(raceFormParam.getSchemeId())
                .raceScoreMode(raceFormParam.getRaceScoreMode())
                .address(raceFormParam.getRaceAddress())
                .addContext(raceFormParam.getAddContext())
                .organizerUid(principal.getUid())
                .predictApplyNum(raceFormParam.getApplicats())
                .createTime(new Date())
                .defaultReferee(true)
                // 需要转换的参数
                .raceState(RaceStateEnum.applyState.getValue())
                .raceBOX(raceBOXEnum.getValue())
                .genderLimit(genderEnum.getValue())

                .build();


        // 创建报名信息
        boolean save = save(raceMain);

        // 将创建的比赛加入缓存 存放15天，如果15天没有进行比赛则关闭比赛（改为已完成）

        return raceMain;
    }

    @Override
    public RaceMainEntity editRace(RaceFormParam raceFormParam) {
        Date parseDate = DateUtil.parseDate(raceFormParam.getRaceCalender() + " " + raceFormParam.getRaceTime() + ":00",
                DateUtil.yyyy__MM__dd + " " + DateUtil.HH_mm_ss);
        GenderEnum genderEnum = GenderEnum.valueOfString(raceFormParam.getGenderLimit());
        RaceBOXEnum raceBOXEnum = RaceBOXEnum.valueOf(raceFormParam.getRaceBOX());
        // 创建比赛主体
        RaceMainEntity raceMain = RaceMainEntity.builder()
                .raceId(raceFormParam.getRaceId())
                .raceTitle(raceFormParam.getRaceTitle())
                .raceDateTime(parseDate)
                .raceMainType(raceFormParam.getRaceMainType())
                .schemeId(raceFormParam.getSchemeId())
                .raceScoreMode(raceFormParam.getRaceScoreMode())
                .address(raceFormParam.getRaceAddress())
                .addContext(raceFormParam.getAddContext())
//                .organizerUid(principal.getUid())
                .predictApplyNum(raceFormParam.getApplicats())
                .createTime(new Date())
                // 需要转换的参数
                .raceState(RaceStateEnum.applyState.getValue())
                .raceBOX(raceBOXEnum.getValue())
                .genderLimit(genderEnum.getValue())
                .build();

        boolean b = updateById(raceMain);
        return raceMain;

    }

    @Override
    @Transactional
    public RaceVO startRace(Long raceId, Integer playerSession, Integer battleGenerate, Integer fieldNum) {
        // 根据 (双打 单打 对抗 团体)、(比赛具体模式：八人转、混双转、超八转等) 选择生成算法
        RaceVO raceInfo = getRaceInfo(raceId);
        // 算法类名
        String generateClassName = raceInfo.getRaceScheme().getGenerateClassName();
        // 参赛选手
        List<PlayerDTO> playerDTOS = new ArrayList<>();
        // 每人上场次数
//        Integer everyPlayTime = startRaceParam.getPeopleSession();
        // 对阵算法
//        Integer battleGenerate = startRaceParam.getBattleGenerateAlgorithm();
        // 场地片数
//        Integer fieldNum = startRaceParam.getFieldNum();

        for (WXUserEntity applicant : raceInfo.getApplicants()) {
            PlayerDTO build = PlayerDTO.builder()
                    .uid(applicant.getUid())
                    .openid(applicant.getOpenid())
                    .nickName(applicant.getNickName())
                    .avatarUrl(applicant.getAvatarUrl())
                    .level(applicant.getLevel())
                    .everyonePlayTimes(playerSession)
                    .build();
            playerDTOS.add(build);
        }
        // 打乱选手顺序
//        Collections.shuffle(playerDTOS);
        RaceGenerateTool raceGenerateTool;
        try {
            // 创建算法类
            raceGenerateTool = RaceToolFactory.getRaceGenerateTool(generateClassName, battleGenerate, playerDTOS, playerSession, fieldNum);
        } catch (Exception ignored) {
            throw new ServerException("生成算法失败");
        }

        Set<BattleDTO> doublesBattleDTOS = new HashSet<>();
        int retry = 0;
        while (doublesBattleDTOS.isEmpty()) {
            try {
                if (retry >= 100) {
                    break;
                }
                raceGenerateTool.generatorBattle();
                doublesBattleDTOS = raceGenerateTool.getResultBattles();
                retry++;
                System.out.printf("第%d次成功%n", retry);
            } catch (Exception e) {
                retry++;
                System.out.printf("第%d次重试%n", retry);
                e.printStackTrace();
            }
        }

        if (!doublesBattleDTOS.isEmpty()) {
            List<RaceBattleEntity> raceBattleEntities = new ArrayList<>();
            for (BattleDTO doublesBattleDTO : doublesBattleDTOS) {
                RaceBattleEntity build = RaceBattleEntity.builder()
                        .raceId(raceId)
                        .playerUid1(doublesBattleDTO.getPartner1().getP1().getUid())
                        .playerUid2(doublesBattleDTO.getPartner1().getP2() != null ? doublesBattleDTO.getPartner1().getP2().getUid() : 0)
                        .firstPartnerScore(0)
                        .playerUid3(doublesBattleDTO.getPartner2().getP1().getUid())
                        .playerUid4(doublesBattleDTO.getPartner2().getP2() != null ? doublesBattleDTO.getPartner2().getP2().getUid() : 0)
                        .secondPartnerScore(0)
                        .battleState(BattleStateEnum.notStart.getValue())
                        .roundNum(doublesBattleDTO.getRoundNum())
//                    .trialUid()
                        .build();
                raceBattleEntities.add(build);
            }
            // 创建 对阵信息 写入数据库
            battleMapper.insert(raceBattleEntities);

            // 创建 裁判信息
            RaceRefereeEntity build = RaceRefereeEntity.builder().raceId(raceId).uid(raceInfo.getOrganizerUid()).master(true).build();
            refereeMapper.insert(build);

            RaceMainEntity raceMain = RaceMainEntity.builder().raceId(raceId).raceState(RaceStateEnum.racing.getValue()).build();
            updateById(raceMain);
        }

        return raceInfo;
    }

    @Override
    public RaceVO getRaceInfo(Long raceId) {
        // 查询主体信息
        RaceVO raceVO = baseMapper.selectRaceById(raceId);
        WXAppletAuthenticationToken principal = (WXAppletAuthenticationToken) SecurityContextHolder.getContext().getAuthentication().getDetails();
        int compare = principal.getUid().compareTo(raceVO.getOrganizerUid());
        if (compare == 0) {
            raceVO.setOrganization(true);
        }
        // ===== 后面这些信息在不同的tab下，也可以点击到tab后查询 =====
        // 查询报名信息

        // 查询排名信息，直接返回女榜男榜；统一榜单前端两者合并再排序

        // 查询对阵信息

        // 查询裁判信息

        return raceVO;
    }

    @Override
    public Page<RaceVO> getRaceList(Long uid, Integer pageNo, Integer pageLimit) {
        // 根据当前用户 组织者身份、参与者身份、时间范围 查询比赛
        Page<RaceVO> page = new Page<>(pageNo, pageLimit);
        // 获取当前日期
        LocalDate today = LocalDate.now();
        LocalDate tenDaysAgo = today.minusDays(10);
        LocalDateTime tenDaysAgoDateTime = tenDaysAgo.atTime(1, 1);
        Date tenDaysAgoDate = Date.from(tenDaysAgoDateTime.atZone(ZoneId.systemDefault()).toInstant());
        LocalDateTime todayDateTime = today.atTime(23, 59);
        Date todayDate = Date.from(todayDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Page<RaceVO> raceVOS = baseMapper.selectRaceByUid(page,uid,todayDate,tenDaysAgoDate);
        return raceVOS;
    }

    @Override
    public List<RaceRankVO> getRaceRanks(Long raceId) {
        List<RaceRankVO> raceRankVOS = new ArrayList<>();
        HashMap<Long, RaceRankVO> rankVOHashMap = new HashMap<>();
        RaceVO raceVO = baseMapper.selectRaceById(raceId);
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


    @Override
    @Transactional
    public boolean changeBattleRecordState(BattleTrailerParam param) {
        RaceBattleEntity build = RaceBattleEntity.builder().bid(param.getBid()).battleState(BattleStateEnum.racing.getValue()).refereeUid(param.getUid()).build();
        int i = battleMapper.updateById(build);

        RaceBattleEntity raceBattleEntity = battleMapper.selectById(param.getBid());
        LambdaQueryWrapper<RaceRefereeEntity> eq = new LambdaQueryWrapper<RaceRefereeEntity>().eq(RaceRefereeEntity::getRaceId, raceBattleEntity.getRaceId()).eq(RaceRefereeEntity::getUid, param.getUid());
        List<RaceRefereeEntity> raceTrialEntities = refereeMapper.selectList(eq);
        if (raceTrialEntities.isEmpty()) {
            RaceRefereeEntity trialEntity = RaceRefereeEntity.builder().raceId(raceBattleEntity.getRaceId()).uid(param.getUid()).build();
            refereeMapper.insert(trialEntity);
        }

        return i > 0;

    }

    @Override
    public boolean editBattleScore(BattleScoreParam param) {
        RaceBattleEntity raceBattleEntity = battleMapper.selectById(param.getBid());
        raceBattleEntity.setFirstPartnerScore(param.getScore1());
        raceBattleEntity.setSecondPartnerScore(param.getScore2());
        raceBattleEntity.setRefereeUid(param.getRefereeUid());
        raceBattleEntity.setBattleState(BattleStateEnum.finish.getValue());
        int i = battleMapper.updateById(raceBattleEntity);

        return i > 0;
    }

    @Override
    public boolean applyReferee(RaceRefereeParam param) {
        RaceMainEntity raceMain = baseMapper.selectById(param.getRaceId());
        Long selectCount = refereeMapper.selectCount(new LambdaQueryWrapper<RaceRefereeEntity>().eq(RaceRefereeEntity::getRaceId, param.getRaceId()).eq(RaceRefereeEntity::getUid, param.getUid()));
        int insert = 0;
        if (selectCount <= 0) {
            RaceRefereeEntity build = RaceRefereeEntity.builder().raceId(param.getRaceId()).uid(param.getUid()).master(raceMain.isDefaultReferee()).build();
            insert = refereeMapper.insert(build);
        }
        return insert > 0;
    }

    @Override
    public boolean applyRefereeAgree(Long rid) {
        RaceRefereeEntity raceRefereeEntity = refereeMapper.selectById(rid);
        raceRefereeEntity.setMaster(true);
        int i = refereeMapper.updateById(raceRefereeEntity);
        return i > 0;
    }

    @Override
    public boolean cancelRace(Long raceId) {
        RaceMainEntity raceMain = getById(raceId);
        raceMain.setRaceState(RaceStateEnum.finish.getValue());
        boolean b = updateById(raceMain);
        return b;
    }
}
