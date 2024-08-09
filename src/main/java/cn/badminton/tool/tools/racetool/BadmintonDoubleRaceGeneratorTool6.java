//package cn.badminton.tool.tools.racetool;
//
//import cn.badminton.tool.tools.SortTool;
//import cn.badminton.tool.web.dto.PartnerDTO;
//import cn.badminton.tool.web.dto.PlayerDTO;
//
//import java.util.*;
//import java.util.stream.Collectors;
//@Deprecated
//public class BadmintonDoubleRaceGeneratorTool6 {
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
//    private Map<PlayerDTO, Integer> playerTimesPool;
//
//    public BadmintonDoubleRaceGeneratorTool6(List<PlayerDTO> players,
//                                             Integer everyonePlayTimes,
//                                             Integer venueNum) {
//        this.players = players;
//        this.everyonePlayTimes = everyonePlayTimes;
//        this.venueNum = venueNum;
//
////        this.playerEveryonePlayTimes = new LinkedHashMap<>();
////        for (T value : players) {
////            playerEveryonePlayTimes.put(value, everyonePlayTimes);
////        }
//
//    }
//
//    /**
//     * 生成所有搭档组合
//     *
//     * @return
//     */
//    public Set<PartnerDTO> generatorPartner() {
//        // 搭档集合
//        Set<PartnerDTO> partnerList = new HashSet<>();
//        // 副本
//        ArrayList<PlayerDTO> playerList = new ArrayList<>(players);
//        // 创建一个池，每个人总共perRoundNum次，没到0说明还需要和其他人搭
//        Map<PlayerDTO, Integer> playerTimesPool = new LinkedHashMap<>();
//        for (PlayerDTO value : playerList) {
//            if (everyonePlayTimes > playerList.size()) {
//                playerTimesPool.put(value, everyonePlayTimes);
//            } else {
//                // 干脆直接和所有人搭一遍
//                playerTimesPool.put(value, playerList.size() - 1);
//            }
//
//        }
//
//        for (Map.Entry<PlayerDTO, Integer> entry : playerTimesPool.entrySet()) {
//            PlayerDTO key = entry.getKey();
//            playerList.remove(key);
//            while (entry.getValue() > 0) {
//                List<PlayerDTO> tempPlayers = new ArrayList<>(playerList);
//                tempPlayers = getTimeHighPlayer(tempPlayers, playerTimesPool);
//                List<PlayerDTO> subList;
//                if (entry.getValue() > tempPlayers.size()) {
//                    subList = tempPlayers;
//                } else {
//                    subList = tempPlayers.subList(0, entry.getValue());
//                }
//                for (PlayerDTO t : subList) {
//                    PartnerDTO partner = new PartnerDTO();
//                    partner.setP1(key);
//                    partner.setP2(t);
//                    partnerList.add(partner);
//
//                    Integer times = entry.getValue();
//                    times--;
//                    playerTimesPool.put(key, times);
//                    Integer tTimes = playerTimesPool.get(t);
//                    tTimes--;
//                    playerTimesPool.put(t, tTimes);
//                }
//            }
//        }
//        return partnerList;
//    }
//
//    private List<PlayerDTO> getTimeHighPlayer(List<PlayerDTO> list,
//                                              Map<PlayerDTO, Integer> playerTimesPool) {
//
//        // 按分数分组，同分的打乱，再按从大到小排。
//        Map<Integer, List<PlayerDTO>> listMap =
//                list.stream().collect(Collectors.groupingBy(item -> playerTimesPool.get(item)));
//
//        Set<Integer> keySet = listMap.keySet();
//        Integer[] array = keySet.toArray(Integer[]::new);
//
//        SortTool.insertionSort(array);
//
//        List<PlayerDTO> listss = new ArrayList<>();
//        for (Integer i : array) {
//            List<PlayerDTO> ts = listMap.get(i);
//            Collections.shuffle(ts);
//            listss.addAll(ts);
//        }
//        Collections.reverse(listss);
//
//        return listss;
//    }
//
//    private boolean isContain(PartnerDTO partner1, PartnerDTO partner2) {
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
//    public Set<DoublesBattleDTO> generatorBattle(Set<PartnerDTO> allPossiblePartnerSet){
//        // 已搭搭档池 每人各搭一次，防止重复。因为都在allPossiblePartnerSet里，搭过删除即可
////        HashSet<PartnerDTO> partnerSetAlready = new HashSet<>();
//
//        // 已有对阵池
//        Set<DoublesBattleDTO> resultBattles = new HashSet<>();
//        // 选手上场次数池 上场后须-1
//        playerTimesPool = new LinkedHashMap<>();
//        for (PlayerDTO value : players) {
//            playerTimesPool.put(value, everyonePlayTimes);
//        }
//
//        // 选手坐冷板凳数，不能太久没打。
//        Map<PlayerDTO, Integer> playerLastTimesPool = new LinkedHashMap<>();
//        for (PlayerDTO value : players) {
//            playerLastTimesPool.put(value, 0);
//        }
//
//        // 总场次
//        Integer raceAllRoundNum = players.size() * (everyonePlayTimes) / 4;
//        // 轮数
//        double roundNum = Math.ceil((double) raceAllRoundNum / venueNum);
//        for (int i = 0; i < roundNum; i++) {
//            System.out.println(String.format("第%d轮", i + 1));
//            Set<PlayerDTO> currentRoundPlayerPool = new HashSet<>(0); // 本轮选手池，该轮出现过的选手不能再出场
//            for (int v = 0; v < venueNum; v++) {
//                if (raceAllRoundNum <= 0) {
//                    // 总场次结束
//                    break;
//                }
//                // 过滤已上场选手的组合
//                Set<PartnerDTO> filterPartnerSet
//                        = filterByPlayer(allPossiblePartnerSet, currentRoundPlayerPool);
//                // 过滤耗尽场次的选手组合
//                filterPartnerSet = filterNoTimesPlayer(filterPartnerSet);
//                // 未上场次多的排前面
//                // 距离上把太久的选手排前面
//                List<PartnerDTO> partnerDTOS = SortByPlayTimesAndLastTime(filterPartnerSet,playerLastTimesPool);
//                // 一个对阵 必须不同的4个选手
//                // TODO 每次到最后一组 组不起来。
//                DoublesBattleDTO aBattle = getPartner(partnerDTOS,resultBattles);
//                assert aBattle != null;
//                resultBattles.add(aBattle);
//
//                // 每配对一场，选手上场次数池 -1
//                PlayerDTO p1 = aBattle.getPartnerDTO1().getP1();
//                PlayerDTO p2 = aBattle.getPartnerDTO1().getP2();
//                PlayerDTO p11 = aBattle.getPartnerDTO2().getP1();
//                PlayerDTO p22 = aBattle.getPartnerDTO2().getP2();
//                List<PlayerDTO> playerDTOS = new ArrayList<>(4);
//                playerDTOS.add(p1);
//                playerDTOS.add(p2);
//                playerDTOS.add(p11);
//                playerDTOS.add(p22);
//                for (PlayerDTO playerDTO : playerDTOS) {
//                    Integer times = playerTimesPool.get(playerDTO);
//                    times--;
//                    // 减去上场次数
//                    playerTimesPool.put(playerDTO, times);
//                    // 添加到该轮选手池
//                    currentRoundPlayerPool.add(playerDTO);
//                }
//                lastTimesPlus(playerLastTimesPool,playerDTOS);
//
//                // 添加到已搭搭档池
////                partnerSetAlready.add(partner1);
////                partnerSetAlready.add(partner2);
//                // 从所有搭档组合中删除
//                allPossiblePartnerSet.remove(aBattle.getPartnerDTO1());
//                allPossiblePartnerSet.remove(aBattle.getPartnerDTO2());
//                raceAllRoundNum--;
//
//                String format = String.format("对阵：%s & %s VS %s & %s",
//                        aBattle.getPartnerDTO1().getP1().getNickName(),
//                        aBattle.getPartnerDTO1().getP2().getNickName(),
//                        aBattle.getPartnerDTO2().getP1().getNickName(),
//                        aBattle.getPartnerDTO2().getP2().getNickName()
//                );
//                System.out.println(format);
//            }
//            currentRoundPlayerPool.clear();
//        }
//        return resultBattles;
//    }
//
//    /**
//     *
//     * @param partnerSet 选手组合
//     * @param currentRoundPlayerPool
//     * @return
//     */
//    private Set<PartnerDTO> filterByPlayer(Set<PartnerDTO> partnerSet,Set<PlayerDTO> currentRoundPlayerPool){
//        HashSet<PartnerDTO> partnerDTOS = new HashSet<>(partnerSet);
//        for (PlayerDTO playerDTO : currentRoundPlayerPool) {
//            for (PartnerDTO partnerDTO : partnerSet) {
//                PlayerDTO p1 = partnerDTO.getP1();
//                PlayerDTO p2 = partnerDTO.getP2();
//                if(playerDTO.equals(p1) || playerDTO.equals(p2)){
//                    partnerDTOS.remove(partnerDTO);
//                }
//            }
//        }
//        return partnerDTOS;
//    }
//
//    /**
//     *
//     * @param partnerSet 选手组合
//     * @return
//     */
//    private Set<PartnerDTO> filterNoTimesPlayer(Set<PartnerDTO> partnerSet){
//        Set<PartnerDTO> partnerDTOS = new HashSet<>();
//        for (PartnerDTO partnerDTO : partnerSet) {
//            PlayerDTO p1 = partnerDTO.getP1();
//            PlayerDTO p2 = partnerDTO.getP2();
//            Integer times1 = playerTimesPool.get(p1);
//            Integer times2 = playerTimesPool.get(p2);
//            if (times1 > 0 && times2 >0) {
//                partnerDTOS.add(partnerDTO);
//            }
//        }
//        return partnerDTOS;
//    }
//
//    /**
//     *
//     * @param partnerSet 选手组合
//     * @param playerLastTimesPool
//     * @return
//     */
//    private List<PartnerDTO> SortByPlayTimesAndLastTime(Set<PartnerDTO> partnerSet,
//                                             Map<PlayerDTO, Integer> playerLastTimesPool){
//        List<PartnerDTO> list = new ArrayList<>(partnerSet.stream().toList());
//        list.sort(new Comparator<PartnerDTO>() {
//            @Override
//            public int compare(PartnerDTO o1, PartnerDTO o2) {
//                PlayerDTO p1 = o1.getP1();
//                PlayerDTO p2 = o1.getP2();
//                PlayerDTO p11 = o2.getP1();
//                PlayerDTO p22 = o2.getP2();
//
//                Integer t1 = playerTimesPool.get(p1);
//                Integer t2 = playerTimesPool.get(p2);
//                Integer t11 = playerTimesPool.get(p11);
//                Integer t22 = playerTimesPool.get(p22);
//
//                Integer l1 = playerLastTimesPool.get(p1);
//                Integer l2 = playerLastTimesPool.get(p2);
//                Integer l11 = playerLastTimesPool.get(p11);
//                Integer l22 = playerLastTimesPool.get(p22);
//                // 先按上场次数排
//                Integer tnum = (t22+t11) - (t2+t1);
//                // 再按坐冷板凳次数
//                Integer lnum = (l22+l11) - (l2+l1);
//                if(tnum != 0){
//                    return tnum;
//                }
//                return lnum;
//            }
//        });
//        return list;
//    }
//    private List<PartnerDTO> SortByPlayTimes(List<PartnerDTO> partnerDTOList){
//        List<PartnerDTO> list = new ArrayList<>(partnerDTOList);
//        list.sort(new Comparator<PartnerDTO>() {
//            @Override
//            public int compare(PartnerDTO o1, PartnerDTO o2) {
//                PlayerDTO p1 = o1.getP1();
//                PlayerDTO p2 = o1.getP2();
//                PlayerDTO p11 = o2.getP1();
//                PlayerDTO p22 = o2.getP2();
//
//                Integer t1 = playerTimesPool.get(p1);
//                Integer t2 = playerTimesPool.get(p2);
//                Integer t11 = playerTimesPool.get(p11);
//                Integer t22 = playerTimesPool.get(p22);
//
////                Integer l1 = playerLastTimesPool.get(p1);
////                Integer l2 = playerLastTimesPool.get(p2);
////                Integer l11 = playerLastTimesPool.get(p11);
////                Integer l22 = playerLastTimesPool.get(p22);
//                // 先按上场次数排
//                Integer tnum = (t22+t11) - (t2+t1);
//                // 再按坐冷板凳次数
////                Integer lnum = (l22+l11) - (l2+l1);
////                if(tnum != 0){
////                    return tnum;
////                }
//                return tnum;
//            }
//        });
//        return list;
//    }
//    /**
//     *
//     * @param partnerDTOS
//     * @param doublesBattleDTOSet
//     * @return
//     */
//    private DoublesBattleDTO getPartner(List<PartnerDTO> partnerDTOS, Set<DoublesBattleDTO> doublesBattleDTOSet){
//
//        Set<PartnerDTO> partnerDTOS2Clone = new LinkedHashSet<>(partnerDTOS);
//        for (int i = 0; i < partnerDTOS.size(); i++) {
//            PartnerDTO partnerDTO = partnerDTOS.get(i);
//            partnerDTOS2Clone.remove(partnerDTO);
//            // 过滤第一组已有选手
//            Set<PlayerDTO> ppp = new LinkedHashSet<>();
//            ppp.add(partnerDTO.getP1());
//            ppp.add(partnerDTO.getP2());
//            Set<PartnerDTO> filterSet = filterByPlayer(partnerDTOS2Clone, ppp);
//            List<PartnerDTO> list = filterSet.stream().toList();
//
//            // 获取搭档时，搭档的出场次数要相同，其他随机
////            List<Map.Entry<PlayerDTO, Integer>> poolMapList = playerTimesPool.entrySet().stream().toList();
////            poolMapList.sort(new Comparator<Map.Entry<PlayerDTO, Integer>>() {
////                @Override
////                public int compare(Map.Entry<PlayerDTO, Integer> o1, Map.Entry<PlayerDTO, Integer> o2) {
////                    return o2.getValue().compareTo(o1.getValue());
////                }
////            });
//            SortByPlayTimes(list);
//
//            for (int j = 0; j < list.size(); j++) {
//                DoublesBattleDTO aBattle = new DoublesBattleDTO();
//                aBattle.setPartnerDTO1(partnerDTO);
//                aBattle.setPartnerDTO2(list.get(j));
//                boolean contains = doublesBattleDTOSet.contains(aBattle);
//                if(!contains){
//                    return aBattle;
//                }
//            }
//
//        }
//        return null;
//    }
//
//
//    /**
//     *
//     * @param playerLastTimesPool 这些选手归零，其他选手加一
//     * @param players
//     */
//    private void lastTimesPlus( Map<PlayerDTO, Integer> playerLastTimesPool,List<PlayerDTO> players){
//        for (Map.Entry<PlayerDTO, Integer> playerDTOIntegerEntry : playerLastTimesPool.entrySet()) {
//            Integer value = playerDTOIntegerEntry.getValue();
//            value++;
//            playerDTOIntegerEntry.setValue(value);
//        }
//        for (PlayerDTO player : players) {
//            playerLastTimesPool.put(player,0);
//        }
//    }
////    private Map<BattleRelation, Integer> battlePool(BattleDTO aBattle){
////        PlayerDTO p1 = aBattle.getPartnerDTO1().getP1();
////        PlayerDTO p2 = aBattle.getPartnerDTO1().getP2();
////        PlayerDTO p11 = aBattle.getPartnerDTO2().getP1();
////        PlayerDTO p22 = aBattle.getPartnerDTO2().getP2();
////
////        BattleRelation battleRelation1 = new BattleRelation();
////        battleRelation1.setP1(p1);
////        battleRelation1.setP2(p11);
////        battleRelation1.setBattleNum(1);
////        BattleRelation battleRelation2 = new BattleRelation();
////        battleRelation2.setP1(p1);
////        battleRelation2.setP2(p22);
////        battleRelation2.setBattleNum(1);
////        BattleRelation battleRelation3 = new BattleRelation();
////        battleRelation3.setP1(p2);
////        battleRelation3.setP2(p11);
////        battleRelation3.setBattleNum(1);
////        BattleRelation battleRelation4 = new BattleRelation();
////        battleRelation4.setP1(p2);
////        battleRelation4.setP2(p22);
////        battleRelation4.setBattleNum(1);
////
////        Map<BattleRelation, Integer> battleRelationIntegerHashMap = new HashMap<>();
////        battleRelationIntegerHashMap.put(battleRelation1,1);
////        battleRelationIntegerHashMap.put(battleRelation2,1);
////        battleRelationIntegerHashMap.put(battleRelation3,1);
////        battleRelationIntegerHashMap.put(battleRelation4,1);
////        return battleRelationIntegerHashMap;
////    }
//
//}
