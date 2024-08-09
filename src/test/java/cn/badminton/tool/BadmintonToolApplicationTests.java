package cn.badminton.tool;

import cn.badminton.tool.tools.racetool.*;
import cn.badminton.tool.web.dto.BattleDTO;
import cn.badminton.tool.web.dto.PartnerDTO;
import cn.badminton.tool.web.dto.PlayerDTO;
import cn.badminton.tool.tools.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.util.*;

@SpringBootTest
class BadmintonToolApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("打印测试");
    }

    @Test
    void equal() {
        PlayerDTO playerDTO = new PlayerDTO();
        PlayerDTO playerDTO2 = new PlayerDTO();

        playerDTO.setUid(100L);
        playerDTO.setOpenid("aaabbb");
        playerDTO.setAvatarUrl("http://");
//        playerDTO.setEveryonePlayTimes(7);

        playerDTO2.setUid(100L);
        playerDTO2.setOpenid("aaabbb");
        playerDTO2.setAvatarUrl("http://");
//        playerDTO2.setEveryonePlayTimes(6);

        System.out.println(playerDTO.equals(playerDTO2));
    }

    /**
     * 8人转
     */
    @Test
    void eightPartner() {
        System.out.println("8人转测试 4~13人");
        // 参赛人列表
        ArrayList<String> participations = new ArrayList<>(8);
        participations.add("小昭");
        participations.add("韦一笑");
        participations.add("无相禅师");
        participations.add("灭绝师太");
//        participations.add("朱元璋");
//        participations.add("陈友谅");
//        participations.add("宋青书");
//        participations.add("张三丰");
//        participations.add("殷野王");
//        participations.add("谢逊");
//        participations.add("何太冲");
//        participations.add("张翠山");
//        participations.add("鹿杖客");

        EightDoubleRaceGeneratorTool<String> stringEightDoubleRaceGeneratorTool
                = new EightDoubleRaceGeneratorTool<>(participations, 9, 1);
        List<List<String>> lists = stringEightDoubleRaceGeneratorTool.generatorPartner();
        for (int i = 0; i < lists.size(); i++) {
            List<String> partner = lists.get(i);
            System.out.println(String.format("第%s组: %s", i + 1, String.join("", partner.get(0), "==", partner.get(1))));
        }
    }

    @Test
    void battle() {
        System.out.println("8人转测试 4~13人");
        // 参赛人列表
        ArrayList<String> participations = new ArrayList<>(8);
        participations.add("小昭");
        participations.add("韦一笑");
        participations.add("无相禅师");
        participations.add("灭绝师太");
        participations.add("朱元璋");
        participations.add("陈友谅");
        participations.add("宋青书");
        participations.add("张三丰");
        participations.add("殷野王");
        participations.add("谢逊");
        participations.add("何太冲");
        participations.add("张翠山");
        participations.add("鹿杖客");

        participations.add("水云道人");
        participations.add("木桑道长");
        participations.add("曹化淳");

        Integer perRoundNum = 8;
        Integer venueNum = 2;

        EightDoubleRaceGeneratorTool<String> stringEightDoubleRaceGeneratorTool
                = new EightDoubleRaceGeneratorTool<>(participations, perRoundNum, venueNum);
        List<List<String>> lists = stringEightDoubleRaceGeneratorTool.generatorPartner();
        for (int i = 0; i < lists.size(); i++) {
            List<String> partner = lists.get(i);
            System.out.println(String.format("第%s组: %s", i + 1, String.join("", partner.get(0), "==", partner.get(1))));
        }

        List<List<List<String>>> generatorBattle = stringEightDoubleRaceGeneratorTool.generatorBattle(lists);
        for (int i = 0; i < generatorBattle.size(); i++) {
            List<List<String>> list = generatorBattle.get(i);

            System.out.println(String.format("第%s轮 第%s组: %s - %s VS %s - %s ", (i / venueNum) + 1, i + 1, list.get(0).get(0), list.get(0).get(1), list.get(1).get(0), list.get(1).get(1)));
        }
    }

    @Test
    void badminton2_1() {
        // 参赛人列表
        ArrayList<String> participations = new ArrayList<>(8);
        participations.add("小昭");
        participations.add("韦一笑");
        participations.add("无相禅师");
        participations.add("灭绝师太");

        participations.add("朱元璋");
        participations.add("陈友谅");
//
        participations.add("宋青书");
//
//        participations.add("张三丰");
//
//        participations.add("殷野王");
//        participations.add("谢逊");
//        participations.add("何太冲");
//        participations.add("张翠山");
//        participations.add("鹿杖客");
//
//        participations.add("水云道人");
//        participations.add("木桑道长");
//        participations.add("曹化淳");
//
//        participations.add("马钰");
//        participations.add("小龙女");
//        participations.add("尹志平");
//        participations.add("丘处机");

        Integer everyoneRoundNum = 8;
        Integer venueNum = 1;
        System.out.println(String.format("测试 %d人，每人%d场，共%d片场地", participations.size(), everyoneRoundNum, venueNum));

        BadmintonDoubleRaceGeneratorTool<String> tool = new BadmintonDoubleRaceGeneratorTool<>(participations,
                everyoneRoundNum,
                venueNum);
        List<List<List<String>>> generatorBattle = tool.generatorBattle();
//        for (int i = 0; i < generatorBattle.size(); i++) {
//            List<List<String>> list = generatorBattle.get(i);
//
//            System.out.println(String.format("第%s轮 第%s组: %s - %s VS %s - %s ",(i/venueNum)+1, i + 1, list.get(0).get(0),list.get(0).get(1),list.get(1).get(0),list.get(1).get(1)));
//        }
    }

    @Test
    void badminton2_2() {
        // 参赛人列表
        ArrayList<String> participations = new ArrayList<>(8);
        participations.add("小昭");
        participations.add("韦一笑");
        participations.add("无相禅师");
        participations.add("灭绝师太");

        participations.add("朱元璋");
        participations.add("陈友谅");

        participations.add("宋青书");

        participations.add("张三丰");

        participations.add("殷野王");
        participations.add("谢逊");
        participations.add("何太冲");
        participations.add("张翠山");
        participations.add("鹿杖客");

        participations.add("水云道人");
        participations.add("木桑道长");
        participations.add("曹化淳");

        participations.add("马钰");
        participations.add("小龙女");
        participations.add("尹志平");
        participations.add("丘处机");

        Integer everyoneRoundNum = 8;
        Integer venueNum = 2;
        System.out.println(String.format("测试 %d人，每人%d场，共%d片场地", participations.size(), everyoneRoundNum, venueNum));
        BadmintonDoubleRaceGeneratorTool2<String> tool = new BadmintonDoubleRaceGeneratorTool2<>(participations,
                everyoneRoundNum,
                venueNum);
        List<List<String>> lists = tool.partnerGenerator();
//        for (int i = 0; i < lists.size(); i++) {
//            List<String> partner = lists.get(i);
//            System.out.println(String.format("第%s组: %s", i + 1, String.join("",partner.get(0),"==", partner.get(1))));
//        }

        List<List<List<String>>> battle = tool.battleGenerator(lists);
        for (int i = 0; i < battle.size(); i++) {
            List<List<String>> list = battle.get(i);
            System.out.println(String.format("第%s轮 第%s组: %s - %s VS %s - %s ", (i / venueNum) + 1, i + 1, list.get(0).get(0), list.get(0).get(1), list.get(1).get(0), list.get(1).get(1)));
        }
    }

    @Test
    void insertSort() {
        Integer[] arr = {7, 2, 5, 9, 4, 13, 4};
        SortTool.insertionSort(arr);
        for (int i : arr) {
            System.out.println(i);
        }
    }

    @Test
    void badminton2_3() {
        // 参赛人列表
        ArrayList<String> participations = new ArrayList<>(8);
        participations.add("小昭");
        participations.add("韦一笑");
        participations.add("无相禅师");
        participations.add("灭绝师太");

        participations.add("朱元璋");
        participations.add("陈友谅");

        participations.add("宋青书");

        participations.add("张三丰");

        participations.add("殷野王");
        participations.add("谢逊");
        participations.add("何太冲");
        participations.add("张翠山");
        participations.add("鹿杖客");

        participations.add("水云道人");
        participations.add("木桑道长");
        participations.add("曹化淳");
//
//        participations.add("马钰");
//        participations.add("小龙女");
//        participations.add("尹志平");
//        participations.add("丘处机");

        Integer everyoneRoundNum = 8;
        Integer venueNum = 2;
        System.out.println(String.format("测试 %d人，每人%d场，共%d片场地", participations.size(), everyoneRoundNum, venueNum));

        BadmintonDoubleRaceGeneratorTool2<String> tool = new BadmintonDoubleRaceGeneratorTool2<>(participations,
                everyoneRoundNum,
                venueNum);
        List<List<String>> lists = tool.partnerGenerator();
        List<List<List<String>>> battleList = tool.battleGenerator(lists);
//        for (int i = 0; i < lists.size(); i++) {
//            List<String> partner = lists.get(i);
//            System.out.println(String.format("第%s组: %s", i + 1, String.join("",partner.get(0),"==", partner.get(1))));
//        }
        for (int i = 0; i < battleList.size(); i++) {
            List<List<String>> list = battleList.get(i);

            System.out.println(String.format("第%s轮 第%s组: %s - %s VS %s - %s ", (i / venueNum) + 1, i + 1, list.get(0).get(0), list.get(0).get(1), list.get(1).get(0), list.get(1).get(1)));
        }
    }

    @Test
    void combine() {
        List<String> list = new ArrayList<>(8);
        list.add("小昭");
        list.add("韦一笑");
        list.add("无相禅师");
        list.add("灭绝师太");
        list.add("朱元璋");
        list.add("陈友谅");
        list.add("宋青书");
        list.add("张三丰");
        List<List<String>> combinations = SortTool.fourPlayerToCombine(list);

        for (List<String> combination : combinations) {
            System.out.println(combination);
        }
    }

    @Test
    void badminton3_1() {
        // 参赛人列表
        ArrayList<String> participations = new ArrayList<>(8);
        participations.add("小昭");
        participations.add("韦一笑");
        participations.add("无相禅师");
        participations.add("灭绝师太");

        participations.add("朱元璋");
        participations.add("陈友谅");

        participations.add("宋青书");

        participations.add("张三丰");

        participations.add("殷野王");
        participations.add("谢逊");
        participations.add("何太冲");
        participations.add("张翠山");
        participations.add("鹿杖客");

        participations.add("水云道人");
        participations.add("木桑道长");
        participations.add("曹化淳");

        participations.add("马钰");
        participations.add("小龙女");
        participations.add("尹志平");
        participations.add("丘处机");

        Integer everyoneRoundNum = 8;
        Integer venueNum = 3;
        System.out.println(String.format("测试 %d人，每人%d场，共%d片场地", participations.size(), everyoneRoundNum, venueNum));

        BadmintonDoubleRaceGeneratorTool3<String> tool = new BadmintonDoubleRaceGeneratorTool3<>(participations,
                everyoneRoundNum,
                venueNum);
        List<List<List<String>>> generatorBattle = tool.generatorBattle();
//        for (int i = 0; i < generatorBattle.size(); i++) {
//            List<List<String>> list = generatorBattle.get(i);
//
//            System.out.println(String.format("第%s轮 第%s组: %s - %s VS %s - %s ",(i/venueNum)+1, i + 1, list.get(0).get(0),list.get(0).get(1),list.get(1).get(0),list.get(1).get(1)));
//        }
    }

    @Test
    void doubleTest() {
        double ceil = Math.ceil((double) 40 / 3);
        System.out.println(ceil);
    }

    @Test
    void badminton4_1() {
        // 参赛人列表
        ArrayList<String> participations = new ArrayList<>(8);
        participations.add("小昭");
        participations.add("韦一笑");
        participations.add("无相禅师");
        participations.add("灭绝师太");

        participations.add("朱元璋");
        participations.add("陈友谅");

//        participations.add("宋青书");
//
//        participations.add("张三丰");
//
//        participations.add("殷野王");
//        participations.add("谢逊");
//        participations.add("何太冲");
//        participations.add("张翠山");
//        participations.add("鹿杖客");
//
//        participations.add("水云道人");
//        participations.add("木桑道长");
//        participations.add("曹化淳");

//        participations.add("马钰");
//        participations.add("小龙女");
//        participations.add("尹志平");
//        participations.add("丘处机");

        Integer everyoneRoundNum = 4;
        Integer venueNum = 2;
        System.out.println(String.format("测试 %d人，每人%d场，共%d片场地", participations.size(), everyoneRoundNum, venueNum));
        BadmintonDoubleRaceGeneratorTool4<String> tool =
                new BadmintonDoubleRaceGeneratorTool4(participations, everyoneRoundNum, venueNum);
        List<List<String>> lists = tool.generatorPartner();
//        for (int i = 0; i < lists.size(); i++) {
//            List<String> partner = lists.get(i);
//            System.out.println(String.format("第%s组: %s", i + 1, String.join("",partner.get(0),"&", partner.get(1))));
//        }
        List<List<List<String>>> allPossibleBattle = tool.generatorAllPossibleBattle(lists);
        for (int i = 0; i < allPossibleBattle.size(); i++) {
            List<List<String>> list = allPossibleBattle.get(i);
            System.out.println(String.format("第%s轮 第%s组: %s - %s VS %s - %s ", (i / venueNum) + 1, i + 1, list.get(0).get(0), list.get(0).get(1), list.get(1).get(0), list.get(1).get(1)));
        }
    }

    @Test
    void test11() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        ArrayList<String> sss = new ArrayList<>(strings);
        strings.remove("1");
        System.out.println(sss);
    }

