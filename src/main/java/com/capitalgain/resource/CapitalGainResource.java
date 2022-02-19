package com.capitalgain.resource;

import com.capitalgain.dto.request.CapitalGainInput;
import com.capitalgain.dto.response.CapitalGainOutput;
import com.capitalgain.model.CapitalGain;
import com.capitalgain.service.CapitalGainService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/capital-gain")
@RequiredArgsConstructor
public class CapitalGainResource {

    private final CapitalGainService capitalGainService;

    @PostMapping
    public List<CapitalGainOutput> calculateTax(@RequestBody List<CapitalGainInput> capitalGainInput) {
        return capitalGainService.calculateTax(capitalGainInput.stream().map(CapitalGain::of).collect(Collectors.toList())).stream().map(CapitalGainOutput::of).collect(Collectors.toList());
    }
}

