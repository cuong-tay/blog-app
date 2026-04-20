package com.cuongtay.blog_app.specification;

import com.cuongtay.blog_app.entity.Post;
import com.cuongtay.blog_app.form.PostFilterForm;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class PostSpecification {
    public static Specification<Post> buildSpec(PostFilterForm form) {
        return form == null ? null : new Specification<Post>() {
            @Override
            public @Nullable Predicate toPredicate(
                    Root<Post> root,
                    CriteriaQuery<?> query,
                    CriteriaBuilder builder
            ) {
                // predicates là danh sách các điều kiện
                var predicates = new ArrayList<Predicate>();
                String search = form.getSearch();
                if (search != null) {
                    // ... title like %search%
                    var predicate = builder.like(
                            root.get("title"),
                            "%" + search + "%");
                    predicates.add(predicate);
                }
                var minCreateDate = form.getMinCreatedDate();
                // 2024-01-01 00:00:00
                if (minCreateDate != null) {
                   var minCreatedAt= LocalDateTime.of(minCreateDate, LocalTime.MIN);
                   // ... where createdAt >= ?
                    var predicate = builder.greaterThanOrEqualTo(
                            root.get("createAt"),
                            minCreatedAt
                    );
                    predicates.add(predicate);
                }
                var maxCreateDate = form.getMaxCreatedDate();
                // 2024-01-01 23:59:59.999999999
                if (maxCreateDate != null) {
                    var maxCreatedAt= LocalDateTime.of(maxCreateDate, LocalTime.MAX);
                    // ... where createdAt <= ?
                    var predicate = builder.lessThanOrEqualTo(
                            root.get("createAt"),
                            maxCreatedAt
                    );
                    predicates.add(predicate);
                }
                return builder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
