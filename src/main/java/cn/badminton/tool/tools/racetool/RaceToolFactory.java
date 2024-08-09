package cn.badminton.tool.tools.racetool;

import cn.badminton.tool.web.dto.PlayerDTO;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class RaceToolFactory {

    /**
     * 根据条件，匹配出符合条件的对阵算法类
     * 1、确定 单打、双打、团体、对抗
     * 2、确定 （单打：自由转、单打转、单循环、双循环、擂台、晋级） 大类型下的某个具体赛制名称
     * 3、确定 生成对阵算法：报名顺序、随机大乱斗、势均力敌
     *
     * 4、其他方式生成对阵：指定组合、抽签
     * @param raceMainType 大类型:单打、双打、团体、对抗
     * @param raceSchemeClassName 具体比赛玩法方案
     * @param battleGenerate 对阵生成方式
     * @param playerDTOS 选手
     * @param everyPlayerTime 每位选手上场次数
     * @param venueNum 场地数
     * @return
     */
    public static RaceGenerateTool getRaceGenerateTool(String raceSchemeClassName, Integer battleGenerate,
                                                       List<PlayerDTO> playerDTOS,Integer everyPlayerTime,Integer venueNum)
            throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        RaceGenerateTool generateTool = null;
//        if(raceFormatType.compareTo(RaceFormatTypeEnum.SINGLE.getType()) == 0){
//
//        }
//        if(raceFormatType.compareTo(RaceFormatTypeEnum.DOUBLE.getType()) == 0){
//
//        }
//        if(raceFormatType.compareTo(RaceFormatTypeEnum.ANTAGONISM.getType()) == 0){
//
//        }
//        if(raceFormatType.compareTo(RaceFormatTypeEnum.TEAM.getType()) == 0){
//
//        }

        Class<?> aClass = Class.forName("cn.badminton.tool.tools.racetool."+raceSchemeClassName);
        Constructor<?> constructor = aClass.getConstructor(List.class, Integer.class, Integer.class);
        generateTool = (RaceGenerateTool)constructor.newInstance(playerDTOS, everyPlayerTime, venueNum);
        return generateTool;
    }
}
