<template>
  <div class="post-form">
    <h2>Đăng Bài Mới</h2>
    <form @submit.prevent="submitForm">
      <div>
        <label for="title">Tiêu đề:</label>
        <input id="title" v-model="title" type="text" required />
      </div>
      <div>
        <label for="description">Mô tả:</label>
        <textarea id="description" v-model="description" required></textarea>
      </div>
      <div>
        <label for="image">Ảnh:</label>
        <input id="image" type="file" @change="handleImageUpload" />
      </div>
      <button type="submit">Đăng bài</button>
    </form>

    <div class="post-list">
      <h2>Các bài viết</h2>
      <div v-for="post in posts" :key="post.id" class="post">
        <h3>{{ post.title }}</h3>
        <p>{{ post.description }}</p>
        <p><strong>Ngày:</strong> {{ post.date }}</p>
        <img v-if="post.imageUrl" :src="post.imageUrl" :alt="post.title" class="post-image" />
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  emits: ['add-post'],
  data() {
    return {
      title: '',
      description: '',
      image: null,
      posts: [], // Dữ liệu bài viết từ backend
    };
  },
  async created() {
    // Lấy danh sách bài viết khi component được tạo
    await this.fetchPosts();
  },
  methods: {
    async fetchPosts() {
      try {
        const response = await axios.get('http://localhost:8080/api/posts'); // Đổi URL thành endpoint backend của bạn
        this.posts = response.data;
      } catch (error) {
        console.error('Failed to fetch posts:', error);
      }
    },
    async submitForm() {
      try {
        const formData = new FormData();
        formData.append('title', this.title);
        formData.append('description', this.description);
        if (this.image) {
          formData.append('image', this.image);
        }

        // Gửi dữ liệu bài viết mới đến backend
        const response = await axios.post('http://localhost:8080/api/posts', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        });

        // Thêm bài viết mới vào danh sách hiện tại
        this.posts.unshift(response.data);

        // Reset form
        this.title = '';
        this.description = '';
        this.image = null;
      } catch (error) {
        console.error('Failed to submit post:', error);
      }
    },
    handleImageUpload(event) {
      this.image = event.target.files[0];
    },
  },
};
</script>

<style scoped>
/* Style cho form đăng bài */
.post-form {
  max-width: 600px;
  margin: 0 auto;
  padding: 20px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

h2 {
  font-size: 24px;
  margin-bottom: 20px;
}

input, textarea, button {
  width: 100%;
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
}

button {
  background-color: #4CAF50;
  color: white;
  border: none;
  cursor: pointer;
}

button:hover {
  background-color: #45a049;
}

.post-list {
  margin-top: 30px;
}

.post {
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 15px;
  margin-bottom: 20px;
}

.post-image {
  max-width: 100%;
  height: auto;
  margin-top: 10px;
}
</style>
