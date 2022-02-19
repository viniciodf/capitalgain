package com.capitalgain.strategy.impl;

import com.capitalgain.model.CapitalGain;
import com.capitalgain.model.Context;
import com.capitalgain.model.Operation;
import com.capitalgain.strategy.CapitalGainStrategy;
import org.springframework.stereotype.Component;

@Component
public class BuyChain implements CapitalGainStrategy {

    @Override
    public CapitalGain executeCapitalGain(Context context, CapitalGain currentCapitalGain) {
        currentCapitalGain.setTax(0);
        return currentCapitalGain;
    }

    @Override
    public Operation getOperation() {
        return Operation.BUY;
    }
}
