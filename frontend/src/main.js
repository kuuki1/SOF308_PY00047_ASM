// src/main.js
import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import '@fortawesome/fontawesome-free/css/all.css';
import '@fortawesome/fontawesome-free/js/all.js';


const app = createApp(App);

// Cấu hình base URL cho các yêu cầu API
axios.defaults.baseURL = 'http://localhost:8081/api';

// Đưa axios vào toàn bộ ứng dụng
app.config.globalProperties.$axios = axios;

app.use(router);
app.mount('#app');

axios.interceptors.response.use(
    (response) => response,
    (error) => {
      if (error.response.status === 401) {
        alert('Phiên đăng nhập đã hết hạn. Vui lòng đăng nhập lại.');
        localStorage.removeItem('authToken'); // Xóa token
        window.location.href = '/login'; // Điều hướng đến trang đăng nhập
      }
      return Promise.reject(error);
    }
  );