//package cn.badminton.tool.tools.racetool;
//
//import cn.badminton.tool.tools.SortTool;
//import cn.badminton.tool.web.dto.PartnerDTO;
//import cn.badminton.tool.web.dto.PlayerDTO;
//import org.springframework.util.ObjectUtils;
//
//import java.util.*;
//import java.util.stream.Collectors;
//@Deprecated
//public class BadmintonDoubleRaceGeneratorTool7 {
//
//    /**
//     * 参赛人列表
//     */
//    private List<PlayerDTO> players;
//
//    /**
//     * 每人上场次数
//     * 用于创建一个池，每个人总共 playerEveryonePlayTimes 次，每分配一次 -1
//     */
//    private Integer everyonePlayTimes;
//
//    /**
//     * 场地数
//     */
//    private Integer venueNum;
//
//    /**
//     * 选手出场次数池
//     */
//    private Map<String, Integer> playerTimesPool;
//
//    private HashSet<PartnerDTO> partnerSetAlready;
//
//    private List<String> tempBattleRepeatNameList;
//
//    public BadmintonDoubleRaceGeneratorTool7(List<PlayerDTO> players,
//                                             Integer everyonePlayTimes,
//                                             Integer venueNum) {
//        this.players = players;
//        this.everyonePlayTimes = everyonePlayTimes;
//        this.venueNum = venueNum;
//
//        this.playerTimesPool = new LinkedHashMap<>();
//        for (PlayerDTO p : players) {
//            playerTimesPool.put(p.getNickName(), everyonePlayTimes);
//        }
//        partnerSetAlready = new HashSet<>();
//
//    }
//
//    public Set<DoublesBattleDTO> generatorBattle() {
//        // 对阵结果集
//        Set<DoublesBattleDTO> resultBattles = new LinkedHashSet<>();
//        // 总场次
//        Integer raceAllRoundNum = players.size() * (everyonePlayTimes) / 4;
//        // 轮数
//        double roundNum = Math.ceil((double) raceAllRoundNum / venueNum);
//
//        for (int i = 0; i < roundNum; i++) {
//            System.out.println(String.format("开始第%d轮", i + 1));
//            // 本轮选手池，该轮出现过的选手不能再出场
//            Set<PlayerDTO> currentRoundPlayerPool = new HashSet<>(0);
//            for (int v = 0; v < venueNum; v++) {
//                if (raceAllRoundNum <= 0) {
//                    // 总场次结束
//                    break;
//                }
//                Set<PlayerDTO> battleSet = new LinkedHashSet<>();
//
//                tempBattleRepeatNameList= new ArrayList<>();
//                while (battleSet.size() < 4) {
//                    PlayerDTO newPlayer = getPlayer(currentRoundPlayerPool);
//                    // 出场次数 -1
//                    // 减了之后，如果匹配不成功，要在本次对阵后还回去
//                    timesPoolSubtract(newPlayer.getNickName());
//                    tempBattleRepeatNameList.add(newPlayer.getNickName());
//                    PartnerDTO oldPartner = new PartnerDTO();
//                    if (battleSet.size() == 3) {
//                        battleSet.add(newPlayer);
//                        // 4人 有 6种组合
//                        List<List<PlayerDTO>> lists
//                                = SortTool.fourPlayerToCombine(battleSet.stream().toList());
//                        battleSet.clear();
//                        for (int s = 0; s < lists.size(); s++) {
//                            List<PlayerDTO> partnerList = lists.get(s);
//                            PlayerDTO p1 = partnerList.get(0);
//                            PlayerDTO p2 = partnerList.get(1);
//                            PartnerDTO newPartner = new PartnerDTO();
//                            newPartner.setP1(p1);
//                            newPartner.setP2(p2);
//                            // 判断4人都不相同
//                            boolean contain1 = isContain(oldPartner, newPartner);
//                            // 判断两人是否已经搭过
//                            boolean contain2 = partnerSetAlready.contains(newPartner);
//
//                            if (contain1 || contain2) {
//                                //只要一个true 相同，跳过
//                                continue;
//                            }
//                            battleSet.add(p1);
//                            battleSet.add(p2);
//                            oldPartner.setP1(p1);
//                            oldPartner.setP2(p2);
//                            if (battleSet.size() == 4) {
//                                break;
//                            }
//                        }
//                    }
//                    if (battleSet.size() <= 3) {
//                        battleSet.add(newPlayer);
//                    }
//                }
//                List<PlayerDTO> playerDTOS = battleSet.stream().toList();
//                DoublesBattleDTO doublesBattleDTO = new DoublesBattleDTO(playerDTOS.get(0), playerDTOS.get(1), playerDTOS.get(2), playerDTOS.get(3));
//                doublesBattleDTO.setRoundNum(i + 1);
//                resultBattles.add(doublesBattleDTO);
//
//                currentRoundPlayerPool.add(playerDTOS.get(0));
//                currentRoundPlayerPool.add(playerDTOS.get(1));
//                currentRoundPlayerPool.add(playerDTOS.get(2));
//                currentRoundPlayerPool.add(playerDTOS.get(3));
//
//                PartnerDTO partner1 = doublesBattleDTO.getPartnerDTO1();
//                PartnerDTO partner2 = doublesBattleDTO.getPartnerDTO2();
//                partnerSetAlready.add(partner1);
//                partnerSetAlready.add(partner2);
//                raceAllRoundNum--;
//
//                // 还原不匹配被多减的选手场次
//                List<String> battleNameList = battleSet.stream().map(PlayerDTO::getNickName).toList();
//                tempBattleRepeatNameList.removeAll(battleNameList);
//                for (String nickname : tempBattleRepeatNameList) {
//                    timesPoolAdd(nickname);
//                }
//                tempBattleRepeatNameList.clear();
//                String format = String.format("对阵：%s & %s VS %s & %s",
//                        doublesBattleDTO.getPartnerDTO1().getP1().getNickName(),
//                        doublesBattleDTO.getPartnerDTO1().getP2().getNickName(),
//                        doublesBattleDTO.getPartnerDTO2().getP1().getNickName(),
//                        doublesBattleDTO.getPartnerDTO2().getP2().getNickName()
//                );
//                System.out.println(format);
//            }
//            currentRoundPlayerPool.clear();
//        }
//
//        return resultBattles;
//    }
//
//    /**
//     * @param currentRoundPlayerPool
//     * @return
//     */
//    private PlayerDTO getPlayer(Set<PlayerDTO> currentRoundPlayerPool) {
//        for (PlayerDTO p : players) {
//            Integer t = playerTimesPool.get(p.getNickName());
//            p.setEveryonePlayTimes(t);
//        }
//        ArrayList<PlayerDTO> playerDTOS = new ArrayList<>(players);
//
//        // 去掉本轮已出场的选手
//        for (int i = 0; i < players.size(); i++) {
//            PlayerDTO p = players.get(i);
//            for (PlayerDTO c : currentRoundPlayerPool) {
//                if (p.equals(c)) {
//                    playerDTOS.remove(p);
//                    break;
//                }
//            }
//        }
//
//        // 分组
//        Map<Integer, List<PlayerDTO>> groupMap = playerDTOS.stream().collect(Collectors.groupingBy(PlayerDTO::getEveryonePlayTimes));
//        List<PlayerDTO> groupMapPlayers = new ArrayList<>();
//        // 分组后 从高到低排，先取未上场
//        for (int i = everyonePlayTimes; i > 0; i--) {
//            List<PlayerDTO> ppp = groupMap.get(i);
//            if (ppp != null && !ppp.isEmpty()) {
//                groupMapPlayers.addAll(ppp);
//                break;
//            }
//        }
//
//        // 只要大于0，打乱后随机取。更加不行，有的会提前消耗完次数。只取2组也不行
////        for (int i = everyonePlayTimes; i > everyonePlayTimes - 2; i--) {
////            List<PlayerDTO> ppp = groupMap.get(i);
////            if(ppp!=null && !ppp.isEmpty()){
////                groupMapPlayers.addAll(ppp);
////            }
////        }
//
////        Collections.shuffle(groupMapPlayers);
//
////        if(groupMapPlayers.isEmpty()){
//            // 说明按照 1&2 3&4这样组合，已经被耗尽，取不到值了
////        }
////                    if(tempBattleRepeatNameList.size() > players.size() - 2){
////                        Collections.reverse(players);
////                    }
//
//        PlayerDTO first = groupMapPlayers.getFirst();
//        return first;
//    }
//
//    public boolean isContain(PartnerDTO partner1, PartnerDTO partner2) {
//        if (ObjectUtils.isEmpty(partner1.getP1()) || ObjectUtils.isEmpty(partner1.getP2())) {
//            return false;
//        }
//        if (ObjectUtils.isEmpty(partner2.getP1()) || ObjectUtils.isEmpty(partner2.getP2())) {
//            return false;
//        }
//        PlayerDTO p1 = partner1.getP1();
//        PlayerDTO p2 = partner1.getP2();
//
//        PlayerDTO p11 = partner2.getP1();
//        PlayerDTO p22 = partner2.getP2();
//
//        if (p1.equals(p11) || p1.equals(p22)) {
//            return true;
//        }
//        if (p2.equals(p11) || p2.equals(p22)) {
//            return true;
//        }
//        return false;
//    }
//
//    private void timesPoolSubtract(String nickname){
//        Integer times = playerTimesPool.get(nickname);
//        times--;
//        playerTimesPool.put(nickname, times);
//    }
//
//    private void timesPoolAdd(String nickname){
//        Integer times = playerTimesPool.get(nickname);
//        times++;
//        if(times >= everyonePlayTimes){
//            times = everyonePlayTimes;
//        }
//        playerTimesPool.put(nickname, times);
//    }
//}
