package cn.badminton.tool.support.common;

public enum GenderEnum {

    NONE(0),MALE(1),FEMALE(2);

    private Integer value;

    private GenderEnum(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }

    public static GenderEnum valueOf(int value) {    //手写的从int到enum的转换函数
        return switch (value) {
            case 1 -> MALE;
            case 2 -> FEMALE;
            default -> NONE;
        };
    }

    public static GenderEnum valueOfString(String value) {    //手写的从int到enum的转换函数
        return switch (value) {
            case "男" -> MALE;
            case "女" -> FEMALE;
            default -> NONE;
        };
    }
}
