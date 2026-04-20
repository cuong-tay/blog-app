package com.cuongtay.blog_app.repository;

import com.cuongtay.blog_app.entity.Comment;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository
        extends JpaRepository<Comment,Long>, JpaSpecificationExecutor<Comment> {
    // 1. Method name
    // tiền tố : findBy, countBy, deleteBy, existsBy (kiểm tra tồn tại)
    // select * from comment where email = ?
    List<Comment> findByEmail(String email);
    List<Comment> findByIdGreaterThanEqualAndIdLessThanEqual(Long minId, Long maxId);
    Page<Comment> findByPostId(Long postId, Pageable pageable);
    // delete from comment where name = ?
    void deleteByEmail(String email);

    // 2 Query annotation
    // chú ý câu truy vấn phải lấy tên thuộc tính tên bảng ở class chứ không phải trong database
    // HQL (Hibernate Query Language)
//    @Query("delete from Comment where post.id = ?1")
//    void deleteAllByPostId(Long postId);
//    @Query("delete from Comment where post.id = :postId")
//    void deleteAllByPostId(@Param("postId") Long postId);
    // Sql
    @Modifying // chú ý khi thực hiện các câu lệnh thay đổi dữ liệu như insert, update, delete
    @Transactional // đam bảo tính toàn vẹn dữ liệu khi sai thì rollback
    @Query(
            value = "delete from comment where post_id = :postId",
            nativeQuery = true
    )
    void deleteAllByPostId(@Param("postId") Long postId);

}
