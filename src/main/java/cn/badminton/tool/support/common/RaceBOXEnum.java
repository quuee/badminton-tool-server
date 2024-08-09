package cn.badminton.tool.support.common;

/**
 * BOx就表示在x局中决出胜者
 * 一局决胜负、三局两胜、五局三胜
 */
public enum RaceBOXEnum {

    BO1(1),BO3(3),BO5(5);

    private Integer value;

    private RaceBOXEnum(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }

    public static RaceBOXEnum valueOf(int value) {    //手写的从int到enum的转换函数
        return switch (value) {
            case 3 -> BO3;
            case 5 -> BO5;
            default -> BO1;
        };
    }
    public static RaceBOXEnum valueOfString(String value) {    //手写的从int到enum的转换函数
        return switch (value) {
            case "三局两胜" -> BO3;
            case "五局三胜" -> BO5;
            default -> BO1;
        };
    }
}
