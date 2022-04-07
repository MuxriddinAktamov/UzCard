package com.company.dto;

import com.company.entity.CardEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionDTO {
    private Integer id;
    @NotEmpty(message = "excDate is Null or is Empty")
    private Integer fromCardId;
    @Positive
    private Integer toCardId;
    @Positive
    private Long amount;
    private LocalDateTime createdDate;
}
