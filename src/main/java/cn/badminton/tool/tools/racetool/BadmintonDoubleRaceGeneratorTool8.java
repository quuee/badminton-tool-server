package cn.badminton.tool.tools.racetool;

import cn.badminton.tool.support.exception.ServerException;
import cn.badminton.tool.tools.SortTool;
import cn.badminton.tool.web.dto.BattleDTO;
import cn.badminton.tool.web.dto.PartnerDTO;
import cn.badminton.tool.web.dto.PlayerDTO;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 每次匹配对阵时，列出可以出场的选手，组合成所有可能的搭档，再随机取搭档
 */
public class BadmintonDoubleRaceGeneratorTool8 extends AbstractRaceGenerateTool {



    private HashSet<PartnerDTO> partnerSetAlready;

    /**
     * 对阵结果集
     */
    private Set<BattleDTO> resultBattles;


    public BadmintonDoubleRaceGeneratorTool8(List<PlayerDTO> players,
                                             Integer everyonePlayTimes,
                                             Integer venueNum) {
        super(players,everyonePlayTimes,venueNum);
        partnerSetAlready = new HashSet<>();
        resultBattles = new LinkedHashSet<>();
    }

//    public BadmintonDoubleRaceGeneratorTool8(){
//
//    }

//    private boolean checkParams(){
//        if(this.players.isEmpty() || ObjectUtils.isEmpty(everyonePlayTimes) || ObjectUtils.isEmpty(venueNum)){
//            return true;
//        }
//        return false;
//    }

    public void generatorBattle() throws ServerException {
//        boolean b = checkParams();
//        if(b){
//            throw new ServerException("未设置对阵参数");
//        }


        // 总场次
        int raceAllRoundNum = players.size() * (everyonePlayTimes) / 4;
        // 轮数
        double roundNum = Math.ceil((double) raceAllRoundNum / venueNum);

        for (int i = 0; i < roundNum; i++) {
            System.out.printf("开始第%d轮 \n", i + 1);
            // 本轮选手池，该轮出现过的选手不能再出场
            Set<PlayerDTO> currentRoundPlayerPool = new HashSet<>(0);
            for (int v = 0; v < venueNum; v++) {
                if (raceAllRoundNum <= 0) {
                    // 总场次结束
                    break;
                }
                Set<PlayerDTO> battlePlayerSet = new LinkedHashSet<>();

                List<PlayerDTO> newPlayers = getPossiblePlayers(currentRoundPlayerPool);
                if (newPlayers.isEmpty()) {
                    throw new ServerException("找不到选手,跳出死循环");
                }
                // 出场次数 -1
//                List<String> nameCollect = newPlayers.stream().map(PlayerDTO::getNickname).collect(Collectors.toList());
                timesPoolSubtract(newPlayers);
                // 减了之后，如果匹配不成功，要在本次匹配对阵后还回去
                List<PlayerDTO> tempBattleRepeatNameList = new ArrayList<>(newPlayers);

                List<List<PlayerDTO>> combineList
                        = SortTool.fourPlayerToCombine(newPlayers);
                List<PartnerDTO> battlePartner = getBattlePartner1(combineList);
//                List<PartnerDTO> battlePartner = getBattlePartner2(combineList);
                if(battlePartner.isEmpty()){
                    throw new ServerException("没有合适的组合");
                }
                battlePlayerSet.add(battlePartner.getFirst().getP1());
                battlePlayerSet.add(battlePartner.getFirst().getP2());
                battlePlayerSet.add(battlePartner.getLast().getP1());
                battlePlayerSet.add(battlePartner.getLast().getP2());

                List<PlayerDTO> playerDTOS = battlePlayerSet.stream().toList();
                BattleDTO doublesBattleDTO = new BattleDTO((int) i+1,playerDTOS.get(0), playerDTOS.get(1), playerDTOS.get(2), playerDTOS.get(3));
                doublesBattleDTO.setRoundNum(i + 1);
                resultBattles.add(doublesBattleDTO);

                currentRoundPlayerPool.add(playerDTOS.get(0));
                currentRoundPlayerPool.add(playerDTOS.get(1));
                currentRoundPlayerPool.add(playerDTOS.get(2));
                currentRoundPlayerPool.add(playerDTOS.get(3));

                PartnerDTO partner1 = doublesBattleDTO.getPartner1();
                PartnerDTO partner2 = doublesBattleDTO.getPartner2();
                partnerSetAlready.add(partner1);
                partnerSetAlready.add(partner2);
                raceAllRoundNum--;

                // 还原不匹配被多减的选手场次
                List<PlayerDTO> battleNameList = battlePlayerSet.stream().toList();
                // 去掉已筛选的组合
                tempBattleRepeatNameList.removeAll(battleNameList);
                // 未匹配的组合还原出场次数
                timesPoolAdd(tempBattleRepeatNameList);

//                System.out.printf("对阵: %s & %s VS %s & %s %n",
//                        battleDTO.getPartnerDTO1().getP1().getNickname(),
//                        battleDTO.getPartnerDTO1().getP2().getNickname(),
//                        battleDTO.getPartnerDTO2().getP1().getNickname(),
//                        battleDTO.getPartnerDTO2().getP2().getNickname());
            }
            currentRoundPlayerPool.clear();
        }
    }

