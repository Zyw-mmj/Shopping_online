const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave: false
})
module.exports = {
    devServer: {
        port: 8081 // 此处修改你想要的端口号

    }

}
