package cn.badminton.tool.tools.racetool;

public class RaceStartTool {

    /**
     * 双打 计算N人，适合的场数
     * 适合八人转，超八转
     * 全搭（少于10人时默认全搭）、超过10时可选固定搭8人，或全搭
     * @param pnum
     * @param s
     * @return
     */
    public static Integer calcSession(Integer pnum, Integer s){
        Integer session;
        double last = (pnum * s)%4;
        if(last  == 0){

            session = s;
        }else {
            Integer sss = calcSession(pnum,s+1);
            session = sss;
        }
        return session;
    }

    public static Integer doublePlayerSession(Integer playerNum, boolean all ){

        if(playerNum < 9){
            return calcSession(playerNum,playerNum -1);
        }else{
            if(all){
                double ls = (playerNum*(playerNum-1))%4;
                if(ls == 0){
                   return calcSession(playerNum,playerNum-1);
                }
            }
            return calcSession(playerNum,8);
        }
    }

    // 混双转

    /**
     * 根据人数 计算场地数
     * @param players
     * @param playingNum 上场人数： 单打4人， 双打8人
     * @return
     */
    public static Integer calcFieldNum(Integer players,Integer playingNum){
        double num = (double) players / playingNum;
        double filedNum = Math.ceil(num);
        return (int)filedNum;
    }

}
