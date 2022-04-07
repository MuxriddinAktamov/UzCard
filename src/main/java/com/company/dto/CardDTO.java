package com.company.dto;

import com.company.entity.ProfileEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CardDTO {
    private Integer id;
    @NotEmpty(message = "Number is Null or is Empty")
    private String number;
    @NotEmpty(message = "excDate is Null or is Empty")
    private String excDate;
    private Long balance; // in (tiyin)
    @Positive
    private Integer profileId;
    private LocalDateTime createdDate;
}
