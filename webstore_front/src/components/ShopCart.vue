<template>
	<div class="shopCart">
		<h2 class="page-title">商城-购物车</h2>
		<el-table :data="ShopCartItems" ref="table" border @selection-change="updateSelectedItems()">
			<el-table-column type="selection"></el-table-column>
			<!-- <el-table-column prop="shopName" label="店铺名称"></el-table-column> -->
			<el-table-column label="图片"><template slot-scope="scope">
					<img v-if="!imageLoading && scope.row.imageUrl" :src="scope.row.imageUrl" alt="商品图片" width="100"
						height="100">
					<span v-else>加载中...</span>
				</template></el-table-column>

			<el-table-column prop="goodsname" label="商品名称"></el-table-column>
			<el-table-column prop="price" label="单价"></el-table-column>
			<el-table-column prop="quantity" label="数量">
				<template slot-scope="scope">
					<el-input-number v-model="scope.row.quantity" :min="1" :max="5"
						@change="updateItem(scope.row.id,scope.row.quantity)"></el-input-number>
				</template>
			</el-table-column>
			<el-table-column label="小计">
				<template slot-scope="scope">
					{{ scope.row.quantity * scope.row.price * (scope.row.discount || 1) }}元
				</template>
			</el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button prop="id" type="danger" @click="deleteItem(scope.row.id)">
						删除
					</el-button>
				</template>
			</el-table-column>

		</el-table>
		购物车中还剩余<span>{{ itemsTotal }}</span>件商品
		<el-row class="text-right">

			<el-col :span="12" style="font-size:28px;">

				<span>已选择共{{selectedItems.length}}种, 总计{{ itemSelectedTotal }}项商品</span>
			</el-col>
			<el-col :span="12" style="font-size: 30px;">
				合计共需支付（不含运费）：¥<span>{{ totalSelectedPrice }}</span>
				<el-button v-if="itemsTotal > 0" type="primary" @click="goToSettle">
					去结算
				</el-button>
				<el-button v-else disabled>
					去结算
				</el-button>
			</el-col>
		</el-row>

	</div>
</template>