//    @Test
//    void badminton5_1() {
//        Integer playerNum = 20;
//        Integer everyonePlayTimes = 8;
//        Integer venueNum = 3;
//        String[] names = {
//                "小昭",
//                "韦一笑",
//                "无相禅师",
//                "灭绝师太",
//                "朱元璋",
//                "陈友谅",
//                "宋青书",
//                "张三丰",
//                "殷野王",
//                "谢逊",
//                "何太冲",
//                "张翠山",
//                "鹿杖客",
//                "水云道人",
//                "木桑道长",
//                "曹化淳",
//                "马钰",
//                "小龙女",
//                "尹志平",
//                "丘处机",
//        };
//        // 参赛人列表
//        List<PlayerDTO> playList = new ArrayList<>();
//        for (Integer i = 1; i <= playerNum; i++) {
//            PlayerDTO playerDTO1 = new PlayerDTO();
//            playerDTO1.setNickName(names[i - 1]);
//            playList.add(playerDTO1);
//        }
//
//        System.out.println(
//                String.format("测试 %d人，每人%d场，共%d片场地",
//                        playList.size(),
//                        everyonePlayTimes,
//                        venueNum)
//        );
//        BadmintonDoubleRaceGeneratorTool5 tool5 =
//                new BadmintonDoubleRaceGeneratorTool5(playList, everyonePlayTimes, venueNum);
//        List<PartnerDTO> partnerDTOS = tool5.generatorPartner();
////        System.out.println("测试搭档生成列表");
////        for (int i = 0; i < partnerDTOS.size(); i++) {
////            PartnerDTO partnerDTO = partnerDTOS.get(i);
////            System.out.println(
////                    String.format("第%d组：%s & %s",
////                            i+1,
////                            partnerDTO.getP1().getNickname(),
////                            partnerDTO.getP2().getNickname())
////            );
////        }
//
//        List<BattleDTO> allPossibleBattle = tool5.generatorAllPossibleBattle(partnerDTOS);
////        System.out.println("测试所有对阵可能性数据列表");
////        for (int i = 0; i < allPossibleBattle.size(); i++) {
////            BattleDTO battleDTO = allPossibleBattle.get(i);
////            boolean contain = tool5.isContain(battleDTO.getPartnerDTO1(), battleDTO.getPartnerDTO2());
////            String format = String.format("第%d场：%s & %s VS %s & %s -- 4人相同：%b",
////                    i + 1,
////                    battleDTO.getPartnerDTO1().getP1().getNickname(),
////                    battleDTO.getPartnerDTO1().getP2().getNickname(),
////                    battleDTO.getPartnerDTO2().getP1().getNickname(),
////                    battleDTO.getPartnerDTO2().getP2().getNickname(),
////                    contain
////                    );
////            System.out.println(format);
////        }
//        List<BattleDTO> doublesBattleDTOS = tool5.startBattle(allPossibleBattle);
//        System.out.println("测试最终对阵");
////        for (int i = 0; i < battleDTOS.size(); i++) {
////            BattleDTO battleDTO = battleDTOS.get(i);
////            String format = String.format("第%d场：%s & %s VS %s & %s",
////                    i + 1,
////                    battleDTO.getPartnerDTO1().getP1().getNickname(),
////                    battleDTO.getPartnerDTO1().getP2().getNickname(),
////                    battleDTO.getPartnerDTO2().getP1().getNickname(),
////                    battleDTO.getPartnerDTO2().getP2().getNickname()
////                    );
////            System.out.println(format);
////        }
//    }

