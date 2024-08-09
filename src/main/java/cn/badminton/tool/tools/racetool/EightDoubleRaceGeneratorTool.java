package cn.badminton.tool.tools.racetool;

import java.util.*;

/**
 * 双打 8人转生成比赛
 * 先生成搭档，再生成对阵
 * 8人转最多兼容4~13人，每人场次不超过八场
 * 一般一个场地，或两个场地
 * <p>
 * 6、7人转特例，无法整除
 * 6人 每人4场共6场（6*4/4=6），每人6场共9场（6*6/4=9），每人10场共15场（6*10/4=15）
 * 7人 每人8场共14场，每人12场共21场
 * <p>
 * 最佳人数：4、5、8、9。  10人以上可以用超八转
 */
@Deprecated
public class EightDoubleRaceGeneratorTool<T> {

    /**
     * 参赛人列表
     */
    private List<T> participations = new ArrayList<>();

    /**
     * 每人场次 = 参与人数 - 1
     * 超过8人时（放超八转），1、要么每人8场；2要么每个人都搭一遍。（全搭不要超过20人）
     * <p>
     * 默认1场定胜负，一局定胜负（1场） 三局两胜（2-3场） 五局三胜（3-5场）
     * <p>
     * 这个参数需要一开始就确定每人多少场：
     * 除了6人可选4、6、9；7人可选8、12；
     * 4、5、8人都是-1，
     * 9~13人都固定8
     */
    private Integer perRoundNum;

    /**
     * 几片场地
     */
    private Integer venueNum;

    /**
     * 总比赛场数 = A*(A-1)/4，A为参数选手人数
     */
    //private Integer raceRoundNum;
    public EightDoubleRaceGeneratorTool(List<T> participations, Integer perRoundNum, Integer venueNum) {
        this.participations.addAll(participations);
        this.perRoundNum = perRoundNum;
        this.venueNum = venueNum;
    }


    /**
     * 搭档生成
     *
     * @return
     */
    public List<List<T>> generatorPartner() {

        // 测试发现这个方法可以支持多种人数搭档，不能整除先不管，由业务处理。
        // 前提: 根据人数设置好每人N场，调用方法前先确定人数、场次
        return generator6_7SpecialPartner();


    }

    /**
     * 4~13人 不支持6 7人
     * 通用性不够
     *
     * @return
     */
    private List<List<T>> generator4_13Partner() {

        // 所有可能的组合
        List<List<T>> partnerGroupList = new ArrayList<>();
        // 生成组合
        for (int i = 0; i < participations.size(); i++) {
            // 第一位选手
            T first = participations.get(i);
            // 第二位选手，如果人数小于等于8人，每人场次是7（A-1）。
            // 如果超过8人，每人场次是8。（随机搭配8人）
            List<T> shuffleList = new ArrayList<>();

            if (i + 1 < participations.size()) {
                // 还需要把已经搭配的选手剔除
                shuffleList.addAll(participations.subList(i + 1, participations.size()));
            }

            Collections.shuffle(shuffleList);
            for (int j = 0; j < shuffleList.size(); j++) {

                T second = shuffleList.get(j);
                ArrayList<T> partner = new ArrayList<>(2);

                partner.add(first);
                partner.add(second);
                partnerGroupList.add(partner);
            }

        }
        return partnerGroupList;

    }

    /**
     * 6人或7人
     * 支持6人4场 6场 10场
     * 支持7人 8场 12场
     * <p>
     * 需要提前设置``perRoundNum``参数
     *
     * @return
     */
    private List<List<T>> generator6_7SpecialPartner() {
        List<List<T>> partnerGroupList = new ArrayList<>();
        ArrayList<T> tempList = new ArrayList<>(participations);
        // 创建一个池，每个人总共perRoundNum次，没到0说明还需要和其他人搭
        Map<T, Integer> pool = new LinkedHashMap<>();
        for (T value : tempList) {
            pool.put(value, perRoundNum);
        }

        for (T t : pool.keySet()) {
            Integer round = pool.get(t);
            ArrayList<T> ts = new ArrayList<>(tempList);
            ts.remove(t);
            Integer tempRound = round;
            // 将未搭档次数最高的 排前面
            ts.sort((o1, o2) -> {
                Integer round1 = pool.get(o1);
                Integer round2 = pool.get(o2);
                return round2 - round1;
            });
            for (int i = 0; i < round; i++) {
                if (ts.isEmpty()) {
                    break;
                }
                ArrayList<T> partner = new ArrayList<>(2);
                T t2 = null;
                if (i >= ts.size()) {
                    // 取未搭档次数最高
                    t2 = getRoundHight(ts, pool);
                } else {
                    t2 = ts.get(i);
                }
                partner.add(t);
                partner.add(t2);
                partnerGroupList.add(partner);
//                System.out.println(String.join("",String.valueOf(partner.get(0)),"==",String.valueOf(partner.get(1))));
                Integer roundOther = 0;
                roundOther = pool.get(t2);
                roundOther--;
                if (roundOther <= 0) {
                    tempList.remove(t2);
                    ts.remove(t2);
                }
                pool.put(t2, roundOther);
                tempRound--;
            }
            if (tempRound <= 0) {
                tempList.remove(t);
            }
            pool.put(t, tempRound);
        }
        return partnerGroupList;
    }

