package com.capitalgain.model;

import com.capitalgain.dto.request.CapitalGainInput;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
@Builder
public class CapitalGain {
    private String operation;
    private Integer unitcost;
    private Integer quantity;
    @Builder.Default private Integer tax = 0;

    public static CapitalGain of(CapitalGainInput capitalGainInput){
        CapitalGain capitalGain = CapitalGain.builder().build();
        BeanUtils.copyProperties(capitalGainInput, capitalGain);
        return capitalGain;
    }
}
