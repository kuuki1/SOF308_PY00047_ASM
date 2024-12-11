<template>
  <div class="post-form">
    <h2>Add a new post</h2>
    <form @submit.prevent="submitPost">
      <div>
        <label for="title">Title:</label>
        <input id="title" v-model="title" type="text" required />
      </div>
      <div>
        <label for="content">Content:</label>
        <textarea id="content" v-model="content" required></textarea>
      </div>
      <div>
        <label for="image">IMG:</label>
        <input id="image" type="file" @change="handleFileUpload" />
      </div>
      <button type="submit">ADD</button>
      <button @click="$router.push('/')">Back</button>
    </form>
  </div>
</template>



<style scoped>
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

input,
textarea,
button {
  width: 100%;
  padding: 10px;
  margin-bottom: 15px;
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
</style>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      title: '',
      content: '',
      imageFile: null, // Lưu trữ file ảnh tải lên
    };
  },
  methods: {
    // Handle file upload
    handleFileUpload(event) {
      this.imageFile = event.target.files[0]; // Lấy file ảnh từ input
    },

    // Submit post with image
    async submitPost() {
      const formData = new FormData();
      formData.append('title', this.title);
      formData.append('content', this.content);

      if (this.imageFile) {
        formData.append('image', this.imageFile); // Đặt đúng tên tham số
      }

      try {
        const response = await axios.post('http://localhost:8080/api/posts', formData, {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        });   

        alert('The article was added successfully!');
        console.log(response.data); // In ra đường dẫn ảnh được lưu trên server

        // Reset form sau khi gửi thành công
        this.title = '';
        this.content = '';
        this.imageFile = null;
      } catch (error) {
        console.error('Error when adding a post:', error);
        alert('An error occurred when adding an article. Please try again!');
      }
    },
  },
};
</script>