package com.company.repository;

import com.company.entity.ProfileEntity;
import com.company.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TransactionRepository extends JpaRepository<TransactionEntity, String>, JpaSpecificationExecutor<TransactionEntity> {

    List<TransactionEntity> findByToCardOrderByCreatedDate(String toCard);

    List<TransactionEntity> findByFromCardOrderByCreatedDate(String fromCard);

    @Query("select s from TransactionEntity s where s.fromCard.number=:card and s.toCard.number=:card")
    List<TransactionEntity> findByFromCardAndToCard(@Param("card") String card);

}
