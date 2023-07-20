<template>
	<div class="product-detail">
		<el-row>
			<el-col :span="12">
				<div class="product-image">
					<img :src="imageUrl" alt="Product Image">
				</div>
			</el-col>
			<el-col :span="12">
				<div class="product-details">
					<el-card class="product-title-card">
						<div class="product-title">{{ product.goodsname }}</div>
					</el-card>
					<div class="product-info">
						<p class="product-price">
							价格：
							<span class="price-text">¥{{ product.price }}</span>
						</p>
						<p class="product-sales">
							在售数量：
							<span class="sales-text">{{ product.stock }}</span>
						</p>
						<p class="product-sales">
							销量：
							<span class="sales-text">{{ product.sales }}</span>
						</p>
						<p class="product-rating">
							评分：
							<el-rate :value="product.rating" :show-score="false" :disabled="true" />
						</p>
					</div>
					<el-form class="product-form" label-width="100px" v-if="product">
						<el-form-item label="数量">
							<el-input-number v-model="quantity" :min="1" :max="product.stock" :step="1">
							</el-input-number>
						</el-form-item>
						<el-form-item v-if="product.categorie === '鞋类'" label="鞋码">
							<el-select v-model="selectedSize">
								<el-option v-for="size in shoeSizes" :key="size" :value="size">{{ size }}</el-option>
							</el-select>
						</el-form-item>
						<el-form-item v-if="product.categorie === '服装类'" label="服装尺码">
							<el-select v-model="selectedSize">
								<el-option v-for="size in clothingSizes" :key="size" :value="size">{{ size }}
								</el-option>
							</el-select>
						</el-form-item>
					</el-form>
					<div class="product-actions">
						<el-button class="buy-button" @click="buyProduct" :disabled="!selectedSize">立即购买</el-button>
						<el-button class="add-to-cart-button" @click="addToCart(product)" :disabled="!selectedSize">
							加入购物车</el-button>
					</div>
					<div class="product-description">
						<h3>商品描述：</h3>
						<p>{{ product.describes }}</p>
					</div>
				</div>
			</el-col>
		</el-row>
		<evaluation-form></evaluation-form>
		<FeedbackForm></FeedbackForm>
	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return {
				product: {},
				quantity: 1,
				selectedSize: '',
				imageUrl: '',
				shoeSizes: ['36', '37', '38', '39', '40', '41', '42', '43', '44', '45'],
				clothingSizes: ['S', 'M', 'L', 'XL', 'XXL'],
			};
		},
		mounted() {
			const productId = this.$route.params.id;
			this.getProductData(productId);
		},
		methods: {
			getProductData(productId) {
				axios
					.get(`http://localhost:8080/Goods/getGoodsById/${productId}`)
					.then((response) => {
						console.log(response.data.data);
						this.product = response.data.data;
						return axios.get(`http://localhost:8080/Goods/getGoodsImages2?id=${productId}`, {
							responseType: 'arraybuffer',
						});
					})
					.then((imageResponse) => {
						const arrayBuffer = imageResponse.data;
						const blob = new Blob([arrayBuffer], {
							type: 'image/jpeg'
						});
						this.imageUrl = URL.createObjectURL(blob);

					})
					.catch((error) => {
						console.error(error);
					});
			},
			async buyProduct() {
				if (this.selectedSize) {
					console.log('Buy button clicked');
					console.log('Quantity:', this.quantity);
					console.log('Size:', this.selectedSize);
					console.log("开始");
					let userid = JSON.parse(localStorage.getItem('user')).Userid;
					let total = (this.product.price * this.quantity).toFixed(2);
					console.log(userid);
					console.log(total);
					const orderData = {
						userId: userid,
						totalPrice: parseInt(total),
						payStatus: 0,
						payType: 0,
						orderStatus: 0,
						userName: "zywOo",
						userPhone: "15012340259",
						userAddress: "ojbk St"
					}; //不管
					const config1 = {
						headers: {
							'content-type': 'application/json'
						}
					};

					try {
						const orderResponse = await axios.post('http://localhost:8080/orders', orderData, config1);
						console.log(orderResponse);
						const orderId = orderResponse.data.id;
						console.log("创建订单: " + orderId);

						const data = {
							orderId: orderId,
							goodsId: this.product.id,
							goodsName: this.product.goodsname,
							sellingPrice: this.product.price,
							goodsCount: this.quantity,
						};
						console.log(data);
						const config = {
							headers: {
								'content-type': 'application/json'
							}
						};
						await axios.post('http://localhost:8080/orders/item', data, config)
							.then(response => {
								console.log(response);
							});
						console.log("创建商品订单明细成功");
					} catch (error) {
						console.error(error);
						return console.log("没有正常创建订单");
					}
					this.$router.push('/Order')
				}
			},
			addToCart(product) {
				if (this.selectedSize) {
					console.log('Add to Cart button clicked');
					console.log('Quantity:', this.quantity);
					console.log('Size:', this.selectedSize);
					console.log(product);
					let userid = JSON.parse(localStorage.getItem('user')).Userid;
					const requestData = {
						productId: product.id,
						quantity: this.quantity,
						price: product.price,
						id: userid
					};

					axios.post('http://localhost:8080/Cart/addCartItem', requestData).then(response => {
							console.log('Item added to cart:', response.data);
							this.$message({
								message: '加入购物车成功',
								type: 'success',
							});
							// Handle successful response, e.g., update the cart or show a success message
						})
						.catch(error => {
							console.error('Failed to add item to cart:', error);
							// Handle error, e.g., show an error message
							if (error.response && error.response.status === 401) {
								// 弹出提示窗口告知用户需要先登录
								alert('请先登录');
							}
						});
				}
			},
		},
	};
</script>

<style scoped>
	.product-detail {
		padding: 20px;
		background-color: #fff;
	}

	.product-image img {
		max-width: 100%;
	}

	.product-details {
		padding: 20px;
		border: 1px solid #ddd;
		border-radius: 4px;
		background-color: #fff;
	}

	.product-title-card {
		padding: 20px;
		margin-bottom: 20px;
	}

	.product-title {
		font-size: 24px;
		font-weight: bold;
		margin-bottom: 10px;
	}

	.product-info {
		display: flex;
		justify-content: space-between;
		align-items: center;
		margin-bottom: 10px;
	}

	.product-info p {
		margin: 0;
	}

	.product-price .price-text {
		color: #ff4400;
		font-size: 16px;
		font-weight: bold;
	}

	.product-sales .sales-text {
		font-size: 14px;
		color: #999;
	}

	.product-actions {
		margin-top: 20px;
		display: flex;
		gap: 10px;
	}

	.buy-button,
	.add-to-cart-button {
		flex: 1;
	}

	.product-description {
		margin-top: 40px;
	}

	.product-description h3 {
		font-size: 20px;
		font-weight: bold;
		margin-bottom: 10px;
	}

	.product-description p {
		line-height: 1.5;
	}

	.product-form {
		margin-top: 20px;
	}
</style>
