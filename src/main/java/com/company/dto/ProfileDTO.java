package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class ProfileDTO {
    private Integer id;
    @NotEmpty(message = "excDate is Null or is Empty")
    private String name;
    @NotEmpty(message = "excDate is Null or is Empty")
    private String surname;
    @NotEmpty(message = "excDate is Null or is Empty")
    private String middleName;
    @PastOrPresent
    private LocalDate birthDate;
    private LocalDateTime createdDate;
}
