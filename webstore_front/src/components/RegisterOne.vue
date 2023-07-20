<template>
	<div class="register">
  <el-form size="medium" :inline="true" :label-position="labelPosition" label-width="120px"  :model="form" :rules="rules" ref="form" >
    <el-form-item label="姓名" prop="name">
      <el-input v-model="form.name"></el-input>
    </el-form-item>
    <el-form-item label="账号" prop="account">
      <el-input v-model="form.account"></el-input>
    </el-form-item>
    <el-form-item label="密码" prop="password" >
      <el-input type="password" v-model="form.password"></el-input>
    </el-form-item>
    <el-form-item label="确认密码" prop="confirmPassword" >
      <el-input type="password" v-model="form.confirmPassword"></el-input>
    </el-form-item>
    <el-form-item label="手机号" prop="phonenumber">
      <el-input v-model="form.phonenumber"></el-input>
    </el-form-item>
    <el-form-item label="邮箱" prop="email">
      <el-input v-model="form.email"></el-input>
    </el-form-item>
    <el-form-item label="城市" prop="city">
        <el-input v-model="form.city"></el-input>
    </el-form-item>
    <el-form-item label="性别" prop="gender">
          <el-radio-group v-model="form.gender">
            <el-radio :label="1" value="男" >男</el-radio>
            <el-radio :label="2" value="女">女</el-radio>
          </el-radio-group>
    </el-form-item>
    <el-form-item label="银行账号" prop="bankaccount">
      <el-input v-model="form.bankaccount"></el-input>
    </el-form-item>
    <el-form-item label="用户类型" prop="shoptype">
        <el-select v-model="form.shoptype" placeholder="请选择">
            <el-option label="个体用户" value="个体用户"></el-option>
            <el-option label="商家用户" value="商家用户"></el-option>
         </el-select>
    </el-form-item>
	<el-form-item label="验证码" prop="captcha">
		<el-input v-model="form.captcha" ></el-input>
	</el-form-item>
	<br>
	<img :src="captchaImage" alt="验证码" id="code" @click="refresh">
	<br>
	<br>
    <el-form-item>
          <el-button type="primary" @click="submitForm">注册</el-button>
          <el-button @click="resetForm">重置</el-button>
        </el-form-item>
  </el-form>
  </div>
</template>

<script>
	import axios from "axios"
export default {
  data() {
    return {
		captchaImage:' ',
		session:'',
      form: {
        name: '',
        account: '',
        password: '',
        phonenumber: '',
        email: '',
        city: '',
        gender: '',
        bankaccount: '',
        shoptype: '',
		confirmPassword: '',
		captcha:''
      },
	
      rules: {	
        name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
        account: [{ required: true, message: '请输入账号', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        confirmPassword: [{ required: true, message: '请再次输入密码', trigger: 'blur' }, { validator: this.validateConfirmPassword, trigger: 'blur' }],
		mobile: [{ required: true, message: '请输入手机号', trigger: 'blur' }, { validator: this.validateMobile, trigger: 'blur' }],
		email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }, { type: 'email', message: '请输入正确的邮箱格式', trigger: 'blur' }],
		city: [{ required: true, message: '请选择城市', trigger: 'blur' }],
		gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
		bankaccount: [{ required: true, message: '请输入银行账号', trigger: 'blur' }, { validator: this.validateBankAccount, trigger: 'blur' }],
		shoptype: [{ required: true, message: '请选择用户类型', trigger: 'change' }],
		captcha: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
			},
		};
		},
		mounted() {
		// 获取验证码图片的 Base64 编码字符串
		axios.get('http://localhost:8080/Register/getCaptchaImage').then(response => {
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
		submitForm() {
		this.$refs.form.validate((valid) => {
			if (valid) {
				axios.post('http://localhost:8080/Register/verify', {
					name: this.form.name,
					account:this.form.account,
					password:this.form.password,
					phone:this.form.phonenumber,
					email:this.form.email,
					city:this.form.city,
					gender:this.form.gender==1?'男':'女',
					bankAccount:this.form.bankaccount,
					shopType:this.form.shoptype,
					captcha:this.form.captcha,
					session:this.session,
					}).then(res => {
						// 处理登录结果
						if(res.data.data==false){
							console.log(res.data);
							this.$message.error('注册失败！请稍后再试');
						}else if(res.data.data=="yanzhenma"){
							this.$message.error('验证码有误！请重试');
						}else if(res.data.data=="zhanghao"){
							this.$message.error('账号已存在，请换一个账号');
						}else{
							this.$message({
								message: '注册成功！',
								type: 'success'
								});
							this.$router.push('/Login')
						}
					console.error(res)
					}).catch(error => {
					console.error(error)
					})
			} else {
				console.log('校验失败');
				return false;}
			});
		},
		resetForm() {
		this.$refs.form.resetFields();
		},
		validateConfirmPassword(rule, value, callback) {
		if (value !== this.form.password) {
		callback(new Error('两次输入的密码不一致'));
		} else {
		callback();
			}
		},
		validateMobile(rule, value, callback) {
		const mobileReg = /^1[3456789]\d{9}$/;
		if (!mobileReg.test(value)) {
			callback(new Error('请输入正确的手机号码'));
		} else {
			callback();
			}
		},
		validateBankAccount(rule, value, callback) {
		const bankAccountReg = /^\d{16}$/;
		if (!bankAccountReg.test(value)) {
			callback(new Error('请输入16位数字的银行账号'));
		} else {
			callback();
				}
			},
		},
	};
</script>
<style scoped>
.register {
    width: 400px;
    height: 800px;
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