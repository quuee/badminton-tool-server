package cn.badminton.tool.tools.racetool;

import cn.badminton.tool.tools.SortTool;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 双打比赛生成对阵
 * 先生成搭档组合，再根据场地数量分配
 *
 * @param <T>
 */
@Deprecated
public class BadmintonDoubleRaceGeneratorTool2<T> {

    /**
     * 参赛人列表
     */
    private List<T> participations;

    /**
     * 每人上场次数
     */
    private Integer everyoneRoundNum;

    /**
     * 场地数
     */
    private Integer venueNum;

    /**
     * 总比赛场数 = A*(A-1)/4，A为参数选手人数，（A-1）每人上场次数
     * 双打4人起步
     * 4、5、8人时，每人上场次数 A-1，
     * 6、7人时，6人：可选4、6、9；7人时：选8、12。
     * 8人以上时，默认每人上场次数 8
     */
    private Integer raceAllRoundNum;

    /**
     * // 创建一个池，每个人总共 everyoneRoundNum 次，每分配一次 -1
     */
//    private LinkedHashMap<T, Integer> playerPool;


    /**
     * 已经组合过的搭档
     */
//    private Set<List<T>> usedPartnerSet;

    public BadmintonDoubleRaceGeneratorTool2(List<T> participations,
                                             Integer everyoneRoundNum,
                                             Integer venueNum) {
        this.participations = participations;
        this.everyoneRoundNum = everyoneRoundNum;
        this.venueNum = venueNum;
//        this.usedPartnerSet = new HashSet<>();
    }

    public List<List<T>> partnerGenerator() {
        List<List<T>> partnerGroupList = new ArrayList<>();
        ArrayList<T> playerList = new ArrayList<>(participations);
        // 创建一个池，每个人总共perRoundNum次，没到0说明还需要和其他人搭
        Map<T, Integer> pool = new LinkedHashMap<>();
        for (T value : playerList) {
            pool.put(value, everyoneRoundNum);
        }
        for (T t : pool.keySet()) {
            Integer round = pool.get(t);
            ArrayList<T> tempPlayerList = new ArrayList<>(playerList);
            tempPlayerList.remove(t);
            Integer tempRound = round;
            // 将未搭档次数最高的 排前面
            tempPlayerList.sort((o1, o2) -> {
                Integer round1 = pool.get(o1);
                Integer round2 = pool.get(o2);
                return round2 - round1;
            });
            for (int i = 0; i < round; i++) {
                if (tempPlayerList.isEmpty()) {
                    break;
                }
                ArrayList<T> partner = new ArrayList<>(2);
                T t2 = null;
                if (i >= tempPlayerList.size()) {
                    // 取未搭档次数最高
                    t2 = getRoundHighPlayer(tempPlayerList, pool);
                } else {
                    t2 = tempPlayerList.get(i);
                }
                partner.add(t);
                partner.add(t2);
                partnerGroupList.add(partner);
//                System.out.println(String.join("",String.valueOf(partner.get(0)),"==",String.valueOf(partner.get(1))));
                Integer roundOther = 0;
                roundOther = pool.get(t2);
                roundOther--;
                if (roundOther <= 0) {
                    playerList.remove(t2);
                    tempPlayerList.remove(t2);
                }
                pool.put(t2, roundOther);
                tempRound--;
            }
            if (tempRound <= 0) {
                playerList.remove(t);
            }
            pool.put(t, tempRound);
        }
        return partnerGroupList;
    }

    private T getRoundHighPlayer(List<T> list, Map<T, Integer> pool) {

        // 按分数分组，按从大到小排，同时同分的打乱
        Map<Integer, List<T>> listMap =
                list.stream().collect(Collectors.groupingBy(item -> pool.get(item)));

        Set<Integer> keySet = listMap.keySet();
        Integer[] array = keySet.toArray(Integer[]::new);

        SortTool.insertionSort(array);

        List<T> listss = new ArrayList<>();
        for (Integer i : array) {
            List<T> ts = listMap.get(i);
            Collections.shuffle(ts);
            listss.addAll(ts);
        }
        Collections.reverse(listss);
        T p = listss.getFirst();
        return p;
    }

