package com.example.backend.repository;

import com.example.backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Phương thức tự động sinh ra để tìm bình luận theo postId
    static List<Comment> findByPostId(Long postId) {
        return null;
    }

    @Query("SELECT c FROM Comment c WHERE c.post.id = :post_id")
    List<Comment> findCommentsByPostId(@Param("post_id") Long postId);
}
