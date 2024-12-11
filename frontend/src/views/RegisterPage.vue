<template>
  <div class="login-page">
    <div class="login-container">
      <h2>Register</h2>
      <form @submit.prevent="register">
        <div>
          <input
            v-model="name"
            type="text"
            placeholder="Name"
            required
          />
        </div>
        <div>
          <input
            v-model="email"
            type="email"
            placeholder="Email"
            required
          />
        </div>
        <div>
          <input
            v-model="password"
            type="password"
            placeholder="Password"
            required
          />
        </div>
        <button type="submit">Register</button>
        <button type="button" @click="$router.push('/')">Quay lại</button>
        <p v-if="errorMessage" class="error">{{ errorMessage }}</p>
      </form>
    </div>
  </div>
</template>



<style scoped>
.login-page {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background: linear-gradient(to right, #6a11cb, #2575fc); /* Gradient màu nền */
  background-attachment: fixed;
  margin: 0;
}

.login-container {
  padding: 30px;
  max-width: 400px;
  width: 100%;
  background: rgba(255, 255, 255, 0.7); /* Nền mờ mờ */
  border-radius: 15px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
  backdrop-filter: blur(10px); /* Tạo hiệu ứng Glassmorphism */
  text-align: center;
  transition: transform 0.3s ease, box-shadow 0.3s ease; /* Thêm hiệu ứng khi hover */
}

.login-container:hover {
  transform: translateY(-10px); /* Nâng container khi hover */
  box-shadow: 0 15px 30px rgba(0, 0, 0, 0.2); /* Tăng hiệu ứng bóng khi hover */
}

h2 {
  font-size: 26px;
  margin-bottom: 20px;
  font-family: 'Arial', sans-serif;
  color: #333;
  text-transform: uppercase;
  letter-spacing: 1px;
}

input {
  width: 100%;
  padding: 14px;
  margin-bottom: 20px;
  border-radius: 8px;
  border: 1px solid #ddd;
  box-sizing: border-box;
  font-size: 16px;
  transition: border 0.3s ease, box-shadow 0.3s ease;
}

input:focus {
  border-color: #3b7ddd;
  outline: none;
  box-shadow: 0 0 10px rgba(59, 125, 221, 0.5);
}

button {
  width: 100%;
  padding: 14px;
  background-color: #d32f2f;
  color: white;
  border-radius: 8px;
  margin-bottom: 15px;
  cursor: pointer;
  font-size: 16px;
  border: none;
  transition: background-color 0.3s ease, transform 0.3s ease;
}

button:hover {
  background-color: #8b0000;
  transform: scale(1.05); /* Phóng to khi hover */
}

button:active {
  transform: scale(1); /* Trở lại kích thước bình thường khi nhấn */
}

button:focus {
  outline: none;
}

button[type="button"] {
  background-color: #4CAF50;
  margin-top: 5px;
}

button[type="button"]:hover {
  background-color: #45a049;
}

.error {
  color: #ff4d4d;
  font-size: 0.9em;
  margin-top: 15px;
  animation: shake 0.5s ease; /* Thêm hiệu ứng lắc khi có lỗi */
}

@keyframes shake {
  0% { transform: translateX(0); }
  25% { transform: translateX(-5px); }
  50% { transform: translateX(5px); }
  75% { transform: translateX(-5px); }
  100% { transform: translateX(0); }
}
</style>

<script>
import axios from 'axios';

export default {
  name: 'RegisterPage',
  data() {
    return {
      name: '',
      email: '',
      password: '',
      errorMessage: '',
    };
  },
  created() {
    // Nếu đã đăng nhập, chuyển hướng về Home
    if (localStorage.getItem('authToken')) {
      this.$router.push('/');
    }
  },
  methods: {
    async register() {
      try {
        await axios.post('http://localhost:8080/api/auth/register', {
          name: this.name,
          email: this.email,
          password: this.password,
        });

        alert('Registration successful! Please login.');
        this.$router.push('/login'); // Chuyển hướng về trang Login
      } catch (error) {
        this.errorMessage = error.response?.data?.message || 'Registration failed.';
      }
    },
  },
};
</script>