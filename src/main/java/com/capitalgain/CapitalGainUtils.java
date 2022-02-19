package com.capitalgain;

import com.capitalgain.model.CapitalGain;
import com.capitalgain.model.Context;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CapitalGainUtils {

    public final Double TAX_PERCENT = 0.2;
    public final Integer TAX_LIMIT = 20000;

    public boolean isLoss(Integer averageValue, Integer currentValue){
        return currentValue < averageValue ? true : false;
    }

    public boolean isFreeOfTax(Integer unitcost, Integer quantity){
        return unitcost * quantity < TAX_LIMIT ? true : false;
    }

    public Integer getCurrentLossValue(Context context, Integer quantity, Integer unitcost) {
        var baseValue = context.getAverageValue() * quantity;
        var currentValue = (quantity * unitcost);
        return baseValue < currentValue ? 0 : baseValue - currentValue;
    }

    public Integer getCurrentProfitValue(Context context, Integer quantity, Integer unitcost) {
        var baseValue = context.getAverageValue() * quantity;
        var currentValue = (quantity * unitcost);
        return baseValue > currentValue ? 0 : currentValue - baseValue;
    }

    public Integer getCurrentProfitWithContextBalance(Integer balance, Integer currentProfitValue) {
        var currentProfit = 0;
        if(balance > 0){
            currentProfit = balance < currentProfitValue ? currentProfitValue : currentProfitValue - balance;
        } else {
            currentProfit = Math.abs(balance) > currentProfitValue ? 0 : currentProfitValue - Math.abs(balance);
        }
        return currentProfit;
    }
}
