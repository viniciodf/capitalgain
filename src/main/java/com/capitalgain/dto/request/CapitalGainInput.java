package com.capitalgain.dto.request;

import lombok.Data;

@Data
public class CapitalGainInput {
    private String operation;
    private Integer unitcost;
    private Integer quantity;
}
