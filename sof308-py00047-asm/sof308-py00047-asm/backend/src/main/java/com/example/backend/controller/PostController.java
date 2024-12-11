package com.example.backend.controller;

import com.example.backend.model.Comment;
import com.example.backend.model.Post;
import com.example.backend.service.CommentService;
import com.example.backend.service.PostService;
import com.example.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    // Lấy tất cả bài viết
    @GetMapping
    public List<Post> getAllPosts() {
        return postService.findAllPosts();
    }


    // Lấy chi tiết bài viết (với lượt xem tăng lên)
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        // Tăng lượt xem bài viết và trả về chi tiết bài viết
        Post post = postService.incrementViews(id);
        return ResponseEntity.ok(post);
    }


    // Constructor để inject PostService
    public PostController(PostService postService) {
        this.postService = postService;
    }

        @PostMapping
        public ResponseEntity<Post> createPost(@RequestParam String title,
                                               @RequestParam String content,
                                               @RequestParam(required = false) MultipartFile image) {
            try {
                String imageUrl = null;
                if (image != null && !image.isEmpty()) {
                    imageUrl = uploadImage(image);
                }

                Post post = new Post();
                post.setTitle(title);
                post.setContent(content);
                post.setImageUrl(imageUrl);
                post.setCreatedAt(LocalDateTime.now());
                post.setDate(LocalDateTime.now());
                post.setViews(0);

                // Log để kiểm tra dữ liệu bài viết
                System.out.println("Post to save: " + post);

                Post savedPost = postService.save(post); // Lưu bài viết
                return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
            }
        }



    // API để tải ảnh lên và lưu vào thư mục 'uploads'
    public String uploadImage(MultipartFile image) throws IOException {
        String uploadDir = "uploads/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFileName = image.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;
        Path filePath = uploadPath.resolve(fileName);

        // Kiểm tra xem file có được lưu thành công
        try {
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Image saved at: " + filePath.toString());
        } catch (IOException e) {
            System.out.println("Failed to save image: " + e.getMessage());
            throw e;
        }

        return fileName;
    }
}
