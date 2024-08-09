package cn.badminton.tool.tools.racetool;

import cn.badminton.tool.support.exception.ServerException;
import cn.badminton.tool.tools.SortTool;
import cn.badminton.tool.web.dto.BattleDTO;
import cn.badminton.tool.web.dto.PlayerDTO;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 单打
 * 每位选手跟其他选手各比赛一场
 * 比赛场数=A*(A-1)/2，A为选手人数
 */
public class BadmintonSingleRaceGeneratorTool extends AbstractRaceGenerateTool {


    /**
     * 对阵结果集
     */
    private HashSet<BattleDTO> SinglesBattleDTOResultSet;

    public BadmintonSingleRaceGeneratorTool(List<PlayerDTO> players,
                                            Integer everyonePlayTimes,
                                            Integer venueNum){
        super(players,everyonePlayTimes,venueNum);
        SinglesBattleDTOResultSet = new LinkedHashSet<>();
    }

    @Override
    public void generatorBattle() {

        int raceAllRoundNum = players.size() * (everyonePlayTimes) / 2;

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
                List<PlayerDTO> newPlayers = getPossiblePlayers(currentRoundPlayerPool);
                if (newPlayers.isEmpty()) {
                    throw new ServerException("找不到选手,跳出死循环");
                }

                timesPoolSubtract(newPlayers);
                List<PlayerDTO> tempBattleRepeatNameList = new ArrayList<>(newPlayers);

                List<PlayerDTO> playersCombine = getBattleCombine(newPlayers);
                BattleDTO singlesBattleDTO = new BattleDTO((int)i+1,playersCombine.get(0),null, playersCombine.get(1),null);
                SinglesBattleDTOResultSet.add(singlesBattleDTO);

                raceAllRoundNum--;
                tempBattleRepeatNameList.removeAll(playersCombine);
                timesPoolAdd(tempBattleRepeatNameList);
            }
        }

    }

    /**
     * 获取可以上场的选手
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

    /**
     * 获取对阵组合
     * @param players
     * @return
     */
    private List<PlayerDTO> getBattleCombine(List<PlayerDTO> players){
        List<PlayerDTO> result = new ArrayList<>(2);

        List<PlayerDTO> cloneList = new ArrayList<>(players);
        OUT:
        for (int i = 0; i < players.size(); i++) {
            PlayerDTO p1 = players.get(i);
            result.add(p1);
            cloneList.remove(p1);
            for (int j = 0; j < cloneList.size(); j++) {
//                Random random = new Random();
//                int randomIndex = random.nextInt(0, cloneList.size());
                PlayerDTO p2 = cloneList.get(j);
                if(p1.equals(p2)){
                    continue;
                }
                BattleDTO battleDTO = new BattleDTO(0,p1,null,p2,null);
                if(isContain(SinglesBattleDTOResultSet,battleDTO)){
                    continue ;
                }
                result.add(p2);
                break OUT;
            }
        }
        return result;
    }

    /**
     * 如果选手顺序调换下判断又不行，需重写判断是否相同
     * @param set
     * @param dto
     * @return
     */
    private boolean isContain(Set<BattleDTO> set,BattleDTO dto){
        for (BattleDTO battleDTO : set) {
            PlayerDTO player1 = battleDTO.getPartner1Player1();
            PlayerDTO player2 = battleDTO.getPartner2Player1();
            boolean e11 = player1.equals(dto.getPartner1Player1());
            boolean e22 = player1.equals(dto.getPartner2Player1());
            boolean e211 = player2.equals(dto.getPartner1Player1());
            boolean e222 = player2.equals(dto.getPartner2Player1());
            if((e11 || e22) && (e211 || e222)){
                return true;
            }

        }
        return false;
    }

    @Override
    public Set<BattleDTO> getResultBattles() {
        return SinglesBattleDTOResultSet;
    }
}
