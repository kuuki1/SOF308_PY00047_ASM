module.exports = {
  env: {
    browser: true,
    es2021: true,
    node: true, // Thêm dòng này để ESLint hiểu module.exports
  },
  extends: [
    'plugin:vue/vue3-recommended',
    'eslint:recommended',
  ],
  parserOptions: {
    ecmaVersion: 12,
    sourceType: 'module',
  },
  rules: {
    'vue/max-attributes-per-line': 'off',
    'vue/singleline-html-element-content-newline': 'off',
    'vue/html-self-closing': 'off',
    'vue/no-v-html': 'off', // Cảnh báo khi sử dụng v-html
    'no-unused-vars': 'warn', // Cảnh báo biến không được sử dụng
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off', // Cảnh báo khi sử dụng console trong môi trường sản xuất
    'vue/require-default-prop': 'off', // Tắt kiểm tra đối với props không có giá trị mặc định
    'vue/valid-v-for': 'error', // Cảnh báo khi sử dụng v-for không đúng cách
    'vue/valid-v-bind': 'error', // Cảnh báo khi sử dụng v-bind không đúng cách
  },
};
