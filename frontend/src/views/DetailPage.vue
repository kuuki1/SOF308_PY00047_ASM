<template>
  <div class="container">
    <!-- Hiển thị chi tiết bài viết -->
    <div v-if="post">
      <h2>{{ post.title }}</h2>
      <p>{{ post.content }}</p>
  
      <!-- Hiển thị danh sách bình luận -->
      <h3>Comments</h3>
      <ul class="list-group mb-3">
        <li v-for="comment in comments" :key="comment.id" class="list-group-item">
          <strong>{{ comment.authorName }}</strong>: {{ comment.content }}
        </li>
      </ul>
  
      <!-- Form thêm bình luận nếu đã đăng nhập -->
      <form v-if="authToken" @submit.prevent="addComment">
        <div class="mb-3">
          <label for="newComment" class="form-label">Add a comment</label>
          <textarea
            id="newComment"
            v-model="newComment"
            class="form-control"
            required
          ></textarea>
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
      </form>
  
      <!-- Nếu chưa đăng nhập, hiển thị liên kết đăng nhập -->
      <div v-else>
        <p><router-link to="/login">Login</router-link> to add a comment.</p>
      </div>
    </div>
  
    <!-- Hiển thị thông báo nếu bài viết không tồn tại -->
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
        post: null,
        comments: [],
        newComment: '',
        authToken: localStorage.getItem('authToken') || '',
      };
    },
    async created() {
      const postId = this.$route.params.id;
      try {
        // Lấy dữ liệu bài viết
        const postResponse = await axios.get(`http://localhost:5000/api/posts/${postId}`);
        this.post = postResponse.data;
  
        // Lấy dữ liệu bình luận
        const commentResponse = await axios.get(`http://localhost:5000/api/comments/${postId}`);
        this.comments = commentResponse.data;
      } catch (error) {
        console.error('Failed to load post or comments:', error);
      }
    },
    methods: {
      async addComment() {
        if (!this.newComment.trim()) return;
  
        try {
          // Gửi bình luận mới
          const response = await axios.post(
            `http://localhost:5000/api/comments`,
            {
              content: this.newComment,
              postId: this.post.id,
            },
            {
              headers: { Authorization: `Bearer ${this.authToken}` },
            }
          );
          this.comments.push(response.data);
          this.newComment = '';
        } catch (error) {
          console.error('Failed to add comment:', error);
        }
      },
    },
  };
  </script>
  
  <style scoped>
  .container {
    max-width: 800px;
    margin: auto;
  }
  </style>
  