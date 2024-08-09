package cn.badminton.tool.tools.racetool;

import cn.badminton.tool.tools.SortTool;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 双打比赛生成对阵
 *
 */
@Deprecated
public class BadmintonDoubleRaceGeneratorTool4<T> {

    /**
     * 参赛人列表
     */
    private List<T> players;

    /**
     * 每人上场次数
     */
    private Integer everyonePlayTimes;

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
//    private LinkedHashMap<T, Integer> playerEveryonePlayTimes;
    public BadmintonDoubleRaceGeneratorTool4(List<T> players,
                                             Integer everyonePlayTimes,
                                             Integer venueNum) {
        this.players = players;
        this.everyonePlayTimes = everyonePlayTimes;
        this.venueNum = venueNum;

//        this.playerEveryonePlayTimes = new LinkedHashMap<>();
//        for (T value : players) {
//            playerEveryonePlayTimes.put(value, everyonePlayTimes);
//        }

    }

    /**
     * 生成搭档组合
     *
     * @return
     */
    public List<List<T>> generatorPartner() {
        // 搭档集合
        List<List<T>> partnerList = new ArrayList<>();
        // 副本
        ArrayList<T> playerList = new ArrayList<>(players);
        // 创建一个池，每个人总共perRoundNum次，没到0说明还需要和其他人搭
        Map<T, Integer> playerTimesPool = new LinkedHashMap<>();
        for (T value : playerList) {
            playerTimesPool.put(value, everyonePlayTimes);
        }

        for (Map.Entry<T, Integer> entry : playerTimesPool.entrySet()) {
            T key = entry.getKey();
            playerList.remove(key);
            while (entry.getValue() > 0) {
                List<T> tempPlayers = new ArrayList<>(playerList);
                tempPlayers = getTimeHighPlayer(tempPlayers, playerTimesPool);
                List<T> subList;
                if (entry.getValue() > tempPlayers.size()) {
                    subList = tempPlayers;
                } else {
                    subList = tempPlayers.subList(0, entry.getValue());
                }

                for (T t : subList) {
                    List<T> partner = new ArrayList<>();
                    partner.add(key);
                    partner.add(t);
                    partnerList.add(partner);

                    Integer times = entry.getValue();
                    times--;
                    playerTimesPool.put(key, times);
                    Integer tTimes = playerTimesPool.get(t);
                    tTimes--;
                    playerTimesPool.put(t, tTimes);
                }
            }
        }
        return partnerList;
    }

    private List<T> getTimeHighPlayer(List<T> list, Map<T, Integer> playerTimesPool) {

        // 按分数分组，同分的打乱，再按从大到小排。
        Map<Integer, List<T>> listMap =
                list.stream().collect(Collectors.groupingBy(item -> playerTimesPool.get(item)));

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

        return listss;
    }

    /**
     * 生成所有可能的对阵
     * 选手不能同时出场两边
     *
     * @return
     */
    public List<List<List<T>>> generatorAllPossibleBattle(List<List<T>> partnerList) {
        // 所有可能的对阵结果
        List<List<List<T>>> allPossibleBattleList = new ArrayList<>();

        for (Integer i = 0; i < partnerList.size(); i++) {
            List<T> firstPartner = partnerList.get(i);
            // 将第一组两位选手排除后，获得第二组选手集合
            List<List<T>> otherPartner = getExcludePartner(firstPartner, partnerList);
            // 将所有可能的对阵都加入
            for (List<T> secondPartner : otherPartner) {
                List<List<T>> oneBattle = new ArrayList<>();
                oneBattle.add(firstPartner);
                oneBattle.add(secondPartner);
                allPossibleBattleList.add(oneBattle);
            }
        }
        // TODO 所有对阵次数
        createBattlePool();

        return allPossibleBattleList;
    }

    /**
     * TODO 根据所有可能的对阵，创建一个已对阵次数池。根据选手和选手间的对阵次数 调整分配比赛，不能一个选手总是和某个人对阵
     */
    public void createBattlePool(){

    }

    private List<List<T>> getExcludePartner(List<T> excludePartner, List<List<T>> allPartnerList) {
        List<List<T>> alreadyExcludeList = new ArrayList<>();
        for (int i = 0; i < allPartnerList.size(); i++) {
            List<T> partner = allPartnerList.get(i);
            boolean contain = isContain(partner, excludePartner);
            if (!contain) {
                alreadyExcludeList.add(partner);
            }
        }
        // 返回已排除集合
        return alreadyExcludeList;
    }

    private boolean isContain(List<T> list1, List<T> list2) {
        for (T t : list1) {
            for (T t1 : list2) {
                if (t.equals(t1)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 开始 列出真实可用的对阵
     * @param allPossibleBattleList
     * @return
     */
    public List<List<List<T>>> startBattle(List<List<List<T>>> allPossibleBattleList){
        // 总场次
        raceAllRoundNum = players.size() * (everyonePlayTimes) / 4;
        for (int i = 0; i < allPossibleBattleList.size(); i++) {

        }

        return null;
    }

}