    private List<PartnerDTO> getBattlePartner1(List<List<PlayerDTO>> combineList){
        // 按顺序取的方式，最后一组 3人互不能组合，1人和这3人都没组过。无法形成组合对阵

        List<PartnerDTO> partnerDTOS = new ArrayList<>();
        List<List<PlayerDTO>> cloneList = new ArrayList<>(combineList);

        OUT:
        for (int s = 0; s < combineList.size(); s++) {

            // 取第一组随机，偶尔能成功。看运气，只能多次运行
            Random random = new Random();
            int randomIndex = random.nextInt(0, cloneList.size());
            List<PlayerDTO> partnerList1 = cloneList.get(randomIndex);
            PlayerDTO pp1 = partnerList1.get(0);
            PlayerDTO pp2 = partnerList1.get(1);
            PartnerDTO part1 = new PartnerDTO();
            part1.setP1(pp1);
            part1.setP2(pp2);
            cloneList.remove(partnerList1);
            // 判断两人是否已经搭过
            boolean contain = partnerSetAlready.contains(part1);
            if(contain){
                continue ;
            }

            for (int m = s + 1; m < combineList.size(); m++) {
                List<PlayerDTO> partnerList2 = combineList.get(m);
                PlayerDTO pp11 = partnerList2.get(0);
                PlayerDTO pp22 = partnerList2.get(1);
                PartnerDTO part2 = new PartnerDTO();
                part2.setP1(pp11);
                part2.setP2(pp22);

                // 判断4人都不相同
                boolean contain1 = isContain(part1, part2);
                // 判断两人是否已经搭过
                boolean contain3 = partnerSetAlready.contains(part2);

                if (contain1 || contain3) {
                    //只要一个true 相同，跳过
                    continue;
                }
                partnerDTOS.add(part1);
                partnerDTOS.add(part2);
                break OUT;
            }

        }
        return partnerDTOS;
    }

    /**
     * 随机取两个组合。会死循环
     * 报名顺序随机就行，算法不能随机
     * @param combineList
     * @return
     */
    private List<PartnerDTO> getBattlePartner2(List<List<PlayerDTO>> combineList){
        List<PartnerDTO> partnerDTOS = new ArrayList<>();

        Collections.shuffle(combineList);

        for (int s = 0; s < combineList.size(); s++) {
            List<PlayerDTO> partnerList1 = combineList.get(s);
            PlayerDTO pp1 = partnerList1.get(0);
            PlayerDTO pp2 = partnerList1.get(1);
            PartnerDTO part1 = new PartnerDTO();
            part1.setP1(pp1);
            part1.setP2(pp2);
            Collections.shuffle(combineList);
            List<PlayerDTO> partnerList2 = combineList.getFirst();
            PlayerDTO pp11 = partnerList2.get(0);
            PlayerDTO pp22 = partnerList2.get(1);
            PartnerDTO part2 = new PartnerDTO();
            part2.setP1(pp11);
            part2.setP2(pp22);
            // 判断4人都不相同
            boolean contain1 = isContain(part1, part2);
            // 判断两人是否已经搭过
            boolean contain2 = partnerSetAlready.contains(part1);
            boolean contain3 = partnerSetAlready.contains(part2);

            if (contain1 || contain2 || contain3) {
                //只要一个true 相同，下一个
                continue;
            }
            partnerDTOS.add(part1);
            partnerDTOS.add(part2);
            break;
        }
        return partnerDTOS;
    }

