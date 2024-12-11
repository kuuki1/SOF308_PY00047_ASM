<template>
  <div class="container">
    <!-- Hiển thị nếu post có dữ liệu -->
    <div v-if="post">
      <h2>{{ post.title }}</h2>
      <p>{{ post.content }}</p>
      <h4>Comments</h4>
      <ul class="list-group mb-3">
        <li
          v-for="comment in comments"
          :key="comment._id"
          class="list-group-item"
        >
          <strong>{{ comment.authorName }}</strong>: {{ comment.content }}
        </li>
      </ul>
      <!-- Form thêm comment, chỉ hiển thị khi đã đăng nhập -->
      <form v-if="authToken" @submit.prevent="addComment">
        <div class="mb-3">
          <label for="newComment" class="form-label">Add a comment</label>
          <textarea
            id="newComment"
            v-model="newComment"
            class="form-control"
            required
          />
        </div>
        <button type="submit" class="btn btn-primary">
          Submit
        </button>
      </form>
    </div>
    <!-- Nếu post chưa có, hiển thị thông báo lỗi -->
    <div v-else>
      <p>Loading post...</p>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      post: null,              // Dữ liệu bài viết
      comments: [],            // Dữ liệu bình luận
      newComment: '',          // Nội dung bình luận mới
      authToken: localStorage.getItem('authToken') || '', // Token người dùng
      isLoading: false,        // Trạng thái loading
    };
  },
  async created() {
    const postId = this.$route.params.id;
    try {
      this.isLoading = true;  // Bắt đầu load dữ liệu
      // Tải bài viết
      const postResponse = await axios.get(`http://localhost:5000/api/posts/${postId}`);
      this.post = postResponse.data;
      
      // Tải bình luận của bài viết
      const commentResponse = await axios.get(`http://localhost:5000/api/comments/${postId}`);
      this.comments = commentResponse.data;
    } catch (error) {
      console.error('Failed to load post or comments:', error);
    } finally {
      this.isLoading = false;  // Dữ liệu đã được tải xong
    }
  },
  methods: {
    async addComment() {
      try {
        if (this.newComment.trim() === '') return; // Kiểm tra nếu nội dung trống
        // Gửi bình luận mới
        const response = await axios.post(
          `http://localhost:5000/api/comments/${this.post._id}`,
          { content: this.newComment },
          { headers: { Authorization: `Bearer ${this.authToken}` } }
        );
        // Thêm bình luận mới vào danh sách bình luận
        this.comments.push(response.data);
        this.newComment = '';  // Làm trống form sau khi gửi
      } catch (error) {
        console.error('Failed to add comment:', error);
        alert('Failed to add comment, please try again.');
      }
    },
  },
};
</script>
