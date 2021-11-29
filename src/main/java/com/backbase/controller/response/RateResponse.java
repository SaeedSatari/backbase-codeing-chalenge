package com.backbase.controller.response;

import lombok.*;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class RateResponse {
    private String movieTitle;
    private BigDecimal rate;
    private String rateStatus;
}
