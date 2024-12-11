package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
public class Comment {

    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;  // Liên kết với bài viết

    @Getter
    @Setter
    private String content;  // Nội dung bình luận

    @Getter
    @Setter
    private LocalDateTime date;  // Thời gian bình luận

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // Tham chiếu đến người dùng

    @Getter
    @Setter
    @Column(nullable = false, updatable = false)  // updatable = false để tránh thay đổi
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        if (this.date == null) {
            this.date = LocalDateTime.now();  // Nếu date chưa được gán, tự động gán thời gian hiện tại
        }
        this.createdAt = LocalDateTime.now();  // Tự động gán thời gian tạo
    }
}

