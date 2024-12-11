// module.exports = {
//     presets: [
//       '@vue/cli-plugin-babel/preset',
//     ],
//     plugins: [
//       '@vue/babel-plugin-jsx',
//     ]
//   };
  
module.exports = {
  presets: [
    '@babel/preset-env',
    '@babel/preset-react',  // Nếu bạn đang sử dụng React
    '@vue/babel-preset-jsx'  // Nếu bạn dùng Vue JSX
  ]
};
