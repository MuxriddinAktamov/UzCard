package com.company.repository;

import com.company.entity.BaseEntity;
import com.company.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, Integer>, JpaSpecificationExecutor<CardEntity> {
    Optional<CardEntity> findByNumber(String number);

    @Transactional
    @Modifying
    @Query("update CardEntity as s set s.balance = s.balance + :amount where s.number =:number")
    void updateBalance(@Param("number") String number, @Param("amount") Long amount);

    @Query("Select s.balance from CardEntity s where s.number =:number ")
     Long getCardBalance(@Param("number") String number);


    @Query("Select s.number from CardEntity s where s.profileId =:number ")
     String findByProfileId(@Param("number") Integer profileId);
}
