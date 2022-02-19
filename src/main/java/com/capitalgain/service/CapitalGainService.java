package com.capitalgain.service;

import com.capitalgain.model.CapitalGain;
import com.capitalgain.model.Tax;

import java.util.List;

public interface CapitalGainService {
    List<Tax> calculateTax(List<CapitalGain> capitalGain);
}
