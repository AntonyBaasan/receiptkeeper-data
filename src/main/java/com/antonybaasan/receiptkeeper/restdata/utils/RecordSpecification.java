package com.antonybaasan.receiptkeeper.restdata.utils;

import com.antonybaasan.receiptkeeper.restdata.model.Record;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Date;

public class RecordSpecification implements Specification<Record> {

    private SearchCriteria criteria;

    public RecordSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Record> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder builder) {

        if (criteria.getOperation().equalsIgnoreCase(">")) {
            if (criteria.getValue() instanceof Date) {
                return builder.greaterThanOrEqualTo(root.<Date>get(criteria.getKey()), (Date) criteria.getValue());
            }
            return builder.greaterThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("<")) {
            if (criteria.getValue() instanceof Date) {
                return builder.lessThanOrEqualTo(root.<Date>get(criteria.getKey()), (Date) criteria.getValue());
            }
            return builder.lessThanOrEqualTo(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase("=")) {
            return builder.equal(root.<String>get(criteria.getKey()), criteria.getValue().toString());
        } else if (criteria.getOperation().equalsIgnoreCase(":")) {
            if (root.get(criteria.getKey()).getJavaType() == String.class) {
                return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
            } else {
                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
            }
        }
        return null;

    }
}
