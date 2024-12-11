<template>
  <h1 class="moving-text">
    <span>{{ words.join(' ') }}</span>
  </h1>
</template>

<script>
export default {
  name: 'MovingText',
  data() {
    return {
      words: ['Funny Blog'],
    };
  }
};
</script>

<style scoped>
.moving-text {
  display: inline-block;
  position: relative;
  font-size: 1.8em;
  font-weight: bold;
  color: white;;
  white-space: nowrap;
  overflow: hidden;  /* Ẩn phần chữ ngoài phạm vi */
  width: 100%;  /* Đảm bảo phần tử chứa có chiều rộng đủ */
}

.moving-text span {
  display: inline-block;
  animation: moveAndFade 5s linear infinite;
}

@keyframes moveAndFade {
  0% {
    transform: translateX(100%); /* Bắt đầu từ bên phải */
    opacity: 0;
  }
  25% {
    transform: translateX(0); /* Vào giữa */
    opacity: 1;
  }
  50% {
    transform: translateX(0); /* Giữ vị trí */
    opacity: 1;
  }
  100% {
    transform: translateX(-100%); /* Cuối cùng là chạy qua bên trái */
    opacity: 0;
  }
}
</style>
