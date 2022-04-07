package com.company.spec;

import com.company.entity.BaseEntity;
import com.company.entity.CardEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class CardSpecfication {

    public static Specification<CardEntity> isNotNull(String field) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get(field)));
    }

    public static Specification<CardEntity> id(String field, Integer id) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), id));
    }


    public static Specification<CardEntity> date(LocalDate date1, LocalDate date2) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("createdDate"), date1, date2));
    }

    public static Specification<CardEntity> equal(String field, String value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), value));
    }
    public static Specification<CardEntity> balance(String field, Long id) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), id));
    }
}
