package cn.badminton.tool.support.common;

public enum RaceMainTypeEnum {

    DOUBLE(1,"双打"),SINGLE(2,"单打"),ANTAGONISM(3,"对抗"),TEAM(4,"团队");

    private Integer type;
    private String info;

    private RaceMainTypeEnum(Integer type, String info){
        this.type=type;
        this.info=info;
    }

    public Integer getType(){
        return type;
    }

    public String getInfo(){
        return info;
    }
}
