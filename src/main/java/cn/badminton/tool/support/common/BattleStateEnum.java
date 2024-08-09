package cn.badminton.tool.support.common;

/**
 * 比赛对阵状态
 */
public enum BattleStateEnum {

    notStart(0),racing(1),finish(2);

    private Integer value;

    private BattleStateEnum(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }

    public static BattleStateEnum valueOf(int value) {    //手写的从int到enum的转换函数
        return switch (value) {
            case 1 -> racing;
            case 2 -> finish;
            default -> notStart;
        };
    }
}
