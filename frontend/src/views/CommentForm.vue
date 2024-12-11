<template>
  <div class="comment-form-modal">
    <h3>Viết bình luận</h3>
    <textarea v-model="commentText" placeholder="Nhập bình luận của bạn..."></textarea>
    <button @click="submitComment">Gửi bình luận</button>
    <button class="close-button" @click="$emit('close-form')">Đóng</button>
  </div>
</template>
  
  <script>
  import axios from 'axios';
  export default {
    emits: ['add-comment', 'close-form'], // Khai báo sự kiện tùy chỉnh
    data() {
      return {
        commentText: '',
      };
    },
    methods: {
  async submitComment() {
    if (!this.newComment.trim()) {
      alert('Vui lòng nhập nội dung bình luận.');
      return;
    }

    try {
      const token = localStorage.getItem('authToken'); // Lấy token từ localStorage
      if (!token) {
        alert('Bạn cần đăng nhập để thực hiện bình luận.');
        return;
      }

      const postId = this.$route.params.id;
      await axios.post(
        `http://localhost:8080/api/posts/${postId}/comments`,
        { content: this.newComment },
        {
          headers: {
            Authorization: `Bearer ${token}`, // Gửi token trong header
          },
        }
      );

      alert('Bình luận đã được thêm thành công!');
      this.newComment = ''; // Reset nội dung sau khi gửi bình luận
      this.fetchPostDetails(); // Tải lại danh sách bình luận
    } catch (error) {
      console.error('Lỗi khi gửi bình luận:', error);
      alert('Không thể gửi bình luận. Vui lòng thử lại.');
    }
  },
},

  };
  </script>
  
  <style scoped>
  .comment-form-modal {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
    z-index: 1000;
    width: 400px;
  }
  textarea {
    width: 100%;
    height: 100px;
    margin-bottom: 10px;
    padding: 10px;
  }
  button {
    margin-right: 10px;
  }
  .close-button {
    background-color: #ffcc00;
    border: none;
    border-radius: 5px;
    padding: 5px 10px;
    cursor: pointer;
  }
  .close-button:hover {
    background-color: #ff9900;
  }
  </style>
  