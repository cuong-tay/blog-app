package com.cuongtay.blog_app.specification;

import com.cuongtay.blog_app.entity.Comment;
import com.cuongtay.blog_app.form.CommentFilterForm;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;

public class CommentSpecification {
    public static Specification<Comment> buildSpec(CommentFilterForm form) {
        return form ==null? null: new Specification<Comment>() {
            @Override
            public @Nullable Predicate toPredicate(Root<Comment> root,
                                                   CriteriaQuery<?> query,
                                                   CriteriaBuilder builder
            ) {
                var predicates = new ArrayList<Predicate>();
                // ĐIỀU KIỆN 1: Tìm kiếm theo name hoặc email
                String search = form.getSearch();
                if (search != null) {
                    var hasNameLike = builder.like(
                            root.get("name"),
                            "%" + search + "%"
                    );
                    var hasemailLike = builder.like(
                            root.get("email"),
                            "%" + search + "%"
                    );
                    var predicate = builder.or(hasNameLike, hasemailLike);
                    predicates.add(predicate);
                }
                // ĐIỀU KIỆN 2: Lọc theo bài viết
                Long postId = form.getPostId();
                if (postId != null) {
                    var predicate = builder.equal(root.get("post").get("id"), postId);
                    predicates.add(predicate);
                }
                return builder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
