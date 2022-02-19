package com.capitalgain.factory.impl;

import com.capitalgain.model.Operation;
import com.capitalgain.factory.CapitalGainFactory;
import com.capitalgain.strategy.CapitalGainStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CapitalGainFactoryImpl implements CapitalGainFactory {

    private final ApplicationContext applicationContext;

    @Override
    public CapitalGainStrategy findStrategy(Operation operation) {
        return applicationContext
                .getBeansOfType(CapitalGainStrategy.class)
                .values()
                .stream()
                .filter(operationStrategy -> operationStrategy.getOperation().equals(operation))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Strategy not loaded"));
    }
}
