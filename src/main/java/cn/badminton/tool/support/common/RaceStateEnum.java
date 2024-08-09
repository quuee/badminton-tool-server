package cn.badminton.tool.support.common;

/**
 * 比赛状态
 * 1报名中、2报名暂停、3比赛中、4结束
 */
public enum RaceStateEnum {

    applyState(1),applyPause(2),racing(3),finish(4);

    private Integer value;

    private RaceStateEnum(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }

    public static RaceStateEnum valueOf(int value) {    //手写的从int到enum的转换函数
        return switch (value) {
            case 2 -> applyPause;
            case 3 -> racing;
            case 4 -> finish;
            default -> applyState;
        };
    }
}
