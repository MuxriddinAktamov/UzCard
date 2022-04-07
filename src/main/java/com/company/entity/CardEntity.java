package com.company.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "card")
@ToString
public class CardEntity extends BaseEntity {
    @Column(name = "number", nullable = false, unique = true)
    private String number;
    @Column(name = "exc_date")
    private String excDate;
    @Column(name = "balance")
    private Long balance; // in (tiyin)

    @Column(name = "profile_id", nullable = false)
    private Integer profileId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", insertable = false, updatable = false)
    private ProfileEntity profile;
}
