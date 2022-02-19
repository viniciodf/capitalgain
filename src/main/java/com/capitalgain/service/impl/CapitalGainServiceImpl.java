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
    public List<Tax> calculateTax(List<CapitalGain> capitalGains) {
        Context context = loadContext(capitalGains);
        return processTaxes(capitalGains, context);
    }

    private List<Tax> processTaxes(List<CapitalGain> capitalGains, Context context) {
        return capitalGains.stream().map(capitalGain -> processTax(context, capitalGain)).collect(Collectors.toCollection(LinkedList::new));
    }

    private Tax processTax(Context context, CapitalGain capitalGain) {
        CapitalGain capitalGainProcessed = executeCapitalGain(context, capitalGain);
        context.setLastCapitalGain(capitalGainProcessed);
        return Tax.builder().tax(capitalGain.getTax()).build();
    }

    private List<CapitalGain> getOnlyBuyOperations(List<CapitalGain> capitalGains) {
        return capitalGains.stream().filter(capitalGain -> Operation.BUY.equals(Operation.valueOf(capitalGain.getOperation().toUpperCase()))).collect(Collectors.toList());
    }

    private CapitalGain executeCapitalGain(Context context, CapitalGain currentCapitalGain) {
        CapitalGainStrategy strategy = capitalGainFactory.findStrategy(Operation.valueOf(currentCapitalGain.getOperation().toUpperCase()));
        return strategy.executeCapitalGain(context, currentCapitalGain);
    }

    private Context loadContext(List<CapitalGain> capitalGainsBuy) {
        var averageValue = getAverageValue(capitalGainsBuy);
        return Context.builder().averageValue(averageValue).build();
    }

    private Integer getAverageValue(List<CapitalGain> capitalGainsBuy) {
        var onlyBuyOperations = getOnlyBuyOperations(capitalGainsBuy);
        var totalQuantity = getTotalQuantity(onlyBuyOperations);
        return onlyBuyOperations.stream().reduce(0, (partialSum, b) -> partialSum + (b.getQuantity() * b.getUnitcost()), Integer::sum) / totalQuantity;
    }

    private Integer getTotalQuantity(List<CapitalGain> capitalGainsBuy) {
        return capitalGainsBuy.stream().reduce(0, (partialTotal, item) -> partialTotal + item.getQuantity(), Integer::sum);
    }
}
