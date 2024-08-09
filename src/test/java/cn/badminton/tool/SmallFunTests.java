package cn.badminton.tool;

import cn.badminton.tool.tools.racetool.RaceStartTool;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class SmallFunTests {

    @Test
    public void doubleSession() {
        Set<Integer> maps = new LinkedHashSet<>();
        int p = 10;

        // 全搭模式 A-1，（如果除不尽，选固定8人模式）（人数不足10人，默认全搭）（全搭没有随机方式）
        // 固定搭8人模式

        Integer session1 = RaceStartTool.doublePlayerSession(p, false);
        Integer session2 = RaceStartTool.doublePlayerSession(p, true);

        maps.add(session1);
        maps.add(session2);
        for (Integer session : maps) {
            System.out.printf("人数%d人，每场人数%d场，总共%d场 \n", p,session, p * session / 4);
        }
    }
}
