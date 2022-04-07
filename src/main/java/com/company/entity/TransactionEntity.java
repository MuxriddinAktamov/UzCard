package com.company.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "system_uuid")
    private String system_uuid;

    @ManyToOne
    @JoinColumn(name = "from_card_id")
    private CardEntity fromCard;

    @ManyToOne
    @JoinColumn(name = "to_card_id")
    private CardEntity toCard;
    @Column(name = "amount")
    private Long amount;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate = LocalDateTime.now();
}
