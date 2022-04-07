package com.company.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "profile")
@ToString
public class ProfileEntity extends BaseEntity{
    @Column(name = "name")
    private String name;
    @Column(name = "surname")
    private String surname;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "birth_date")
    private LocalDate birthDate;
}
