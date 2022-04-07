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
public class TransactionFilterDTO {
    private String id;
    @NotEmpty(message = "excDate is Null or is Empty")
    private Integer fromCardId;
    @Positive
    private Integer toCardId;
    @Positive
    private Long amount;
    @Past
    private LocalDate fromDate;
    @FutureOrPresent
    private LocalDate toDate;
}
