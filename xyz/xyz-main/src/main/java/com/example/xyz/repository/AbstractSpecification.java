package com.example.xyz.repository;

import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collection;

public interface AbstractSpecification<T> {
    default Specification<T> equal(String attribute, String field, Object value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(attribute).get(field), value);
    }
    default Specification<T> equal(String attribute, String subAttribute, String field, Object value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(attribute).get(subAttribute).get(field), value);
    }
    default Specification<T> joinInner(String attribute, String field, Object value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join(attribute, JoinType.INNER).get(field), value);
    }
    default Specification<T> joinInner(String attribute, String subAttribute, String field, Object value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join(attribute, JoinType.INNER).join(subAttribute, JoinType.INNER).get(field), value);
    }
    default Specification<T> joinOuter(String attribute, String field, Object value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join(attribute, JoinType.LEFT).get(field), value);
    }

    default Specification<T> equal(String field, Object value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(field), value);
    }
    default Specification<T> like(String field, String value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(field), "%"+value+"%");
    }
    default Specification<T> in(String field, Collection value) {
        return (root, query, criteriaBuilder) ->
                root.get(field).in(value);
    }

    // subquery
    default Specification<T> in(String field, Subquery subquery) {
        return (root, query, criteriaBuilder) ->
                root.get(field).in(subquery);
    }
    default Specification<T> exist(Subquery subquery) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.exists(subquery);
    }

    // Comparable
    default Specification<T> lessThan(String field, Comparable value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThan(root.get(field), value);
    }
    default Specification<T> greaterThan(String field, Comparable value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThan(root.get(field), value);
    }
    default Specification<T> between(String field, Comparable from, Comparable to) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(field), from, to);
    }
}
