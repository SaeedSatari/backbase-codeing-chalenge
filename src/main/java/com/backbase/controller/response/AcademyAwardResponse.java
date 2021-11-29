package com.backbase.controller.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder(toBuilder = true)
public class AcademyAwardResponse {
    private String nominee;
    private String category;
    private Boolean won;
}
