<template>
	<div class="item">
		<h3 style="width: 500px; text-align: center; margin-bottom: 30px;">个人信息</h3>
		<el-form label-width="100px" label-position="left">
			<el-form-item label="头像：">
				<div>
					<el-upload action="//jsonplaceholder.typicode.com/posts/" :http-request="uploadFile"
						:show-file-list="false" :on-success="handleSuccess" :before-upload="beforeUpload"
						:limit="fileLimit">
						<!-- <img :src="imageUrl" class="avatar"> -->
						<!-- <i class="el-icon-plus avatar-uploader-icon"></i> -->
						<div class="avatar-wrapper">
							<img :src="form.touxiang" class="avatar">
							<!-- <div class="icon-wrapper">
							<el-icon class="icon" name="el-icon-camera"></el-icon>
							</div> -->
						</div>
					</el-upload>
				</div>
			</el-form-item>
			<el-form-item label="昵称：">
				<el-input v-model="form.nickname"></el-input>
			</el-form-item>
			<el-form-item label="个人介绍：">
				<el-input v-model="form.introduction"></el-input>
			</el-form-item>
			<el-form-item label="收货地址：">
				<el-input v-model="form.goodsaddress"></el-input>
			</el-form-item>
			<el-form-item label="微信：">
				<el-input v-model="form.wechat"></el-input>
			</el-form-item>
			<!-- <el-form-item label="修改密码：">
                <el-input v-model="form.password" type="password"></el-input>
            </el-form-item> -->
			<el-form-item style="text-align: right;">
				<el-button type="primary" @click="onSave">保存</el-button>
				<el-button type="primary" @click="showDialog">修改密码</el-button>
				<el-button type="danger" @click="onExit">退出登陆</el-button>
			</el-form-item>
		</el-form>
		<el-dialog title="修改密码" :visible.sync="dialogVisible" :close-on-click-modal="false" :show-close="false">
			<el-form ref="form" :model="form2" label-position="left" label-width="100px" :rules="rules">
				<el-form-item label="旧密码" prop="oldPassword">
					<el-input type="password" v-model="form2.oldPassword" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="新密码" prop="newPassword">
					<el-input type="password" v-model="form2.newPassword" autocomplete="off"></el-input>
				</el-form-item>
				<el-form-item label="确认密码" prop="confirmPassword">
					<el-input type="password" v-model="form2.confirmPassword" autocomplete="off"></el-input>
				</el-form-item>
			</el-form>

			<span slot="footer" class="dialog-footer">
				<el-button @click="dialogVisible = false">取消</el-button>
				<el-button type="primary" @click="submitForm">确认</el-button>
			</span>
		</el-dialog>
	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return {
				dialogVisible: false,
				form: {
					nickname: '',
					account: '',
					// password:'',
					touxiang: '',
					introduction: '',
					wechat: '',
					goodsaddress: '',
				},
				form2: {
					oldPassword: '',
					newPassword: '',
					confirmPassword: ''
				},

				fileList: [],
				uploadedFile: null,
				imageUrl: '',
				fileLimit: 10,
				headers: {
					"Content-Type": "multipart/form-data"
				},
				rules: {
					oldPassword: [{
						required: true,
						message: '请输入旧密码',
						trigger: 'blur'
					}],
					newPassword: [{
							required: true,
							message: '请输入新密码',
							trigger: 'blur'
						},
						{
							min: 6,
							message: '新密码长度不能少于6个字符',
							trigger: 'blur'
						}
					],
					confirmPassword: [{
							required: true,
							message: '请再次输入新密码',
							trigger: 'blur'
						},
						{
							validator: this.validateConfirmPassword,
							trigger: 'blur'
						}
					]
				},
			}
		},
		methods: {
			// 保存用户信息
			showDialog() {
				this.dialogVisible = true
			},
			submitForm() {
				this.$refs.form.validate(valid => {
					if (valid) {
						// 执行密码修改的逻辑，如通过API请求发送数据到后端
						console.log('表单验证通过，执行密码修改的逻辑')
						var that = this;
						let account = JSON.parse(localStorage.getItem('user')).account;
						axios.post('http://localhost:8080/Info/updatePassword', {
								account: account,
								oldPassword: this.form2.oldPassword,
								newPassword: this.form2.newPassword,
							})
							.then(function(response) {
								console.log(response.data.data);
								if (response.data.data == true) {
									that.$message({
										message: '修改密码成功',
										type: 'success'
									});
									// alert('保存成功！');
								} else if (response.data.message == "oldPasswordError") {
									that.$message.error('旧密码有误！请重试');
								}
							})
							.catch(function(error) {
								console.log(error);
							});
						this.dialogVisible = false
					} else {
						console.log('表单验证未通过，')
					}
				})
			},
			validateConfirmPassword(rule, value, callback) {
				if (value !== this.form2.newPassword) {
					callback(new Error('两次输入的密码不一致'))
				} else {
					callback()
				}
			},
			onSave() {
				var that = this;
				let account = JSON.parse(localStorage.getItem('user')).account;
				axios.post('http://localhost:8080/Info/updateUserInfo', {
						account: account,
						nickname: this.form.nickname,
						introduction: this.form.introduction,
						wechat: this.form.wechat,
						// password:this.form.password,
						goodsaddress: this.form.goodsaddress
					})
					.then(function(response) {
						console.log(response.data.data);
						if (response.data.data == true) {
							that.$message({
								message: '保存成功',
								type: 'success'
							});
							// alert('保存成功！');
						}
					})
					.catch(function(error) {
						console.log(error);
					});

				// if (this.form.password != '') {
				//     this.$axios.post('/modct_Password', {
				//         ct_UserID: account,
				//         ct_Password: this.form.password
				//     })
				//     .then(function (response) {
				//         console.log(response);
				//     })
				//     .catch(function (error) {
				//         console.log(error);
				//     });
				// }

				// alert('保存成功！');
				// this.form.password = '';
			},
			// 退出登录
			onExit() {
				localStorage.removeItem('user');
				// 跳转到首页
				this.$router.push('/');
				// 刷新页面
				window.location.reload(true);
			},
			uploadFile(item) {
				console.log("!!!!!!!!!");
				// this.$showMessage('文件上传中........')
				//上传文件的需要formdata类型;所以要转
				// console.log("54543254235432");
				let FormDatas = new FormData();
				let account = JSON.parse(localStorage.getItem('user')).account;
				FormDatas.append('file', item.file);
				FormDatas.append('account', account);
				axios.post('http://localhost:8080/image/uploadImage', FormDatas, {
					headers: {
						'Content-Type': 'multipart/form-data' // 设置请求头，指定请求体为 FormData 类型
					}
				}).then(response => {
					console.log(response.data);

				}).catch(error => {
					console.error(error);
				});
			},
			handleSuccess(response, file, fileList) {
				console.log("111111111111");
				console.log(response);
				this.uploadFile = file;
				this.fileList = fileList;
				this.$message.success('上传成功');
			},
			// handleSuccess() {
			// 	this.$message.success('上传成功');
			// },
			beforeUpload(file) {
				let that = this;
				const isJPG = file.type === 'image/jpeg';
				const isPNG = file.type === 'image/png';
				const isLt2M = file.size / 1024 / 1024 < 2;

				if (!isJPG && !isPNG) {
					this.$message.error('上传图片只能是 JPG/PNG 格式!');
					return false;
				}
				if (!isLt2M) {
					this.$message.error('上传图片大小不能超过 2MB!');
					return false;
				}

				const reader = new FileReader();
				reader.readAsDataURL(file);
				reader.onload = () => {
					that.form.touxiang = reader.result;
				};
				return true;
			},
		},
		created() {
			// 获取用户信息
			var that = this;
			var account = JSON.parse(localStorage.getItem('user')).account;
			console.log(account);
			axios.get('http://localhost:8080/Info/getUserInfo', {
					params: {
						account: account
					},
				})
				.then(function(response) {
					console.log(response.data)
					that.form.nickname = response.data.data[0].nickname;
					that.form.introduction = response.data.data[0].introduction;
					that.form.wechat = response.data.data[0].wechat;
					// that.form.password = response.data.data[0].password;
					that.form.touxiang = response.data.data[0].touxiang;
					that.form.goodsaddress = response.data.data[0].goodsaddress;
				})
				.catch(function(error) {
					console.log(error);
				});
			axios.get('http://localhost:8080/Info/getUserImage', {
					params: {
						account: account
					},
					responseType: 'arraybuffer'
				})
				.then(function(response) {
					console.log(response.data)
					const blob = new Blob([response.data], {
						type: 'image/jpeg'
					}); // 将响应数据转换为Blob对象
					const url = URL.createObjectURL(blob); // 创建一个URL地址
					that.form.touxiang = url; // 将URL地址赋值给image数据
				})
				.catch(function(error) {
					console.log(error);
				});

		},
		beforeDestroy() {
			if (this.image) {
				URL.revokeObjectURL(this.image);
			}
		},
	}
</script>

<style scoped>
	.item {
		width: 500px;
		background-color: white;
		padding: 10px 200px 20px;
		box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
		margin: 0 auto;
		margin-bottom: 50px;
		margin-top: 50px;
	}

	.avatar {
		border-radius: 50%;
		width: 100px;
		height: 100px;
	}

	.icon-wrapper {
		position: absolute;
		bottom: 0;
		right: 0;
		background-color: #409EFF;
		border-radius: 50%;
		width: 32px;
		height: 32px;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.zxc {
		left: 0;
	}

	.icon {
		color: #FFFFFF;
	}
</style>
