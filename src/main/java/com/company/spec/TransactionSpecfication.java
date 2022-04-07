package com.company.spec;

import com.company.entity.CardEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.TransactionEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class TransactionSpecfication {
    public static Specification<TransactionEntity> isNotNull(String field) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get(field)));
    }

    public static Specification<TransactionEntity> id(String field, Integer id) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), id));
    }


    public static Specification<TransactionEntity> date(LocalDate date1, LocalDate date2) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("createdDate"), date1, date2));
    }

    public static Specification<TransactionEntity> equal(String field, String value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), value));
    }
    public static Specification<TransactionEntity> balance(String field, Long id) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), id));
    }
}
