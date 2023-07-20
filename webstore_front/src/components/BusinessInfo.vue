<template>

	<!-- <div class="item">
		<el-button type="primary" @click="onExit">退出</el-button>
		<h3>商家信息</h3>
		<el-row>
			<el-col :span="12">
				<el-form label-position="top">
					<el-form-item label="商家名称">
						<el-input v-model="business.name" disabled></el-input>
					</el-form-item>
					<el-form-item label="商家等级">
						<el-input v-model="business.grade" disabled></el-input>
					</el-form-item>
					<el-form-item label="邮箱">
						<el-input v-model="business.email" disabled></el-input>
					</el-form-item>
					<el-form-item label="电话号码">
						<el-input v-model="business.phonenumber" disabled></el-input>
					</el-form-item>
				</el-form>
			</el-col>
			<el-col :span="12">
				<el-form label-position="top">
					<el-form-item label="商家类型">
						<el-input v-model="business.shoptype" disabled></el-input>
					</el-form-item>
					<el-form-item label="交易数量">
						<el-input v-model="business.quantity" disabled></el-input>
					</el-form-item>
					<el-form-item label="交易额">
						<el-input v-model="business.amount" disabled></el-input>
					</el-form-item>
					<el-form-item label="钱包">
						<el-input v-model="business.wallet" disabled></el-input>
					</el-form-item>
				</el-form>
			</el-col>
		</el-row>
	</div> -->
	<div class="business-info">
		<h3>商家信息</h3>
		<el-row>
			<el-col :span="20">
				<el-card class="business-card">
					<div slot="header" class="card-header">
						商家基本信息
					</div>
					<div class="card-content">
						<el-form label-width="100px" class="business-form">
							<el-form-item label="商家名称">
								<span class="business-value">{{ business.name }}</span>
							</el-form-item>
							<el-form-item label="商家等级">
								<span class="business-value">{{ business.grade }}</span>
							</el-form-item>
							<el-form-item label="邮箱">
								<span class="business-value">{{ business.email }}</span>
							</el-form-item>
							<el-form-item label="电话号码">
								<span class="business-value">{{ business.phonenumber }}</span>
							</el-form-item>
							<el-form-item label="商家类型">
								<span class="business-value">{{ business.shoptype }}</span>
							</el-form-item>
						</el-form>
					</div>
				</el-card>
			</el-col>
			<el-col :span="20">
				<el-card class="business-card">
					<div slot="header" class="card-header">
						商家统计信息
					</div>
					<div class="card-content">
						<el-form label-width="100px" class="business-form">
							<el-form-item label="交易数量">
								<span class="business-value">{{ business.quantity }}</span>
							</el-form-item>
							<el-form-item label="交易额">
								<span class="business-value">{{ business.amount }}</span>
							</el-form-item>
							<el-form-item label="钱包">
								<span class="business-value">{{ business.wallet }}</span>
							</el-form-item>
						</el-form>
					</div>
				</el-card>
			</el-col>
		</el-row>
		<el-button type="primary" @click="onExit">退出</el-button>
	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return {
				business: {
					name: '',
					grade: '',
					email: '',
					phonenumber: '',
					shoptype: '',
					quantity: '',
					amount: '',
					wallet: ''
				}

			}
		},
		methods: {
			onExit() {
				localStorage.removeItem('business');
				// 跳转到首页
				this.$router.push('/business');
				// 刷新页面
				window.location.reload(true);
			},
		},
		created() {
			// 获取用户信息
			var that = this;
			var id = JSON.parse(localStorage.getItem('business')).id;
			console.log(id);
			axios.get('http://localhost:8080/Business/getBusinessInfo', {
					params: {
						id: id
					},
				})
				.then(function(response) {
					console.log(response.data)
					const data = response.data.data;
					that.business.name = data.name;
					that.business.grade = data.grade;
					that.business.email = data.email;
					that.business.phonenumber = data.phonenumber;
					that.business.shoptype = data.shoptype;
					that.business.quantity = data.quantity;
					that.business.amount = data.amount;
					that.business.wallet = data.wallet;
					// that.form.nickname = response.data.data[0].nickname;
					// that.form.introduction = response.data.data[0].introduction;
					// that.form.wechat = response.data.data[0].wechat;
					// // that.form.password = response.data.data[0].password;
					// that.form.touxiang = response.data.data[0].touxiang;
					// that.form.goodsaddress = response.data.data[0].goodsaddress;
				})
				.catch(function(error) {
					console.log(error);
				});
			// axios.get('http://localhost:8080/Info/getUserImage', {
			// 	params: {
			// 		account:account
			// 	},
			// 	responseType: 'arraybuffer'
			// })
			// .then(function (response) {
			// 	console.log(response.data)
			// 	const blob = new Blob([response.data], {type: 'image/jpeg'}); // 将响应数据转换为Blob对象
			// 	const url = URL.createObjectURL(blob); // 创建一个URL地址
			// 	that.form.touxiang = url; // 将URL地址赋值给image数据
			// })
			// .catch(function (error) {
			// 	console.log(error);
			// });

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

	.business-info {
		width: 700px;
		background-color: white;
		padding: 10px 200px 20px;
		box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);
		margin: 0 auto;
		margin-bottom: 50px;
		/* margin-top: 50px; */
		margin-top: 30px;
	}

	.business-card {
		margin-bottom: 20px;
	}

	.card-header {
		background-color: #f5f7fa;
		padding: 10px;
	}

	.card-content {
		padding: 10px;
	}

	.business-form {
		margin-bottom: 20px;
	}

	.business-value {
		font-weight: bold;
	}
</style>
