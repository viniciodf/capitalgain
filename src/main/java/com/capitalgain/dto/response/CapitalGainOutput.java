package com.capitalgain.dto.response;

import com.capitalgain.dto.request.CapitalGainInput;
import com.capitalgain.model.CapitalGain;
import com.capitalgain.model.Tax;
import lombok.Builder;
import lombok.Data;
import org.springframework.beans.BeanUtils;

@Data
@Builder
public class CapitalGainOutput {
    private Integer tax;

    public static CapitalGainOutput of(Tax tax){
        CapitalGainOutput capitalGainOutput = CapitalGainOutput.builder().build();
        BeanUtils.copyProperties(tax, capitalGainOutput);
        return capitalGainOutput;
    }
}
