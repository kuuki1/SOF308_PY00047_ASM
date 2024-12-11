package com.example.backend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts")
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "author_id")
    private Long authorId;

    @Column(name = "image")
    private String image;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false, columnDefinition = "int default 0")
    private Integer views = 0; // Lượt xem, mặc định là 0

    @Column(nullable = false, updatable = false)  // updatable = false để tránh thay đổi sau khi tạo
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();  // Tự động gán thời gian hiện tại khi bài viết được tạo
    }

//    // Make sure to set valid author before saving
//    public void setAuthor(User author) {
//        if (author == null) {
//            throw new IllegalArgumentException("Author cannot be null");
//        }
//        this.author = author;
//    }

}
