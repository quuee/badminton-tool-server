package cn.badminton.tool.web.vo;

import lombok.Data;

import java.text.DecimalFormat;

@Data
public class BattleStatisticsVO {

    private Integer totalWins = 0;

    private Integer totalLosses = 0;

    public String getWinPercentage() {
        if(totalWins == 0){
            return "0";
        }
        if((totalWins + totalLosses) == 0){
            return "0";
        }
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        double result = (double) totalWins / (totalWins + totalLosses);
        return decimalFormat.format(result);
    }

    public String getLossPercentage() {
        if(totalWins == 0){
            return "0";
        }
        if((totalWins + totalLosses) == 0){
            return "0";
        }
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        double result = (double) totalLosses / (totalWins + totalLosses);
        return decimalFormat.format(result);
    }

}
