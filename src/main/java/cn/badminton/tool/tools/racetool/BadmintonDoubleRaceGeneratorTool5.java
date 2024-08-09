//package cn.badminton.tool.tools.racetool;
//
//import cn.badminton.tool.tools.SortTool;
//import cn.badminton.tool.web.dto.BattleDTO;
//import cn.badminton.tool.web.dto.PartnerDTO;
//import cn.badminton.tool.web.dto.PlayerDTO;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * 双打比赛生成对阵
// * 1生成搭档2生成所有对阵3最终对阵
// * TODO 上场次数超过人数会报错（因为重复组合被过滤，取不到值）
// */
//@Deprecated
//public class BadmintonDoubleRaceGeneratorTool5 {
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
//     * 总比赛场数 = A*(A-1)/4，A为参数选手人数，（A-1）每人上场次数
//     * 双打4人起步
//     * 4、5、8人时，每人上场次数 A-1，
//     * 6、7人时，6人：可选4、6、9；7人时：选8、12。
//     * 8人以上时，默认每人上场次数 8
//     */
//    private Integer raceAllRoundNum;
//
//
//    public BadmintonDoubleRaceGeneratorTool5(List<PlayerDTO> players,
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
//    public List<PartnerDTO> generatorPartner() {
//        // 搭档集合
//        List<PartnerDTO> partnerList = new ArrayList<>();
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
//    /**
//     * 生成所有可能的对阵
//     * 选手不能同时出场两边
//     *
//     * @return
//     */
//    public List<BattleDTO> generatorAllPossibleBattle(List<PartnerDTO> partnerList) {
//        // 所有可能的对阵结果
//        List<BattleDTO> allPossibleBattleList = new ArrayList<>();
//        List<PartnerDTO> tempPartnerList = new ArrayList<>(partnerList);
//        for (PartnerDTO firstPartner : partnerList) {
//            // 将第一组两位选手排除后，获得第二组选手集合
//            List<PartnerDTO> otherPartner = excludePartnerFromPartner(firstPartner, tempPartnerList);
//            // 将所有可能的对阵都加入
//            for (PartnerDTO secondPartner : otherPartner) {
//                BattleDTO oneBattle = new BattleDTO();
//                oneBattle.setPartnerDTO1(firstPartner);
//                oneBattle.setPartnerDTO2(secondPartner);
//                allPossibleBattleList.add(oneBattle);
//            }
//            // 去除已分配组合
//            tempPartnerList.remove(firstPartner);
//        }
//
//        return allPossibleBattleList;
//    }
//
//
//    /**
//     * 去除已经存在的组合
//     *
//     * @param excludePartner 分配的组合
//     * @param allPartnerList 这个参数在外边方法中将分配过的组合删除
//     * @return
//     */
//    private List<PartnerDTO> excludePartnerFromPartner(PartnerDTO excludePartner, List<PartnerDTO> allPartnerList) {
//        List<PartnerDTO> alreadyExcludeList = new ArrayList<>();
//        for (int i = 0; i < allPartnerList.size(); i++) {
//            PartnerDTO partner = allPartnerList.get(i);
//            boolean contain = isContain(partner, excludePartner);
//            if (!contain) {
//                alreadyExcludeList.add(partner);
//            }
//        }
//        // 返回已排除集合
//        return alreadyExcludeList;
//    }
//
//    public boolean isContain(PartnerDTO partner1, PartnerDTO partner2) {
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
//    /**
//     * 开始 列出真实可用的对阵
//     *
//     * @param allPossibleBattleList
//     * @return
//     */
//    public List<DoublesBattleDTO> startBattle(List<DoublesBattleDTO> allPossibleBattleList) {
//        List<DoublesBattleDTO> tempBattleList = new ArrayList<>(allPossibleBattleList);
//        List<DoublesBattleDTO> resultBattles = new ArrayList<>();
//        // 所有已对阵(匹配)次数池
////        Map<BattleDTO, List<BattleRelation>> battlePool
////                = createBattlePool(allPossibleBattleList);
//
//        // 每人各搭一次，防止重复
//        HashSet<PartnerDTO> partnerSet = new HashSet<>();
//
//        // 选手上场次数池
//        Map<PlayerDTO, Integer> playerTimesPool = new LinkedHashMap<>();
//        for (PlayerDTO value : players) {
//            playerTimesPool.put(value, everyonePlayTimes);
//        }
//        // 总场次
//        raceAllRoundNum = players.size() * (everyonePlayTimes) / 4;
//        // 轮数
//        double roundNum = Math.ceil((double) raceAllRoundNum / venueNum);
//
//        for (int i = 0; i < roundNum; i++) {
//            System.out.println(String.format("第%d轮", i + 1));
//            List<PlayerDTO> currentRoundPlayerPool = new ArrayList<>(); // 本轮选手池，该轮出现过的选手不能再出场
//            for (int v = 0; v < venueNum; v++) {
//                if (raceAllRoundNum <= 0) {
//                    // 总场次结束
//                    break;
//                }
//                // 判断本轮选手池是否包含该对阵选手，有则过滤
//                List<DoublesBattleDTO> temptempBattles
//                        = filterBattleFromRoundPlayer(tempBattleList, currentRoundPlayerPool);
//                // 按上场次数大小排序
//                battlePoolSort(temptempBattles, playerTimesPool);
//                // 过滤选手上场次数为0的对阵
//                temptempBattles = filterNoTimesPlayer(temptempBattles, playerTimesPool);
//
//                DoublesBattleDTO aBattle = temptempBattles.getFirst();
//                resultBattles.add(aBattle);
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
//                partnerSet.add(aBattle.getPartnerDTO1());
//                partnerSet.add(aBattle.getPartnerDTO2());
//
//                // 删除该对阵
//                tempBattleList.remove(aBattle);
//                // 在对阵集合中删除该组合，避免重复搭
//                tempBattleList = filterRepeatPartner(tempBattleList, partnerSet);
//                raceAllRoundNum--;
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
//
//        return resultBattles;
//    }
//
//    /**
//     * 只要battleList中选手不能是currentRoundPlayerPool中任意一个
//     *
//     * @param battleList
//     * @param currentRoundPlayerPool
//     */
//    private List<DoublesBattleDTO> filterBattleFromRoundPlayer(List<DoublesBattleDTO> battleList, List<PlayerDTO> currentRoundPlayerPool) {
//        List<DoublesBattleDTO> tempBattleList = new ArrayList<>();
//        for (int i = 0; i < battleList.size(); i++) {
//            DoublesBattleDTO doublesBattleDTO = battleList.get(i);
//            PlayerDTO p1 = doublesBattleDTO.getPartnerDTO1().getP1();
//            PlayerDTO p2 = doublesBattleDTO.getPartnerDTO1().getP2();
//            PlayerDTO p11 = doublesBattleDTO.getPartnerDTO2().getP1();
//            PlayerDTO p22 = doublesBattleDTO.getPartnerDTO2().getP2();
//            List<PlayerDTO> tempPlayerDTOS = new ArrayList<>();
//            tempPlayerDTOS.add(p1);
//            tempPlayerDTOS.add(p2);
//            tempPlayerDTOS.add(p11);
//            tempPlayerDTOS.add(p22);
//            boolean c = false;
//            OUT:
//            for (PlayerDTO playerDTO : currentRoundPlayerPool) {
//                for (PlayerDTO tempPlayerDTO : tempPlayerDTOS) {
//                    c = playerDTO.equals(tempPlayerDTO);
//                    if (c) {
//                        break OUT;
//                    }
//                }
//            }
//            if (!c) {
//                tempBattleList.add(doublesBattleDTO);
//            }
//        }
//        return tempBattleList;
//    }
//
//    private List<DoublesBattleDTO> filterNoTimesPlayer(List<DoublesBattleDTO> battleList, Map<PlayerDTO, Integer> playerTimesPool) {
//        List<DoublesBattleDTO> tempBattleList = new ArrayList<>();
//        for (int i = 0; i < battleList.size(); i++) {
//            DoublesBattleDTO doublesBattleDTO = battleList.get(i);
//            PlayerDTO p1 = doublesBattleDTO.getPartnerDTO1().getP1();
//            PlayerDTO p2 = doublesBattleDTO.getPartnerDTO1().getP2();
//            PlayerDTO p11 = doublesBattleDTO.getPartnerDTO2().getP1();
//            PlayerDTO p22 = doublesBattleDTO.getPartnerDTO2().getP2();
//            List<PlayerDTO> tempPlayerDTOS = new ArrayList<>();
//            tempPlayerDTOS.add(p1);
//            tempPlayerDTOS.add(p2);
//            tempPlayerDTOS.add(p11);
//            tempPlayerDTOS.add(p22);
//            boolean c = false;
//            for (PlayerDTO tempPlayerDTO : tempPlayerDTOS) {
//                Integer times = playerTimesPool.get(tempPlayerDTO);
//                if (times <= 0) {
//                    c = true;
//                }
//            }
//            if (!c) {
//                tempBattleList.add(doublesBattleDTO);
//            }
//        }
//        return tempBattleList;
//    }
//
//    private List<DoublesBattleDTO> filterRepeatPartner(List<DoublesBattleDTO> battleList, HashSet<PartnerDTO> partnerSet) {
//        List<DoublesBattleDTO> tempBattleList = new ArrayList<>(battleList);
//        for (int i = 0; i < tempBattleList.size(); i++) {
//            DoublesBattleDTO doublesBattleDTO = tempBattleList.get(i);
//            PartnerDTO partnerDTO1 = doublesBattleDTO.getPartnerDTO1();
//            PartnerDTO partnerDTO2 = doublesBattleDTO.getPartnerDTO2();
//            for (PartnerDTO partnerDTO : partnerSet) {
//                if (partnerDTO1.equals(partnerDTO) || partnerDTO2.equals(partnerDTO)) {
//                    battleList.remove(doublesBattleDTO);
//                }
//            }
//        }
//        return battleList;
//    }
//
//    private void battlePoolSort(List<DoublesBattleDTO> battleList, Map<PlayerDTO, Integer> playerTimesPool) {
//
//        battleList.sort(new Comparator<DoublesBattleDTO>() {
//            @Override
//            public int compare(DoublesBattleDTO o1, DoublesBattleDTO o2) {
//                PlayerDTO p1_1 = o1.getPartnerDTO1().getP1();
//                PlayerDTO p1_2 = o1.getPartnerDTO1().getP2();
//                PlayerDTO p1_3 = o1.getPartnerDTO2().getP1();
//                PlayerDTO p1_4 = o1.getPartnerDTO2().getP2();
//                Integer r1_1 = playerTimesPool.get(p1_1);
//                Integer r1_2 = playerTimesPool.get(p1_2);
//                Integer r1_3 = playerTimesPool.get(p1_3);
//                Integer r1_4 = playerTimesPool.get(p1_4);
//                Integer r1 = r1_1 + r1_2 + r1_3 + r1_4;
//
//                PlayerDTO p2_1 = o2.getPartnerDTO1().getP1();
//                PlayerDTO p2_2 = o2.getPartnerDTO1().getP2();
//                PlayerDTO p2_3 = o2.getPartnerDTO2().getP1();
//                PlayerDTO p2_4 = o2.getPartnerDTO2().getP2();
//                Integer r2_1 = playerTimesPool.get(p2_1);
//                Integer r2_2 = playerTimesPool.get(p2_2);
//                Integer r2_3 = playerTimesPool.get(p2_3);
//                Integer r2_4 = playerTimesPool.get(p2_4);
//                Integer r2 = r2_1 + r2_2 + r2_3 + r2_4;
//
//                return r2 - r1;
//            }
//        });
//
//    }
//
//
//}
