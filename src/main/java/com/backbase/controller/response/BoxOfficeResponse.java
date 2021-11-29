package com.backbase.controller.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class BoxOfficeResponse {
    private String title;
    private String boxOffice;
}
