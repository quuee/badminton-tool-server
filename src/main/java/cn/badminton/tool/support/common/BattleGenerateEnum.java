package cn.badminton.tool.support.common;

/**
 * 对阵生成算法 随机、势均力敌、报名顺序
 * 不出现男男VS女女
 */
public enum BattleGenerateEnum {

    random(1),balance(2),sequence(3);


    private Integer value;

    private BattleGenerateEnum(Integer value){
        this.value = value;
    }

    public Integer getValue(){
        return value;
    }

    public static BattleGenerateEnum valueOf(int value) {    //手写的从int到enum的转换函数
        return switch (value) {
            case 2 -> balance;
            case 3 -> sequence;
            default -> random;
        };
    }

}
