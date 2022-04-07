package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
@Setter
public class CardFilterDTO {
    private String number;
    @NotEmpty(message = "excDate is Null or is Empty")
    private String excDate;
    private Long balance; // in (tiyin)
    @Positive
    private Integer profileId;
    @Past
    private LocalDate fromDate;
    @FutureOrPresent
    private LocalDate toDate;
}