<script>
	import axios from 'axios'
	export default {
		name: "shopCart",
		data() {
			return {
				ShopCartItems: [],
				selectedItems: [],
				imageUrl: '',
				orderData: [],
				// itemsTotal: 0,
				// itemSelectedTotal: 0,
				// totalPrice: 0,
				imageLoading: true // 添加imageLoading属性
			}
		},
		created() {
			this.getCart();
		},
		methods: {
			updateSelectedItems() {
				this.selectedItems = this.$refs.table.selection;
				this.itemSelectedTotal;
				this.totalSelectedPrice = this.getTotalPrice();
			},
			getTotalPrice() {
				let total = 0;
				for (const item of this.selectedItems) {
					console.log("ID000" + item.productId);
					total = total + item.price * item.quantity;
				}
				return total.toFixed(2); // 四舍五入保留两位小数
			},
			// async getMyOrderId() {
			// 	const userId = 19;
			// 	try {
			// 		const response = await axios.get(`http://localhost:8080/orders/user/${userId}`);
			// 		return response.data[response.data.length - 1].id;
			// 	} catch (error) {
			// 		console.error(error);
			// 	}
			// },
			async goToSettle() {
				console.log("开始");
				let userid = JSON.parse(localStorage.getItem('user')).Userid;
				const orderData = {
					userId: userid,
					totalPrice: parseInt(this.totalSelectedPrice),
					payStatus: 0,
					payType: 0,
					orderStatus: 0,
					userName: "zywOo",
					userPhone: "15012340259",
					userAddress: "ojbk St"
				};	//不管
				console.log(orderData);
				console.log(typeof(parseInt(this.totalSelectedPrice)));
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

					for (const item of this.selectedItems) {
						const data = {
							orderId: orderId,
							goodsId: item.productId,
							goodsName: item.goodsname,
							sellingPrice: item.price,
							goodsCount: item.quantity
						};
						console.log(data);
						const config = {
							headers: {
								'content-type': 'application/json'
							}
						};
						await axios.post('http://localhost:8080/orders/item', data, config)
						.then(response=>{
							console.log(response);
						});
						console.log("创建商品订单明细成功");
					}
				} catch (error) {
					console.error(error);
					return console.log("没有正常创建订单");
				}
				this.$router.push('/Order')
			},
			updateItem(id, quantity) {
				console.log(id, quantity)
				axios.put('http://localhost:8080/Cart/updateCartItemNum', null, {
					params: {
						cartId: id,
						quantity: quantity,
					}
				}).then(response => {
					this.getCart();
					this.ShopCartItems = response.data.data;
				}).catch(function(error) {
					console.log(error);
					if (error.response && error.response.status === 401) {
						// 弹出提示窗口告知用户需要先登录
						alert('修改无效，请先登录');
					}

				})
			},

			deleteItem(id) {
				console.log(id)
				axios.delete('http://localhost:8080/Cart/deleteUserCartItem', {
						params: {
							cartId: id
						}
					}).then(response => {
						// 处理删除成功的逻辑
						this.getCart();
						this.ShopCartItems = response.data.data;
						// 可以重新获取购物车数据或更新视图等操作
						const index = this.ShopCartItems.findIndex(item => item.id === id);
						if (index !== -1) {
							this.ShopCartItems.splice(index, 1);
						}
					})
					.catch(error => {
						console.error(error);
						// 处理删除失败的逻辑
						if (error.response && error.response.status === 401) {
							// 弹出提示窗口告知用户需要先登录
							alert('请先登录');
						}
					});
			},
			getCart() {
				// const userId = 24;
				let userId = JSON.parse(localStorage.getItem('user')).Userid;
				axios.get('http://localhost:8080/Cart/getUserCart', {
						params: {
							userId: userId
						}
					}).then(response => {
						this.ShopCartItems = response.data.data;
						console.log(this.ShopCartItems)
						let totalQuantity = 0;
						this.imageLoading = true; // 开始加载图片前将imageLoading设为true
						const promises = this.ShopCartItems.map(item =>
							axios
							.get('http://localhost:8080/Goods/getGoodsImages2', {
								params: {
									id: item.productId
								},
								responseType: 'arraybuffer'
							})
							.then(response => {

								const blob = new Blob([response.data], {
									type: 'image/jpeg'
								});
								const url = URL.createObjectURL(blob);
								item.imageUrl = url;
							})
							.catch(error => {
								console.log(error);
							})
						);
						Promise.all(promises).then(() => {
							this.imageLoading = false;
						});
						for (let i = 0; i < this.ShopCartItems.length; i++) {
							//const productId = this.ShopCartItems[i].productId;
							totalQuantity += this.ShopCartItems[i].quantity;
						}
						this.itemsTotal = totalQuantity;

					})
					.catch(error => {
						console.error(error);
					});

			},

		},
		computed: {
			// 已选中商品折后总价
			totalSelectedPrice: {
				get() {
					return this.getTotalPrice(this.selectedItems);
				},
				set() {}
			},
			// MyOrderId: {
			// 	get() {
			// 		return this.getMyOrderId();
			// 	},
			// 	set() {}
			// },
			// 已选中商品数量
			itemSelectedTotal: {
				get() {
					let totalItems = 0;
					for (const item of this.selectedItems) {

						totalItems += item.quantity;
					}
					return totalItems
				},
				set() {}
			},
			// 所有商品数量
			itemsTotal: {
				get() {
					let total = 0
					for (const item of this.ShopCartItems) {
						total += item.quantity
					}
					return total
				},
				set() {}
			}
		},
	}
</script>

<style scoped>
	.shopCart {
		padding: 20px;
	}

	.page-title {
		margin-bottom: 20px;
	}

	.cart-table {
		border: none;
		margin-bottom: 20px;
	}

	.image-container {
		display: flex;
		align-items: center;
		justify-content: center;
	}

	.loading-placeholder {
		display: flex;
		align-items: center;
		justify-content: center;
		height: 100px;
		color: #999;
	}

	.text-right {
		display: flex;
		justify-content: flex-end;
		align-items: center;
	}

	.settle-button {
		font-size: 16px;
		padding: 10px 20px;
		margin-left: 10px;
	}
</style>