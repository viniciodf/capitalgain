package com.capitalgain.factory;

import com.capitalgain.model.Operation;
import com.capitalgain.strategy.CapitalGainStrategy;

public interface CapitalGainFactory {
    CapitalGainStrategy findStrategy(Operation operation);
}
