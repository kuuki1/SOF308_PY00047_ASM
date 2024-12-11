<template>
  <div class="wrapper">
    <!-- Header Section -->
    <header class="header">
      <div class="logo-container">
        <img class="main-image" src="img/unnamed.jpg" />
        <MovingText />
      </div>
    </header>

    <!-- Navigation Bar -->
    <nav class="nav">
      <div class="nav-left">
        <router-link to="/" exact><i class="fas fa-home"></i> Home</router-link>
      </div>
      <div class="nav-right">
        <router-link v-if="!isLoggedIn" to="/login" class="login-button">
          <i class="fas fa-user-circle"></i> sign in
        </router-link>
        <router-link v-if="!isLoggedIn" to="/register" class="register-button">
          <i class="fas fa-user-plus"></i> sign up
        </router-link>
        <button v-if="isLoggedIn" class="logout-button" @click="logout">
          <i class="fas fa-sign-out-alt"></i> Log out
        </button>
      </div>
    </nav>

    <!-- Main Content Area -->
    <div class="content">
      <article class="articles">
        <h2>Latest news</h2>
        <div v-for="post in posts" :key="post.id" class="post">
          <img src="../../public/img/unnamed.jpg" :alt="post.title" class="post-image" />
          <div class="post-content">
            <h3><router-link :to="'/post/' + post.id">{{ post.title }}</router-link></h3>
            <p class="post-date"> Views: {{ post.views }}</p>
            <p class="post-description">{{ post.description }}</p>
          </div>
        </div>
      </article>

      <aside class="sidebar">
        <h4>Top 5 Breaking News</h4>
        <ul class="top-news">
          <li v-for="topPost in topPosts" :key="topPost.id">
            <router-link :to="'/post/' + topPost.id">{{ topPost.title }}</router-link>
          </li>
        </ul>
      </aside>
    </div>
    <!-- Nút thêm bài viết (Hiển thị khi đã đăng nhập) -->
    <div v-if="isLoggedIn" class="add-post">
      <button class="add-post-button" @click="$router.push({ name: 'NewPostForm' })">
        <i class="fa fa-plus"></i> <!-- Sử dụng biểu tượng FontAwesome thay vì chữ -->
      </button>
    </div>

    <!-- Footer Section -->
    <footer class="footer">
      <p>&copy; PY00047 Jacker blog. All rights reserved.</p>
    </footer>
  </div>
</template>

<style scoped>
/* Toàn bộ giao diện */
.wrapper {
  display: flex;
  flex-direction: column;
  min-height: 100vh;
  font-family: 'Roboto', sans-serif;
  background-color: #f4f7fc;
  color: #333;
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

/* Footer */
.footer {
  width: 100%;
  text-align: center;
  padding: 20px 0;
  background-color: #2c3e50;
  color: #fff;
  position: relative;
  margin-top: auto;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);
}

.footer p {
  margin: 0;
  font-size: 14px;
  font-weight: 300;
}

