<template>
	<div class="product-form">
		<h2>发布商品信息</h2>
		<el-form ref="form" :model="product" label-width="80px" :rules="rules">
			<el-form-item label="商品名字" prop="name">
				<el-input v-model="product.name"></el-input>
			</el-form-item>
			<el-form-item label="商品类型" prop="type">
				<el-select v-model="product.type" placeholder="请选择商品类型">
					<el-option label="鞋类" value="鞋类"></el-option>
					<el-option label="服装类" value="服装类"></el-option>
					<el-option label="图书" value="图书"></el-option>
				</el-select>
			</el-form-item>
			<el-form-item v-if="product.type === '鞋类'" label="尺码" prop="size">
				<el-input v-model="product.size"></el-input>
			</el-form-item>
			<el-form-item v-if="product.type === '服装类'" label="尺码" prop="size">
				<el-radio-group v-model="product.size">
					<el-radio label="X"></el-radio>
					<el-radio label="M"></el-radio>
					<el-radio label="L"></el-radio>
					<el-radio label="XL"></el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item label="商品图片" prop="images">
				<el-upload class="upload-demo" :action="null" :file-list="product.images" :auto-upload="false"
					list-type="picture-card" :limit="1" :on-preview="handlePreview" :on-remove="handleRemove"
					:on-change="imgBroadcastChange" :onSuccess="uploadSuccess">
					<!-- <div v-if="file.length==0"> -->
					<i class="el-icon-plus"></i>
					<!-- </div> -->
					<!-- <el-button size="small" type="primary">点击上传</el-button> -->
				</el-upload>
			</el-form-item>
			<el-form-item label="商品介绍" prop="description">
				<el-input type="textarea" v-model="product.description"></el-input>
			</el-form-item>
			<el-form-item label="商品数量" prop="quantity">
				<el-input-number v-model="product.quantity" :min="0"></el-input-number>
			</el-form-item>
			<el-form-item label="商品价格" prop="price">
				<el-input-number v-model="product.price" :precision="2" :min="0"></el-input-number>
			</el-form-item>
			<el-form-item label="交易方式" prop="paymentMethods">
				<el-radio-group v-model="product.paymentMethods">
					<el-radio label="快递交易"></el-radio>
					<el-radio label="线下交易"></el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="submitForm" :disabled="!canPublishProduct">发布</el-button>
				<p v-if="!canPublishProduct">该时间段暂时不允许发布新商品，请稍后再试。</p>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return {
				product: {
					name: '',
					type: '',
					size: '',
					images: [],
					description: '',
					quantity: 0,
					price: 0,
					paymentMethods: ''
				},
				canPublishProduct: true,
				file: '',
				rules: {
					name: [{
							required: true,
							message: '请输入商品名字',
							trigger: 'blur'
						},
						{
							max: 20,
							message: '商品名字不能超过20个字',
							trigger: 'blur'
						}
					],
					type: [{
						required: true,
						message: '请选择商品类型',
						trigger: 'change'
					}],
					size: [{
						required: true,
						message: '请选择尺码',
						trigger: 'change'
					}],
					// images: [
					//   { required: true, message: '请上传商品图片', trigger: 'change' }
					// ],
					description: [{
						required: true,
						message: '请输入商品介绍',
						trigger: 'blur'
					}],
					quantity: [{
							required: true,
							message: '请输入商品数量',
							trigger: 'blur'
						},
						{
							type: 'number',
							message: '商品数量必须为数字',
							trigger: 'blur'
						},
						{
							validator: this.validateQuantity,
							trigger: 'blur'
						}
					],
					price: [{
							required: true,
							message: '请输入商品价格',
							trigger: 'blur'
						},
						{
							type: 'number',
							message: '商品价格必须为数字',
							trigger: 'blur'
						},
						{
							validator: this.validatePrice,
							trigger: 'blur'
						}
					],
					paymentMethods: [{
						required: true,
						message: '请选择交易方式',
						trigger: 'change'
					}]
				}
			};
		},
		mounted() {
			this.checkPublishPermission();
		},
		methods: {
			checkPublishPermission() {
				// 发起API请求来检查商家是否允许发布新商品
				let businessId = JSON.parse(localStorage.getItem('business')).id;
				console.log(businessId);
				axios.post('http://localhost:8080/Admin/checkPublishPermission', null, {
						params: {
							merchantId: businessId,
						}
					}).then(response => {
						// console.log(response.data);
						this.canPublishProduct = response.data;
						console.log(this.canPublishProduct);
					})
					.catch(error => {
						console.log(error);
					});
			},

			imgBroadcastChange(file) {
				// debugger
				this.file = file.raw;
			},

			handlePreview(file) {
				// 图片预览
				console.log(file);
			},
			handleRemove(file, fileList) {
				// 移除图片
				const index = fileList.indexOf(file);
				if (index > -1) {
					fileList.splice(index, 1);
				}
			},
			handleUploadSuccess(response, file, fileList) {
				// 上传成功后的回调函数
				this.product.images = fileList;
			},
			beforeUpload(file) {
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
					this.imageUrl = reader.result;
				};
				return true;
			},
			submitForm() {
				// 提交表单的处理函数，可以将商品信息发送到后端进行保存等操作
				this.$refs.form.validate((valid) => {
					if (valid) {
						console.log("!!!!!!!!!");
						let FormDatas = new FormData();
						// let account = JSON.parse(localStorage.getItem('user')).account;
						let businessId = JSON.parse(localStorage.getItem('business')).account;
						FormDatas.append('file', this.file);
						FormDatas.append('name', this.product.name);
						FormDatas.append('type', this.product.type);
						FormDatas.append('size', this.product.size);
						FormDatas.append('description', this.product.description);
						FormDatas.append('quantity', this.product.quantity);
						FormDatas.append('price', this.product.price);
						FormDatas.append('paymentMethods', this.product.paymentMethods);
						FormDatas.append('businessId', businessId);
						axios.post('http://localhost:8080/Goods/addGoods', FormDatas, {
							headers: {
								'Content-Type': 'multipart/form-data' // 设置请求头，指定请求体为 FormData 类型
							}
						}).then(response => {
							console.log(response.data);
							if (response.data.data == "1") {
								this.$message({
									message: '发布成功！等待审核———',
									type: 'success'
								});
								this.$router.push('/business')
							} else {
								this.$message.error('发布失败');
							}
						}).catch(error => {
							console.error(error);
							this.$message.error('发布失败');
						});
					} else {
						// 表单验证不通过，给出相应的提示或处理
					}
				});
			},
			validateQuantity(rule, value, callback) {
				if (value < 0) {
					callback(new Error('商品数量不能小于零'));
				} else {
					callback();
				}
			},
			validatePrice(rule, value, callback) {
				if (value < 0) {
					callback(new Error('商品价格不能小于零'));
				} else {
					callback();
				}
			}
		}
	};
</script>

<style>
	.product-form {
		border: 1px solid #ccc;
		padding: 20px;
		width: 400px;
	}

	.uploaded-image {
		max-width: 200px;
		max-height: 200px;
		margin-top: 10px;
	}

	.product-form {
		background-color: #f5f5f5;
		border: 1px solid #ebebeb;
		padding: 20px;
		width: 400px;
		border-radius: 4px;
		box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
	}

	.product-form h2 {
		font-size: 20px;
		color: #333;
		margin-bottom: 20px;
	}

	.form-title {
		font-size: 20px;
		color: #333;
		margin-bottom: 20px;
	}

	.product-form .el-form-item {
		margin-bottom: 20px;
	}

	.product-form .el-input {
		width: 100%;
	}

	.product-form .el-select {
		width: 100%;
	}

	.product-form .el-button {
		width: 100%;
		margin-top: 20px;
	}

	.product-form .el-upload {
		display: inline-block;
	}

	.product-form .uploaded-image {
		max-width: 200px;
		max-height: 200px;
		margin-top: 10px;
	}
</style>