//    @Test
//    void badminton6_1() {
//        Integer playerNum = 14;
//        Integer everyonePlayTimes = 8;
//        Integer venueNum = 2;
//        String[] names = {
//                "小昭",
//                "韦一笑",
//                "无相禅师",
//                "灭绝师太",
//                "朱元璋",
//                "陈友谅",
//                "宋青书",
//                "张三丰",
//                "殷野王",
//                "谢逊",
//                "何太冲",
//                "张翠山",
//                "鹿杖客",
//                "水云道人",
//                "木桑道长",
//                "曹化淳",
//                "马钰",
//                "小龙女",
//                "尹志平",
//                "丘处机",
//        };
//        // 参赛人列表
//        List<PlayerDTO> playList = new ArrayList<>();
//        for (Integer i = 1; i <= playerNum; i++) {
//            PlayerDTO playerDTO1 = new PlayerDTO();
//            playerDTO1.setNickName(names[i - 1]);
//            playList.add(playerDTO1);
//        }
//
//        System.out.println(
//                String.format("测试 %d人，每人%d场，共%d片场地",
//                        playList.size(),
//                        everyonePlayTimes,
//                        venueNum)
//        );
//        BadmintonDoubleRaceGeneratorTool6 tool6 =
//                new BadmintonDoubleRaceGeneratorTool6(playList, everyonePlayTimes, venueNum);
//        Set<PartnerDTO> partnerDTOS = tool6.generatorPartner();
//        Set<BattleDTO> doublesBattleDTOS = tool6.generatorBattle(partnerDTOS);
//        System.out.println("测试最终对阵");
//        for (BattleDTO doublesBattleDTO : doublesBattleDTOS) {
//            String format = String.format("对阵：%s & %s VS %s & %s",
//                    doublesBattleDTO.getPartner1().getP1().getNickName(),
//                    doublesBattleDTO.getPartner1().getP2().getNickName(),
//                    doublesBattleDTO.getPartner2().getP1().getNickName(),
//                    doublesBattleDTO.getPartner2().getP2().getNickName()
//            );
//            System.out.println(format);
//        }
//    }