    /**
     * @param currentRoundPlayerPool
     * @return
     */
    private List<PlayerDTO> getPossiblePlayers(Set<PlayerDTO> currentRoundPlayerPool) {
        for (PlayerDTO p : players) {
            Integer t = playerTimesPool.get(p);
            p.setEveryonePlayTimes(t);
        }
        ArrayList<PlayerDTO> playerDTOS = new ArrayList<>(players);

        // 去掉本轮已出场的选手
        for (int i = 0; i < players.size(); i++) {
            PlayerDTO p = players.get(i);
            for (PlayerDTO c : currentRoundPlayerPool) {
                if (p.equals(c)) {
                    playerDTOS.remove(p);
                    break;
                }
            }
        }

        // 分组
        Map<Integer, List<PlayerDTO>> groupMap = playerDTOS.stream().collect(Collectors.groupingBy(PlayerDTO::getEveryonePlayTimes));
        List<PlayerDTO> groupMapPlayers = new ArrayList<>();
        // 分组后 从高到低排，先取未上场
//        for (int i = everyonePlayTimes; i > 0; i--) {
//            List<PlayerDTO> ppp = groupMap.get(i);
//            if (ppp != null && !ppp.isEmpty()) {
//                groupMapPlayers.addAll(ppp);
//                break;
//            }
//        }

        // 未上场次排序
        Set<Integer> keySet = groupMap.keySet();
        Integer[] array = keySet.toArray(Integer[]::new);
        SortTool.insertionSort(array); // 小到大
        // 取最后的max
        int maxCurrentTimes = array[array.length > 0 ? array.length - 1 : 0];
        // 取2组 场次相差1的
        for (int key = maxCurrentTimes; key > maxCurrentTimes - 2 && key > 0; ) {
            List<PlayerDTO> ppp = groupMap.get(key);
            if (ppp != null && !ppp.isEmpty()) {
                groupMapPlayers.addAll(ppp);
            }
            //当选手场次都只剩1时，会取到0场次选手
            key--;
        }

        return groupMapPlayers;
    }

//    private Map<String,Integer> playerCurrentShowTimes(){
//        HashMap<String, Integer> showTimeMap = new HashMap<>();
//        for (PlayerDTO p : players) {
//            showTimeMap.put(p.getNickname(), 0);
//        }
//        Iterator<PartnerDTO> iterator = partnerSetAlready.iterator();
//        while (iterator.hasNext()){
//            PartnerDTO partnerDTO = iterator.next();
//            PlayerDTO p1 = partnerDTO.getP1();
//            PlayerDTO p2 = partnerDTO.getP2();
//            Integer showTime = showTimeMap.get(p1.getNickname());
//            showTime++;
//            showTimeMap.put(p1.getNickname(),showTime);
//
//            Integer showTime2 = showTimeMap.get(p2.getNickname());
//            showTime2++;
//            showTimeMap.put(p2.getNickname(),showTime2);
//        }
//        return showTimeMap;

    //        Map<String, Integer> showTimes = playerCurrentShowTimes();
//        groupMapPlayers.sort(new Comparator<PlayerDTO>() {
//            @Override
//            public int compare(PlayerDTO o1, PlayerDTO o2) {
//                Integer o1time = showTimes.get(o1.getNickname());
//                Integer o2time = showTimes.get(o2.getNickname());
//                return o1time - o2time;
//            }
//        });
//    }

    public boolean isContain(PartnerDTO partner1, PartnerDTO partner2) {
        if (ObjectUtils.isEmpty(partner1.getP1()) || ObjectUtils.isEmpty(partner1.getP2())) {
            return false;
        }
        if (ObjectUtils.isEmpty(partner2.getP1()) || ObjectUtils.isEmpty(partner2.getP2())) {
            return false;
        }
        PlayerDTO p1 = partner1.getP1();
        PlayerDTO p2 = partner1.getP2();

        PlayerDTO p11 = partner2.getP1();
        PlayerDTO p22 = partner2.getP2();

        if (p1.equals(p11) || p1.equals(p22)) {
            return true;
        }
        if (p2.equals(p11) || p2.equals(p22)) {
            return true;
        }
        return false;
    }

    public Set<BattleDTO> getResultBattles() {
        return resultBattles;
    }
}
