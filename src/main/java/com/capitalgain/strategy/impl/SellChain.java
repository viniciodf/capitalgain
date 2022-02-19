package com.capitalgain.strategy.impl;

import com.capitalgain.CapitalGainUtils;
import com.capitalgain.model.CapitalGain;
import com.capitalgain.model.Context;
import com.capitalgain.model.Operation;
import com.capitalgain.strategy.CapitalGainStrategy;
import org.springframework.stereotype.Component;

@Component
public class SellChain implements CapitalGainStrategy {

    @Override
    public CapitalGain executeCapitalGain(Context context, CapitalGain currentCapitalGain) {
        var isLoss = CapitalGainUtils.isLoss(context.getAverageValue(), currentCapitalGain.getUnitcost());
        var ifFreeOfTax = CapitalGainUtils.isFreeOfTax(currentCapitalGain.getUnitcost(), currentCapitalGain.getQuantity());
        var currentLossValue = CapitalGainUtils.getCurrentLossValue(context, currentCapitalGain.getQuantity(), currentCapitalGain.getUnitcost());
        var currentProfitValue = CapitalGainUtils.getCurrentProfitValue(context, currentCapitalGain.getQuantity(), currentCapitalGain.getUnitcost());

        if(!isLoss && !ifFreeOfTax){
            var currentProfit = CapitalGainUtils.getCurrentProfitWithContextBalance(context.getBalance(), currentProfitValue);
            var tax = calculateTax(currentProfit);
            currentCapitalGain.setTax(tax);
        }
        if(isLoss){
            context.setBalance(context.getBalance() - currentLossValue);
        } else {
            context.setBalance(context.getBalance() + currentProfitValue);
        }
        return currentCapitalGain;
    }

    private Integer calculateTax(Integer value){
        return Double.valueOf(value * CapitalGainUtils.TAX_PERCENT).intValue();
    }

    @Override
    public Operation getOperation() {
        return Operation.SELL;
    }
}
