package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Getter
@Setter
public class ProfileFilterDTO {

    private Integer id;
    @NotEmpty(message = "excDate is Null or is Empty")
    private String name;
    @NotEmpty(message = "excDate is Null or is Empty")
    private String surname;
    @NotEmpty(message = "excDate is Null or is Empty")
    private String middleName;
    @PastOrPresent
    private LocalDate birthDate;
    @Past
    private LocalDate fromDate;
    @FutureOrPresent
    private LocalDate toDate;
}