    /**
     * 取roundNum最高的
     *
     * @param list1
     * @param pool1
     * @return
     */
    private T getRoundHight(List<T> list1, Map<T, Integer> pool1) {
        list1.sort((o1, o2) -> {
            Integer round1 = pool1.get(o1);
            Integer round2 = pool1.get(o2);
            return round2 - round1;
        });
        return list1.getFirst();
    }

    /**
     * 对阵生成
     * 如果有多片场地
     *
     * @return
     */
    public List<List<List<T>>> generatorBattle(List<List<T>> partnerList) {

        // 总场次
        int allRound = partnerList.size() / 2;

        List<List<T>> partnerListClone = new ArrayList<>(partnerList);

        // 选手出场池，出场减一，未出场推到前面
        Map<T, Integer> pool = new LinkedHashMap<>();
        for (T value : participations) {
            pool.put(value, perRoundNum);
        }

        // 对阵列表
        List<List<List<T>>> battleList = new ArrayList<>(allRound);

        for (int i = 0; i < allRound; ) {
            // 根据有N片场地，每次进行N场，每次就是2N组合
            // 人数较多时，按顺序取会碰到最后一组自己对阵自己;
            // 多片场地，一个人只能出现一次
            List<T> thisRoundPlayerPool = new ArrayList<>(); // 本轮选手池，已出现在池的选手不能再连续出现
            // TODO 对阵是按顺序生成的，但是多片场地同时出现同样的人。应该生成对阵数据后，按轮分配多组对阵（不要把相同人分配到同一轮）
            for (Integer num = venueNum; num > 0; num--) {
                if(partnerListClone.isEmpty()){
                    break;
                }
                boolean f1 = true;

                List<T> fistPartner;
                List<T> secondPartner;
                int index = 0;
                do {
                    List<List<T>> unequiePartnerList = getUnequiePartnerList(thisRoundPlayerPool, partnerListClone);
                    fistPartner = unequiePartnerList.get(index);
                    secondPartner = getOneUnequiePartner(fistPartner, unequiePartnerList);
                    try {
                        if (secondPartner == null) {
                            throw new Exception("未找到能匹配的对阵组合");
                        }
                    } catch (Exception e) {

                    }

                    HashSet<T> set = new HashSet<>(4);
                    set.add(fistPartner.get(0));
                    set.add(fistPartner.get(1));
//                    assert secondPartner != null;
                    set.add(secondPartner.get(0));
                    set.add(secondPartner.get(1));
                    if (set.size() >= 4) {
                        f1 = false;
                    }
                    index++;
                } while (f1);

                thisRoundPlayerPool.clear();// 清空上一轮选手
                thisRoundPlayerPool.add(fistPartner.get(0));
                thisRoundPlayerPool.add(fistPartner.get(1));
                thisRoundPlayerPool.add(secondPartner.get(0));
                thisRoundPlayerPool.add(secondPartner.get(1));

                // 对阵集合
                List<List<T>> tempOneBattle = new ArrayList<>();
                tempOneBattle.add(fistPartner);
                tempOneBattle.add(secondPartner);
                battleList.add(tempOneBattle);

                // 删除已匹配对阵的组合
                partnerListClone.remove(fistPartner);
                partnerListClone.remove(secondPartner);

                for (T t : thisRoundPlayerPool) {
                    // 将已分配对阵的4人的所有组合 出场次数减一
                    Integer r = pool.get(t);
                    r--;
                    pool.put(t, r);
                }
                partnerListClone.sort(new Comparator<List<T>>() {
                    @Override
                    public int compare(List<T> o1, List<T> o2) {
                        T p1_1 = o1.get(0);
                        T p1_2 = o1.get(1);
                        T p2_1 = o2.get(0);
                        T p2_2 = o2.get(1);

                        int r1 = pool.get(p1_1) + pool.get(p1_2);
                        int r2 = pool.get(p2_1) + pool.get(p2_2);

                        // 按未出场从大到小排序
                        return r2 - r1;
                    }
                });
                i++;
            }

        }

        return battleList;
    }

    /**
     * 判断集合1中元素是否出现在集合2
     *
     * @param o1
     * @param o2
     * @return
     */
    private boolean isContain(List<T> o1, List<T> o2) {
        if (o1.isEmpty()) {
            return false;
        }
        for (T t : o1) {
            for (T t1 : o2) {
                boolean equals = t.equals(t1);
                if (equals) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 获取一个不同于第一组组合 的 对阵组合
     *
     * @param o1
     * @param partnerList
     * @return
     */
    private List<T> getOneUnequiePartner(List<T> o1, List<List<T>> partnerList) {
        for (int i = 1; i < partnerList.size(); i++) {
            boolean contain = isContain(o1, partnerList.get(i));
            if (!contain) {
                return partnerList.get(i);
            }
        }
        return null;
    }

    /**
     * 去除本轮已有对阵选手组合，选出未参与对阵的组合
     *
     * @param thisRound
     * @param partnerList
     * @return
     */
    private List<List<T>> getUnequiePartnerList(List<T> thisRound, List<List<T>> partnerList) {
        if (thisRound.isEmpty()) {
            return partnerList;
        }
        List<List<T>> NotInThisRoundPartner = new ArrayList<>();
        for (int i = 0; i < partnerList.size(); i++) {
            List<T> partner = partnerList.get(i);
            boolean flag = false;
            OUT:
            for (T t : partner) {
                for (T r : thisRound) {
                    if (t.equals(r)) {
                        flag = true;
                        break OUT;
                    }
                }
            }
            if (!flag) {
                NotInThisRoundPartner.add(partner);
            }
        }
        return NotInThisRoundPartner;
    }

}
