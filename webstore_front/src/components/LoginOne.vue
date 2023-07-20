<template>
  <div class="login">
    <!-- <el-form ref="loginForm" :model="loginForm" :rules="loginFormRules" class="login-form" :label-position="labelPosition" label-width="60px"> -->
	<el-form size="medium" :inline="true" :label-position="labelPosition" label-width="60px" :model="loginForm" :rules="loginFormRules" ref="loginForm">
      <h3>LOGIN</h3>
      <el-form-item label="账号">
        <el-input v-model="loginForm.username" ></el-input>
      </el-form-item>
	<br>
      <el-form-item label="密码">
        <el-input v-model="loginForm.password" type="password"></el-input>
	</el-form-item>
	<el-form-item label="验证码">
		<el-input v-model="loginForm.captcha" ></el-input>
	</el-form-item>
	<br>
	<img :src="captchaImage" alt="验证码" id="code" @click="refresh">
	<br>
      <el-form-item>
        <el-button type="primary" @click="handleSubmit">登录</el-button>
		<el-button type="info" @click="registerSubmit">注册</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
	import axios from "axios"
export default {
  name: 'LoginOne',
  data() {
    return {
	captchaImage:' ',
      loginForm: {
        username: '',
        password: '',
		captcha: '',
      },
		session:'',
      loginFormRules: {
        username: [
          { required: true, message: 'Please enter your username', trigger: 'blur' },
        ],
        password: [
          { required: true, message: 'Please enter your password', trigger: 'blur' },
        ],
      },
    };
  },
	mounted() {
	// 获取验证码图片的 Base64 编码字符串
	axios.get('http://localhost:8080/Login/getCaptchaImage').then(response => {
		console.log(response)
	this.captchaImage = response.data[0];
	this.session=response.data[1];
	}).catch(error => {
		console.error(error)
		})
    },
  methods: {
	refresh(){
		axios.get('http://localhost:8080/Login/getCaptchaImage').then(response => {
			console.log(response)
		this.captchaImage = response.data[0];
		this.session=response.data[1];
		}).catch(error => {
			console.error(error)
			})
	},
    handleSubmit() {
		var that = this;
      this.$refs.loginForm.validate((valid) => {
        if (valid) {
			axios.post('http://localhost:8080/Login/verify', {
				username: this.loginForm.username,
				password: this.loginForm.password,
				captcha: this.loginForm.captcha,
				session:this.session,
				}).then(res => {
					// 处理登录结果
					console.log(res.data.data);
					if(res.data.data=="密码有误"){
						console.log(res.data.data);
						// console.log("5435");
						this.$message.error('账号或密码有误！请重新输入');
					}else if(res.data.data=="yanzhenma"){
						this.$message.error('验证码有误！请重试');
					}else if( res.data.data=="Rejected"){
						this.$message.error('注册未批准，请联系管理员！');
					}else if(res.data.data[0]=="Approved"){
						let temp = {
							Userid: res.data.data[1],
							account:this.loginForm.username,
							// user: "that.form.user",
							password:this.loginForm.password,
						};
						localStorage.setItem('user', JSON.stringify(temp));
						that.$router.push('/');
						// 刷新页面
						window.location.reload(true);
						this.$message({
							message: '登录成功',
							type: 'success'
							});
					}else if(res.data.data=="notExist"){
						this.$message.error('账号不存在！注意！输入账号而不是姓名');
					}else{
						this.$message.error('请稍后再试！')
					}
					console.error("11111111")
				console.error(res)
				}).catch(error => {
					console.error("243432")
					console.log(error)
				})
		}else{
		console.log('Login form is invalid');
		this.$message({
			showClose: true,
			message: '输入的信息格式不对！',
			type: 'warning'
		});
		return false;
		
        }
      });
    },
	registerSubmit(){
		this.$router.push('/register')
	}
  },
};
</script>

<style scoped>
.login {
    width: 400px;
    height: 300px;
    margin: 0 auto;
    margin-top: 80px;
    margin-bottom: 150px;
    text-align: center;
    padding: 40px;

    box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
    border-radius: 10px;
}

.darg {
    margin: 0 auto;
    margin-bottom: 20px;
}

.button {
    float: right;
    margin-right: 75px !important;
}
</style>