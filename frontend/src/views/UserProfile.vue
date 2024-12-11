<template>
  <div class="container">
    <h2>Profile</h2>
    <form @submit.prevent="updateProfile">
      <div class="mb-3">
        <label for="name" class="form-label">Name</label>
        <input
          id="name"
          v-model="name"
          type="text"
          class="form-control"
          required
        />
      </div>
      <div class="mb-3">
        <label for="email" class="form-label">Email</label>
        <input
          id="email"
          v-model="email"
          type="email"
          class="form-control"
          required
        />
      </div>
      <button type="submit" class="btn btn-primary">
        Save Changes
      </button>
    </form>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      name: '',
      email: '',
      authToken: localStorage.getItem('authToken') || '',
    };
  },
  async created() {
    // Fetch profile data when component is created
    try {
      const response = await axios.get('http://localhost:5000/api/auth/profile', {
        headers: { Authorization: `Bearer ${this.authToken}` },
      });
      this.name = response.data.name;
      this.email = response.data.email;
    } catch (error) {
      console.error('Failed to load profile:', error);
    }
  },
  methods: {
    // Method to update the profile details
    async updateProfile() {
      try {
        await axios.put(
          'http://localhost:5000/api/auth/profile',
          { name: this.name, email: this.email },
          { headers: { Authorization: `Bearer ${this.authToken}` } }
        );
        alert('Profile updated successfully');
      } catch (error) {
        console.error('Failed to update profile:', error);
      }
    },
  },
};
</script>
