import { createRouter, createWebHistory } from 'vue-router';
import HomePage from '@/views/HomePage.vue';
import LoginPage from '@/views/LoginPage.vue';
import RegisterPage from '@/views/RegisterPage.vue';
import PostForm from '@/views/PostForm.vue';
import PostDetail from '@/views/PostDetail.vue';
import NewPostForm from '@/views/NewPostForm.vue';
import UserProfile from '@/views/UserProfile.vue';

const routes = [
  { path: '/', component: HomePage, name: 'HomePage' },
  { 
    path: '/login', 
    component: LoginPage, 
    name: 'LoginPage',
    meta: { requiresGuest: true }, // Chỉ cho phép người dùng chưa đăng nhập
  },
  { 
    path: '/register', 
    component: RegisterPage, 
    name: 'RegisterPage',
    meta: { requiresGuest: true }, // Chỉ cho phép người dùng chưa đăng nhập
  },
  { 
    path: '/profile', 
    component: UserProfile, 
    name: 'UserProfile', 
    meta: { requiresAuth: true }, // Chỉ cho phép người dùng đã đăng nhập
  },
  {
    path: '/post-form',
    name: 'PostForm',
    component: PostForm,
    meta: { requiresAuth: true }, // Yêu cầu người dùng đã đăng nhập
  },
  {
    path: '/post/:id',
    component: PostDetail,
    name: 'DetailPage',
    props: true,
  },
  { 
    path: '/post/new', 
    component: NewPostForm, 
    name: 'NewPostForm',
    meta: { requiresAuth: true, requiresSpecialPermission: false }, // Yêu cầu người dùng đã đăng nhập
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// Route Guard
router.beforeEach((to, from, next) => {
  const isLoggedIn = !!localStorage.getItem('authToken'); // Kiểm tra nếu người dùng đã đăng nhập
  const userEmail = localStorage.getItem('userEmail'); // Lấy email người dùng từ localStorage

  // Kiểm tra nếu trang yêu cầu đăng nhập mà người dùng chưa đăng nhập
  if (to.meta.requiresAuth && !isLoggedIn) {
    return next({ name: 'LoginPage' });
  }

  // Kiểm tra nếu trang yêu cầu người dùng chưa đăng nhập mà họ đã đăng nhập
  if (to.meta.requiresGuest && isLoggedIn) {
    return next({ name: 'HomePage' });
  }

  // Kiểm tra nếu trang yêu cầu quyền đặc biệt (email abc@gmail.com) để tạo bài viết mới
  if (to.meta.requiresSpecialPermission && userEmail !== 'thaibinh@gmail.com') {
    return next({ name: 'HomePage' });
  }

  // Tiếp tục nếu không có điều kiện nào bị vi phạm
  next();
});

export default router;
