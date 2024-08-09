package cn.badminton.tool.tools.racetool;

import cn.badminton.tool.web.dto.PlayerDTO;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractRaceGenerateTool implements RaceGenerateTool {

    /**
     * 参赛人列表
     */
    protected List<PlayerDTO> players;

    /**
     * 每人上场次数
     * 用于创建一个池，每个人总共 playerEveryonePlayTimes 次，每分配一次 -1
     */
    protected Integer everyonePlayTimes;

    /**
     * 场地数
     */
    protected Integer venueNum;

    /**
     * 选手出场次数池
     */
    protected Map<PlayerDTO, Integer> playerTimesPool;

    public AbstractRaceGenerateTool(List<PlayerDTO> players,
                                    Integer everyonePlayTimes,
                                    Integer venueNum) {
        this.players = players;
        this.everyonePlayTimes = everyonePlayTimes;
        this.venueNum = venueNum;

        this.playerTimesPool = new LinkedHashMap<>();
        for (PlayerDTO p : players) {
            playerTimesPool.put(p, everyonePlayTimes);
        }
    }

    protected void timesPoolSubtract(List<PlayerDTO> ps) {
        for (PlayerDTO p : ps) {
            Integer times = playerTimesPool.get(p);
            times--;
            playerTimesPool.put(p, times);
        }
    }

    protected void timesPoolAdd(List<PlayerDTO> ps) {
        for (PlayerDTO p : ps) {
            Integer times = playerTimesPool.get(p);
            times++;
            if (times >= everyonePlayTimes) {
                times = everyonePlayTimes;
            }
            playerTimesPool.put(p, times);
        }
    }
}
