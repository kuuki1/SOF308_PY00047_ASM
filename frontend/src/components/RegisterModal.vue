
<template>
  <div v-if="show" class="modal">
    <div class="modal-content">
      <span class="close" @click="closeModal">&times;</span>
      <h2>Đăng Ký</h2>
      <form @submit.prevent="handleRegister">
        <div>
          <label for="name">Tên:</label>
          <input type="text" v-model="name" required />
        </div>
        <div>
          <label for="email">Email:</label>
          <input type="email" v-model="email" required />
        </div>
        <div>
          <label for="password">Mật khẩu:</label>
          <input type="password" v-model="password" required />
        </div>
        <div>
          <label for="confirmPassword">Xác nhận mật khẩu:</label>
          <input type="password" v-model="confirmPassword" required />
        </div>
        <div>
          <label for="phone">Số điện thoại:</label>
          <input type="tel" v-model="phone" required />
        </div>
        <button type="submit">Đăng Ký</button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: "RegisterModal",
  data() {
    return {
      show: false,
      name: "",
      email: "",
      password: "",
      confirmPassword: "",
      phone: "",
    };
  },
  methods: {
    openModal() {
      this.show = true;
    },
    closeModal() {
      this.show = false;
    },
    handleRegister() {
      // Xử lý đăng ký (có thể gửi dữ liệu tới API tại đây)
      alert("Đăng ký thành công!");
      this.closeModal();
    },
  },
};
</script>

<style scoped>
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}
.modal-content {
  background: white;
  padding: 20px;
  border-radius: 8px;
  width: 400px;
  max-width: 90%;
}
.close {
  position: absolute;
  top: 10px;
  right: 10px;
  cursor: pointer;
}
button {
  margin-top: 20px;
}
</style>
