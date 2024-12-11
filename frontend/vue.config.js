// vue.config.js
module.exports = {
  devServer: {
      port: 8081, // Chạy frontend trên cổng 8081
      proxy: {
          '/api': {
              target: 'http://localhost:8080', // Backend chạy trên cổng 8080
              changeOrigin: true,
              secure: false,
              pathRewrite: { '^/api': '' }, // Bỏ prefix '/api' nếu backend không sử dụng prefix này
          },
      },
  },
};
