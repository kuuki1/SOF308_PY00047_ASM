
package com.example.backend.service;

import com.example.backend.model.Comment;
import com.example.backend.model.Post;
import com.example.backend.repository.CommentRepository;
import com.example.backend.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;


    public List<Post> findAllPosts() {
        return postRepository.findAll();
    }

    public void savePost(Post post) {
        postRepository.save(post);
    }
    // Lấy bình luận của bài viết
    public List<Comment> getCommentsByPostId(Long postId) {
        return CommentRepository.findByPostId(postId);
    }
    public Post findPostById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

//    public Post incrementViews(Long id) {
//        Post post = findPostById(id);
//        if (post == null) {
//            throw new RuntimeException("Post not found with id: " + id);
//        }
//        post.setViews(post.getViews() + 1); // Tăng lượt xem
//        return postRepository.save(post); // Lưu thay đổi
//    }

    public Post incrementViews(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new RuntimeException("Post not found"));
        post.setViews(post.getViews() + 1); // Tăng lượt xem
        postRepository.save(post); // Lưu lại bài viết với số lượt xem mới
        return postRepository.save(post);
    }

    public void savePostWithImage(String title, String content, MultipartFile image) throws IOException {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);

        if (image != null && !image.isEmpty()) {
            // Tạo tên file duy nhất bằng cách thêm timestamp vào tên file gốc
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();

            // Thư mục lưu ảnh trong dự án
            String UPLOAD_DIR = "uploads/"; // Dùng thư mục uploads trong dự án thay vì Images

            // Tạo đường dẫn file
            Path filePath = Paths.get(UPLOAD_DIR + fileName);

            // Tạo thư mục nếu chưa tồn tại
            Files.createDirectories(filePath.getParent());

            // Lưu ảnh vào thư mục
            Files.write(filePath, image.getBytes());

            // Lưu tên file ảnh vào đối tượng post
            post.setImage(fileName);
        }

        // Lưu đối tượng post vào cơ sở dữ liệu
        postRepository.save(post);
    }


//    // Save the Post into the database
//    public Post save(Post post) {
//        postRepository.save(post);
//        return post;
//    }
    @Autowired
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post save(Post post) {
        System.out.println("Saving post: " + post); // Thêm log kiểm tra
        return postRepository.save(post); // Lưu bài viết
    }
}
