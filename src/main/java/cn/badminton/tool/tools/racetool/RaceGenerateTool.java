package cn.badminton.tool.tools.racetool;


import cn.badminton.tool.web.dto.BattleDTO;

import java.util.Set;

public interface RaceGenerateTool {

    void generatorBattle();

    Set<BattleDTO> getResultBattles();
}
