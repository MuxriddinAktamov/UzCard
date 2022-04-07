package com.company.spec;

import com.company.entity.CardEntity;
import com.company.entity.ProfileEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class ProfileSpecfication {
    public static Specification<ProfileEntity> isNotNull(String field) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.isNotNull(root.get(field)));
    }

    public static Specification<ProfileEntity> id(String field, Integer id) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), id));
    }


    public static Specification<ProfileEntity> date(LocalDate date1, LocalDate date2) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.between(root.get("createdDate"), date1, date2));
    }

    public static Specification<ProfileEntity> equal(String field, String value) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), value));
    }
    public static Specification<ProfileEntity> balance(String field, LocalDate id) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(field), id));
    }
}
