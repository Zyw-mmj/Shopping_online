module.exports = {
  root: true,
  env: {
    node: true
  },
  'extends': [
    'plugin:vue/essential',
    'eslint:recommended'
  ],
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'error' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'error' : 'off',
	// 关闭名称校验
	'vue/multi-word-component-names': "off" 
  },
  // parserOptions: {
  //   parser: 'babel-eslint'
  // }
}
