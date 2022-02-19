package com.capitalgain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Context {
    @Builder.Default private Integer averageValue = 0;
    @Builder.Default private Integer balance = 0;
    private CapitalGain lastCapitalGain;
}