//    @Test
//    void badminton7_1() {
//        Integer playerNum = 14;
//        Integer everyonePlayTimes = 8;
//        Integer venueNum = 2;
//        String[] names = {
//                "小昭",
//                "韦一笑",
//                "无相禅师",
//                "灭绝师太",
//                "朱元璋",
//                "陈友谅",
//                "宋青书",
//                "张三丰",
//                "殷野王",
//                "谢逊",
//                "何太冲",
//                "张翠山",
//                "鹿杖客",
//                "水云道人",
//                "木桑道长",
//                "曹化淳",
//                "马钰",
//                "小龙女",
//                "尹志平",
//                "丘处机",
//        };
//        // 参赛人列表
//        List<PlayerDTO> playList = new ArrayList<>();
//        for (Integer i = 1; i <= playerNum; i++) {
//            PlayerDTO playerDTO1 = new PlayerDTO();
//            playerDTO1.setNickName(names[i - 1]);
//            playList.add(playerDTO1);
//        }
//
//        System.out.println(
//                String.format("测试 %d人，每人%d场，共%d片场地",
//                        playList.size(),
//                        everyonePlayTimes,
//                        venueNum)
//        );
//
//        Set<BattleDTO> doublesBattleDTOS = new HashSet<>();
//        int retry = 0;
//        while (doublesBattleDTOS.isEmpty()) {
//            try {
//                if (retry >= 10) {
//                    break;
//                }
//                BadmintonDoubleRaceGeneratorTool7 tool7 =
//                        new BadmintonDoubleRaceGeneratorTool7(playList, everyonePlayTimes, venueNum);
//                doublesBattleDTOS = tool7.generatorBattle();
//                retry++;
//                System.out.println(String.format("第%d次成功", retry));
//            } catch (Exception e) {
//                retry++;
//                e.printStackTrace();
//
//
//            }
//        }
//
//
//        System.out.println("测试最终对阵");
//        for (BattleDTO doublesBattleDTO : doublesBattleDTOS) {
//            String format = String.format("第%d轮 对阵：%s & %s VS %s & %s",
//                    doublesBattleDTO.getRoundNum(),
//                    doublesBattleDTO.getPartner1().getP1().getNickName(),
//                    doublesBattleDTO.getPartner1().getP2().getNickName(),
//                    doublesBattleDTO.getPartner2().getP1().getNickName(),
//                    doublesBattleDTO.getPartner2().getP2().getNickName()
//            );
//            System.out.println(format);
//        }
//
//    }

    @Test
    void badminton8_1() {
        Integer playerNum = 10;
        Integer everyonePlayTimes = 8;
        Integer venueNum = 1;
        String[] names = {
                "小昭",
                "韦一笑",
                "无相禅师",
                "灭绝师太",
                "朱元璋",
                "陈友谅",
                "宋青书",
                "张三丰",
                "殷野王",
                "谢逊",
                "何太冲",
                "张翠山",
                "鹿杖客",
                "水云道人",
                "木桑道长",
                "曹化淳",
                "马钰",
                "小龙女",
                "尹志平",
                "丘处机",
        };
        // 参赛人列表
        List<PlayerDTO> playList = new ArrayList<>();
        for (Integer i = 1; i <= playerNum; i++) {
            PlayerDTO playerDTO1 = new PlayerDTO();
            playerDTO1.setNickName(names[i - 1]);
            playList.add(playerDTO1);
        }

        System.out.println(
                String.format("测试 %d人，每人%d场，共%d片场地",
                        playList.size(),
                        everyonePlayTimes,
                        venueNum)
        );

        Set<BattleDTO> doublesBattleDTOS = new HashSet<>();
        int retry = 0;
        while (doublesBattleDTOS.isEmpty()) {
            try {
                if (retry >= 10) {
                    break;
                }
                BadmintonDoubleRaceGeneratorTool8 tool8 =
                        new BadmintonDoubleRaceGeneratorTool8(playList, everyonePlayTimes, venueNum);
                tool8.generatorBattle();
                doublesBattleDTOS = tool8.getResultBattles();
                retry++;
                System.out.println(String.format("第%d次成功", retry));
            } catch (Exception e) {
                retry++;
                e.printStackTrace();
            }
        }


        System.out.println("测试最终对阵");
        for (BattleDTO doublesBattleDTO : doublesBattleDTOS) {
            String format = String.format("第%d轮 对阵：%s & %s VS %s & %s",
                    doublesBattleDTO.getRoundNum(),
                    doublesBattleDTO.getPartner1().getP1().getNickName(),
                    doublesBattleDTO.getPartner1().getP2().getNickName(),
                    doublesBattleDTO.getPartner2().getP1().getNickName(),
                    doublesBattleDTO.getPartner2().getP2().getNickName()
            );
            System.out.println(format);
        }

    }

    @Test
    void badminton8_2() {
        StopWatch stopWatch = new StopWatch();
        // 开始时间
        stopWatch.start();

        Integer playerNum = 14;
        Integer everyonePlayTimes = 8;
        Integer venueNum = 2;
        String[] names = {
                "港",
                "李宗伟",
                "168",
                "巅峰科比",
                "常有理",
                "啊晨",
                "愿安好",
                "梁",
                "大漠孤狼",
                "Big科比",
                "高",
                "什么鸭鸭",
                "许许许",
                "现",
        };
        // 参赛人列表
        List<PlayerDTO> playList = new ArrayList<>();
        for (Integer i = 1; i <= playerNum; i++) {
            PlayerDTO playerDTO1 = new PlayerDTO();
            playerDTO1.setNickName(names[i - 1]);
            playList.add(playerDTO1);
        }

        System.out.println(
                String.format("测试 %d人，每人%d场，共%d片场地 %n",
                        playList.size(),
                        everyonePlayTimes,
                        venueNum)
        );

        Set<BattleDTO> doublesBattleDTOS = new HashSet<>();
        int retry = 0;
        while (doublesBattleDTOS.isEmpty()) {
            try {
                if (retry >= 100) {
                    break;
                }
                BadmintonDoubleRaceGeneratorTool8 tool8 =
                        new BadmintonDoubleRaceGeneratorTool8(playList, everyonePlayTimes, venueNum);
                tool8.generatorBattle();
                doublesBattleDTOS = tool8.getResultBattles();
                retry++;
                System.out.printf("第%d次成功%n", retry);
            } catch (Exception e) {
                retry++;
                System.out.printf("第%d次重试%n", retry);
                e.printStackTrace();
            }
        }
        // 结束时间
        stopWatch.stop();
        // 统计执行时间（秒）
        System.out.printf("执行时长：%s 秒.%n", stopWatch.getTotalTimeSeconds()); // %n 为换行
        // 统计执行时间（毫秒）
        System.out.printf("执行时长：%d 毫秒.%n", stopWatch.getTotalTimeMillis());
        // 统计执行时间（纳秒）
//        System.out.printf("执行时长：%d 纳秒.%n", stopWatch.getTotalTimeNanos());

        System.out.printf("测试最终对阵%n");
//        for (BattleDTO battleDTO : battleDTOS) {
//            String format = String.format("第%d轮 对阵：%s & %s VS %s & %s",
//                    battleDTO.getRoundNum(),
//                    battleDTO.getPartnerDTO1().getP1().getNickname(),
//                    battleDTO.getPartnerDTO1().getP2().getNickname(),
//                    battleDTO.getPartnerDTO2().getP1().getNickname(),
//                    battleDTO.getPartnerDTO2().getP2().getNickname()
//            );
//            System.out.println(format);
//        }

    }

    @Test
    void singleTest(){
        StopWatch stopWatch = new StopWatch();
        // 开始时间
        stopWatch.start();

        Integer playerNum = 6;
        Integer everyonePlayTimes = 5;
        Integer venueNum = 1;
        String[] names = {
                "港",
                "李宗伟",
                "168",
                "巅峰科比",
                "常有理",
                "啊晨",
        };
        // 参赛人列表
        List<PlayerDTO> playList = new ArrayList<>();
        for (Integer i = 1; i <= playerNum; i++) {
            PlayerDTO playerDTO1 = new PlayerDTO();
            playerDTO1.setNickName(names[i - 1]);
            playList.add(playerDTO1);
        }

        System.out.println(
                String.format("测试 %d人，每人%d场，共%d片场地 %n",
                        playList.size(),
                        everyonePlayTimes,
                        venueNum)
        );

        Set<BattleDTO> doublesBattleDTOS = new HashSet<>();
        int retry = 0;
        while (doublesBattleDTOS.isEmpty()) {
            try {
                if (retry >= 100) {
                    break;
                }
                BadmintonSingleRaceGeneratorTool tool =
                        new BadmintonSingleRaceGeneratorTool(playList, everyonePlayTimes, venueNum);
                tool.generatorBattle();
                doublesBattleDTOS = tool.getResultBattles();
                retry++;
                System.out.printf("第%d次成功%n", retry);
            } catch (Exception e) {
                retry++;
                System.out.printf("第%d次重试%n", retry);
                e.printStackTrace();
            }
        }
        // 结束时间
        stopWatch.stop();
        // 统计执行时间（秒）
        System.out.printf("执行时长：%s 秒.%n", stopWatch.getTotalTimeSeconds()); // %n 为换行
        // 统计执行时间（毫秒）
        System.out.printf("执行时长：%d 毫秒.%n", stopWatch.getTotalTimeMillis());

//        System.out.printf("测试最终对阵%n");
    }


}