/* Header */
.header {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #3498db;
  padding: 20px 40px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.logo-container {
  display: flex;
  align-items: center;
  justify-content: center;
}

.main-image {
  max-width: 100%;
  height: 80px;
  object-fit: contain;
  margin-right: 20px;
}

/* Navigation Bar */
.nav {
  background-color: #2980b9;
  padding: 15px 30px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  position: sticky;
  top: 0;
  z-index: 10;
}

.nav-left a,
.nav-right a,
.nav-center a {
  color: #fff;
  text-decoration: none;
  font-size: 16px;
  margin: 0 20px;
  padding: 10px 15px;
  border-radius: 5px;
  transition: background-color 0.3s ease;
}

.nav-left a:hover,
.nav-right a:hover {
  background-color: #1abc9c; /* Màu nền nhẹ khi hover */
}

.nav-left a:focus,
.nav-right a:focus {
  outline: none;
  background-color: #16a085; /* Màu nền đậm khi focus */
}

/* Content Layout */
.content {
  display: flex;
  gap: 40px;
  padding: 40px;
  margin-top: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.articles {
  flex: 3;
}

.sidebar {
  flex: 1;
  padding: 0 20px;
  background-color: #ecf0f1;
  border-radius: 8px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.sidebar h4 {
  font-size: 20px;
  color: #2980b9;
  margin-bottom: 15px;
  font-weight: 600;
}

.top-news {
  list-style: none;
  padding: 0;
}

.top-news li {
  margin-bottom: 12px;
}

.top-news li a {
  color: #34495e;
  font-size: 16px;
  text-decoration: none;
  padding: 5px 0;
  display: block;
  transition: color 0.3s ease;
}

.top-news li a:hover {
  color: #2980b9;
}

/* Các bài viết */
.post {
  display: flex;
  flex-wrap: wrap;
  margin-bottom: 30px;
  padding-bottom: 20px;
  border-bottom: 1px solid #ddd;
}

.post-image {
  width: 180px;
  height: 120px;
  object-fit: cover;
  border-radius: 8px;
  margin-right: 20px;
}

.post-content {
  flex: 1;
}

.post-content h3 {
  font-size: 24px;
  font-weight: 600;
  color: #2980b9;
  margin-bottom: 10px;
  transition: color 0.3s ease;
}

.post-content h3:hover {
  color: #1abc9c;
}

.post-date {
  font-size: 14px;
  color: #7f8c8d;
}

.post-description {
  color: #7f8c8d;
  font-size: 16px;
  margin-top: 10px;
  line-height: 1.6;
}

/* Nút đăng xuất */
.logout-button {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  background-color: #e74c3c;
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  padding: 10px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.logout-button:hover {
  background-color: #c0392b;
  transform: translateY(-2px);
}

.logout-button:active {
  background-color: #e74c3c;
  transform: translateY(0);
}

/* Nút thêm bài viết */
.add-post {
  position: fixed;
  bottom: 20px;
  right: 20px;
  z-index: 1000;
}

.add-post-button {
  background-color: #3498db;
  color: white;
  border: none;
  padding: 20px;
  width: 60px;
  height: 60px;
  border-radius: 50%;
  cursor: pointer;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  transition: all 0.3s ease;
  display: flex;
  justify-content: center;
  align-items: center;
}

.add-post-button:hover {
  background-color: #1abc9c;
  transform: translateY(-5px);
}

.add-post-button:focus {
  outline: none;
  box-shadow: 0 0 8px rgba(0, 0, 0, 0.3);
}

.add-post-button:active {
  transform: translateY(2px);
}

/* Điều chỉnh responsive cho các màn hình nhỏ */
@media screen and (max-width: 768px) {
  .content {
    flex-direction: column;
    padding: 20px;
  }

  .post {
    flex-direction: column;
    align-items: center;
  }

  .post-image {
    width: 100%;
    height: auto;
    margin-bottom: 15px;
  }

  .sidebar {
    margin-top: 30px;
  }

  .add-post {
    bottom: 15px;
    right: 15px;
  }
}

</style>

<script>
import MovingText from '@/components/MovingText.vue';
import axios from 'axios';

export default {
  components: {
    MovingText,
  },
  data() {
    return {
      posts: [], // Danh sách bài viết từ backend
      topPosts: [], // Top 5 bài viết
      loading: true, // Trạng thái tải dữ liệu
      error: null, // Lưu lỗi nếu có
    };
  },
  computed: {
    isLoggedIn() {
      return !!localStorage.getItem('authToken'); // Kiểm tra trạng thái đăng nhập
    },
  },
  async created() {
    await this.fetchPosts(); // Gọi fetchPosts khi component được tạo
  },
  methods: {
    async fetchPosts() {
      this.loading = true; // Bắt đầu tải dữ liệu
      this.error = null; // Reset lỗi

      try {
        // Gọi API lấy danh sách bài viết
        const response = await axios.get('http://localhost:8080/api/posts'); // Thay bằng endpoint của bạn
        
        if (Array.isArray(response.data)) {
          // Gán dữ liệu vào posts
          this.posts = response.data;

          // Lọc 5 bài viết có lượt xem cao nhất
          this.topPosts = [...this.posts]
            .sort((a, b) => b.views - a.views)
            .slice(0, 5);
        } else {
          throw new Error('Dữ liệu trả về không đúng định dạng.');
        }
      } catch (error) {
        console.error('Error fetching posts:', error);
        this.error = 'Không thể tải dữ liệu bài viết. Vui lòng thử lại sau.';
      } finally {
        this.loading = false; // Kết thúc tải dữ liệu
      }
    },
    logout() {
      localStorage.removeItem('authToken'); // Xóa token đăng nhập
      this.$router.push('/login'); // Điều hướng về trang đăng nhập
    },
  },
};
</script>