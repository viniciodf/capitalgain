package com.capitalgain.service.impl;

import com.capitalgain.factory.CapitalGainFactory;
import com.capitalgain.model.CapitalGain;
import com.capitalgain.model.Context;
import com.capitalgain.model.Operation;
import com.capitalgain.model.Tax;
import com.capitalgain.service.CapitalGainService;
import com.capitalgain.strategy.CapitalGainStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CapitalGainServiceImpl implements CapitalGainService {

    private final CapitalGainFactory capitalGainFactory;

    @Override
    public List<Tax> calculateTax(List<CapitalGain> capitalGain) {
        return processTaxes(capitalGain);
    }

    private List<Tax> processTaxes(List<CapitalGain> capitalGains) {
        List<Tax> taxes = new LinkedList<>();
        Context context = loadContext(capitalGains);
        for (CapitalGain capitalGain : capitalGains) {
            CapitalGain capitalGainProcessed = executeCapitalGain(context, capitalGain);
            var tax = Tax.builder().tax(capitalGain.getTax()).build();
            taxes.add(tax);
            context.setLastCapitalGain(capitalGainProcessed);
        }
        return taxes;
    }

    private List<CapitalGain> getOnlyBuyOperations(List<CapitalGain> capitalGains) {
        return capitalGains.stream().filter(capitalGain -> Operation.BUY.equals(Operation.valueOf(capitalGain.getOperation().toUpperCase()))).collect(Collectors.toList());
    }

    private CapitalGain executeCapitalGain(Context context, CapitalGain currentCapitalGain) {
        CapitalGainStrategy strategy = capitalGainFactory.findStrategy(Operation.valueOf(currentCapitalGain.getOperation().toUpperCase()));
        return strategy.executeCapitalGain(context, currentCapitalGain);
    }

    private Context loadContext(List<CapitalGain> capitalGainsBuy) {
        var onlyBuyOperations = getOnlyBuyOperations(capitalGainsBuy);
        var totalQuantity = getTotalQuantity(onlyBuyOperations);
        var averageValue = getAverageValue(onlyBuyOperations, totalQuantity);
        return Context.builder().averageValue(averageValue).build();
    }

    private Integer getAverageValue(List<CapitalGain> capitalGainsBuy, Integer totalQuantity) {
        return capitalGainsBuy.stream().reduce(0, (partialSum, b) -> partialSum + (b.getQuantity() * b.getUnitcost()), Integer::sum) / totalQuantity;
    }

    private Integer getTotalQuantity(List<CapitalGain> capitalGainsBuy) {
        return capitalGainsBuy.stream().reduce(0, (partialTotal, item) -> partialTotal + item.getQuantity(), Integer::sum);
    }
}
