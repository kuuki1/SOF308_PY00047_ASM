package com.example.backend.service;

import com.example.backend.model.Comment;
import com.example.backend.model.Post;
import com.example.backend.repository.CommentRepository;
import com.example.backend.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    // Lấy danh sách bình luận của một bài viết
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentRepository.findCommentsByPostId(postId);
    }

    // Thêm bình luận vào bài viết
    public Comment addCommentToPost(Long postId, Comment newComment) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        newComment.setPost(post);

        if (newComment.getCreatedAt() == null) {
            newComment.setCreatedAt(LocalDateTime.now());
        }

        return commentRepository.save(newComment);
    }
}
