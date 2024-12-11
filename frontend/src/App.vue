<!-- <template>
  <div id="app">
    <router-view />
  </div>
</template>

<script>
export default {
  name: "App"
};
</script>

<style>
/* Global styles */
</style> -->
<template>
  <div id="app">
    <!-- <nav>
      <router-link to="/">Home</router-link> |
      <router-link v-if="!isLoggedIn" to="/login">Login</router-link> |
      <router-link v-if="!isLoggedIn" to="/register">Register</router-link> |
      <router-link v-if="isLoggedIn" to="/profile">Profile</router-link> |
      <router-link :to="{ name: 'DetailPage', params: { id: 1 } }">Detail (Post 1)</router-link> |
      <button v-if="isLoggedIn" @click="logout">Logout</button>
    </nav> -->
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App',
  computed: {
    isLoggedIn() {
      return !!localStorage.getItem('authToken'); // Kiểm tra xem người dùng đã đăng nhập chưa
    },
  },
  methods: {
    logout() {
      localStorage.removeItem('authToken'); // Xóa token
      this.$router.push('/login'); // Điều hướng về trang đăng nhập
    },
  },
};
</script>

<style scoped>
nav {
  margin-bottom: 20px;
}
</style>
