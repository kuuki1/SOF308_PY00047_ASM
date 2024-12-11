<template>
  <div class="post-detail">
    <h2>{{ post.title }}</h2>
    <div v-if="post.imageUrl">
      <img src="../../public/img/unnamed.jpg" alt="Post Image" class="post-image" />
    </div>

    
    <p><strong>Views:</strong> {{ post.views }}</p>
    <p>{{ post.content }}</p>

    <!-- Form thêm bình luận -->
    <div v-if="isLoggedIn" class="comment-form">
      <h3>Add a comment</h3>
      <textarea v-model="newComment" placeholder="Viết bình luận..."></textarea>
      <button @click="submitComment">Send</button>
    </div>
    <div v-else>
      <p>Please <router-link to="/login">Log in</router-link> for comment.</p>
    </div>

    <!-- Danh sách bình luận -->
    <div v-if="comments.length" class="comments">
      <h3>Comment</h3>
      <div v-for="comment in comments" :key="comment.id" class="comment">
        <div class="comment-header">
          <img v-if="comment.user?.profile_picture" :src="comment.user?.profile_picture" alt="Profile Picture" class="profile-image" />
          <strong>{{ comment.user?.name || 'Người dùng ẩn danh' }}:</strong>
        </div>
        <p>{{ comment.content }}</p>
        
      </div>
    </div>
    <div v-else>
      <p>There is no comment yet.</p>
    </div>

    <button @click="$router.push('/')">Back</button>
  </div>
</template>



<style scoped>
.post-detail {
  max-width: 900px;
  margin: 30px auto;
  padding: 25px;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
}

.post-detail h2 {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 15px;
  color: #b22222; /* Màu đỏ đậm */
}

.post-image {
  width: 100%;
  height: auto;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  margin: 20px 0;
}

.comment-form {
  margin-top: 30px;
  background-color: #f0f0f0;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

textarea {
  width: 100%;
  min-height: 120px;
  padding: 12px;
  border-radius: 8px;
  border: 1px solid #ddd;
  font-size: 16px;
  margin-bottom: 15px;
  transition: border 0.3s ease;
}

textarea:focus {
  border-color: #b22222; /* Khi focus, viền trở thành màu đỏ */
}

button {
  padding: 12px 20px;
  background-color: #b22222;
  color: white;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  font-size: 16px;
  font-weight: bold;
  transition: all 0.3s ease;
}

button:hover {
  background-color: #a11e1e; /* Màu nền đỏ tối hơn khi hover */
  transform: scale(1.05); /* Tăng kích thước nút khi hover */
}

button:active {
  transform: scale(1); /* Trở lại kích thước bình thường khi click */
}

.comments {
  margin-top: 30px;
}

.comment {
  background-color: #fff;
  padding: 15px;
  border-radius: 8px;
  margin-bottom: 20px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.05);
  transition: transform 0.3s ease;
}

.comment:hover {
  transform: translateX(5px); /* Di chuyển nhẹ sang phải khi hover */
}

.comment-header {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
}

.profile-image {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  margin-right: 15px;
  border: 2px solid #ddd; /* Thêm đường viền cho ảnh đại diện */
}

.comment-header strong {
  font-size: 18px;
  color: #333;
}

.comment-date {
  font-size: 13px;
  color: #888;
}

.comment p {
  font-size: 16px;
  line-height: 1.6;
  color: #333;
}

/* Nút Quay lại */
button:focus {
  outline: none; /* Xóa viền khi focus */
}

button {
  background-color: #8b0000; /* Nền đỏ đậm */
  border: none;
  padding: 10px 15px;
  font-size: 16px;
  color: white;
  cursor: pointer;
  border-radius: 5px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: background-color 0.3s, transform 0.2s;
}

button:hover {
  background-color: #b22222; /* Đổi màu khi hover */
  transform: scale(1.05); /* Phóng to khi hover */
}

button:active {
  background-color: #d32f2f; /* Đổi màu khi nhấn */
  transform: scale(1); /* Trở lại kích thước ban đầu khi nhấn */
}

</style>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      post: {
        title: '',
        content: '',
        imageUrl: '',
        date: '',
        views: 0,
      },
      comments: [],
      isLoggedIn: false,
      newComment: '',
    };
  },
  created() {
    // Kiểm tra trạng thái đăng nhập
    this.isLoggedIn = !!localStorage.getItem('authToken');
    this.fetchPostDetails();
  },
  methods: {
    // Hàm định dạng thời gian theo định dạng yyyy-MM-dd HH:mm:ss.SSSSSS
    formatDate(dateString) {
      const date = new Date(dateString);
      const year = date.getFullYear();
      const month = (date.getMonth() + 1).toString().padStart(2, '0');
      const day = date.getDate().toString().padStart(2, '0');
      const hours = date.getHours().toString().padStart(2, '0');
      const minutes = date.getMinutes().toString().padStart(2, '0');
      const seconds = date.getSeconds().toString().padStart(2, '0');
      const milliseconds = date.getMilliseconds().toString().padStart(3, '0');

      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}.${milliseconds}`;
    },

    // Hàm lấy đường dẫn ảnh hoàn chỉnh
    getImageUrl(imageUrl) {
      // Kiểm tra nếu imageUrl bắt đầu với '/' để đảm bảo đúng định dạng URL
      if (imageUrl && imageUrl.startsWith('/')) {
        return `http://localhost:8080${imageUrl}`;  // Thay đổi 'localhost:8080' nếu cần
      }
      return imageUrl;
    },

    // Tải dữ liệu bài viết và bình luận
    async fetchPostDetails() {
      try {
        const postId = this.$route.params.id;

        // Lấy chi tiết bài viết
        const postResponse = await axios.get(`http://localhost:8080/api/posts/${postId}`);
        this.post = postResponse.data;

        // Lấy danh sách bình luận
        const commentsResponse = await axios.get(`http://localhost:8080/api/posts/${postId}/comments`);
        this.comments = commentsResponse.data.map(comment => ({
          ...comment,
          user: comment.user || { name: 'Mysterious Person', profile_picture: '' },
        }));
      } catch (error) {
        console.error('Error loading post or comment data:', error);
      }
    },

    // Gửi bình luận mới
    async submitComment() {
      if (!this.newComment.trim()) {
        alert('Please enter your comment.');
        return;
      }

      try {
        const token = localStorage.getItem('authToken');
        const postId = this.$route.params.id;

        await axios.post(
          `http://localhost:8080/api/posts/${postId}/comments`,
          { content: this.newComment },
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        alert('Comments have been added!');
        this.newComment = ''; // Reset textarea
        this.fetchPostDetails(); // Tải lại danh sách bình luận
      } catch (error) {
        console.error('Unable to add a comment:', error);
        alert('Error when adding a comment. Please try again.');
      }
    },
  },
};
</script>