    public List<List<List<T>>> battleGenerator(List<List<T>> partnerList) {
        // 所有对阵结果
        List<List<List<T>>> allBattleList = new ArrayList<>();
        // 最终对阵结果
        List<List<List<T>>> resultBattleList = new ArrayList<>();
        // 总场次
        raceAllRoundNum = participations.size() * (everyoneRoundNum) / 4;

        Map<T, Integer> pool = new LinkedHashMap<>();
        for (T value : participations) {
            pool.put(value, everyoneRoundNum);
        }

        for (Integer i = 0; i < raceAllRoundNum; i++) {
            // 先生成对阵
            List<List<T>> oneBattle = new ArrayList<>();
            boolean c = false;
            List<T> firstPartner;
            List<T> secondPartner;

            firstPartner = partnerList.getFirst();
            List<List<T>> excludePartner = getExcludePartner(firstPartner, partnerList);
            secondPartner = excludePartner.getFirst();

            oneBattle.add(firstPartner);
            oneBattle.add(secondPartner);
            allBattleList.add(oneBattle);
            System.out.println(String.format("第%d轮：%s - %s VS %s - %s",
                            i+1,
                    oneBattle.get(0).get(0),
                    oneBattle.get(0).get(1),
                    oneBattle.get(1).get(0),
                    oneBattle.get(1).get(1)
                    )
            );

            partnerList.remove(firstPartner);
            partnerList.remove(secondPartner);
        }

        for (int i = 0; i < Math.ceil((double) raceAllRoundNum / venueNum) ; i++) {
//            Collections.shuffle(allBattleList);
            List<T> currentRoundPlayerPool = new ArrayList<>(); // 本轮选手池，该轮出现过的选手不能再出场
            List<List<List<T>>> battleExcludePartner = new ArrayList<>(allBattleList);
            for (int v = 0; v < venueNum; v++) {
                if(allBattleList.isEmpty()){
                    // 最后一轮，不够就结束
                    break;
                }
                // 过滤掉包含这些选手的对阵
                battleExcludePartner =
                        battleExcludePartner(battleExcludePartner, currentRoundPlayerPool);
                List<List<T>> battle = battleExcludePartner.get(0);
//                System.out.println(String.format("第%d轮：%s - %s VS %s - %s",
//                        i+1,
//                        battle.get(0).get(0),
//                        battle.get(0).get(1),
//                        battle.get(1).get(0),
//                        battle.get(1).get(1)
//                        )
//                );
                resultBattleList.add(battle);
                allBattleList.remove(battle);
                // 将可对阵的数据加入最终对阵集合。可对阵数据按未出场排序
                addPlayerToCurrentPool(currentRoundPlayerPool,battle);
            }
            currentRoundPlayerPool.clear();
        }

        return resultBattleList;
    }

    /**
     * 未出场的靠前
     * @param battleList
     * @return
     */
    private List<List<List<T>>> battleSortByRoundNum(List<List<List<T>>> battleList){

        return null;
    }

    /**
     * 将本轮上场对阵的选手去除
     * @param battleList
     * @param players
     * @return
     */
    private List<List<List<T>>> battleExcludePartner(List<List<List<T>>> battleList,List<T> players){
        List<List<List<T>>> result = new ArrayList<>();
        for (int j = 0; j < battleList.size(); j++) {
            List<List<T>> battle = battleList.get(j);
            List<T> partner1 = battle.get(0);
            List<T> partner2 = battle.get(1);
            boolean contain1 = isContain(partner1, players);
            boolean contain2 = isContain(partner2, players);
            if(!contain1 && !contain2){
                // 1组 2组 同时都没有包含这些选手
                result.add(battle);
            }
        }
        return result;
    }

    private boolean isContain(List<T> list1, List<T> list2) {
        for (T t : list1) {
            for (T t1 : list2) {
                if(t.equals(t1)){
                    return true;
                }
            }
        }
        return false;
    }

    private List<List<T>> getExcludePartner(List<T> excludePartner,List<List<T>> allPartnerList){
        List<List<T>> alreadyExcludeList = new ArrayList<>();
        for (int i = 0; i < allPartnerList.size(); i++) {
            List<T> partner = allPartnerList.get(i);
            boolean contain = isContain(partner, excludePartner);
            if(!contain){
                alreadyExcludeList.add(partner);
            }
        }
        // 返回已排除集合
        return alreadyExcludeList;
    }

    private void addPlayerToCurrentPool(List<T> currentPool,List<List<T>> battle){
        for (List<T> ts : battle) {
            currentPool.addAll(ts);
        }
    }

//    private List<T> getRoundHighPartner(List<List<T>> list, Map<T, Integer> playerPool) {
//        // 按分数分组，同分的打乱，再按从大到小排
//        Map<Integer, List<T>> listMap =
//                list.stream().collect(Collectors.groupingBy(item -> playerPool.get(item)));
//
//        Set<Integer> keySet = listMap.keySet();
//        Integer[] array = keySet.toArray(Integer[]::new);
//
//        SortTool.insertionSort(array);
//
//        List<T> listss = new ArrayList<>();
//        for (Integer i : array) {
//            List<T> ts = listMap.get(i);
//            Collections.shuffle(ts);
//            listss.addAll(ts);
//        }
//        Collections.reverse(listss);
//        T p = listss.get(index);
//        return p;
//    }
}
