<template>
	<div>
		<div v-if="products.length">
			<el-row :gutter="20" class="products-list">
				<el-col :xs="24" :sm="12" :md="8" :lg="6" v-for="product in products" :key="product.id">
					<el-card :body-style="{ padding: '0px' }" class="product-card">
						<div class="product-image" :style="{backgroundImage:`url(${product.picture})`}">
							<div class=" product-actions">
								<el-button type="text" size="mini" @click="viewProduct(product)">
									<i></i> 查看详情
								</el-button>
							</div>
						</div>
						<div class="product-info">
							<div class="product-info1">
								<h2 class="product-name">{{ product.goodsname }}</h2>
								<div class="product-status">
									<el-tag v-if="product.status === 'Pending'" type="info">待审核</el-tag>
									<el-tag v-if="product.status === 'Off'" type="danger">已下架</el-tag>
									<el-tag v-if="product.status === 'Rejected'" type="warning">未通过</el-tag>
									<el-tag v-if="product.status === 'Approved'" type="success">已发布</el-tag>
								</div>
							</div>
							<p class="product-description">{{ product.describes }}</p>
							<div class="product-price">￥{{ product.price }}</div>
							<div class="product-stats">
								<div class="product-sold">销量：{{ product.sales }}</div>
								<div class="product-rating">
									评分：
									<el-rate :value="product.rating" :show-score="false" :disabled="true" />
								</div>
							</div>
						</div>
					</el-card>
				</el-col>
			</el-row>
			<!-- 			<el-pagination @current-change="handlePageChange" :current-page="currentPage" :page-size="pageSize"
			:total="totalProducts" layout="prev, pager, next"></el-pagination> -->
		</div>
		<div v-else>
			暂无搜索结果
		</div>
		<el-dialog :visible.sync="dialogVisible" width="50%">
			<!-- <el-image :src="selectedProduct.picture" fit="contain"></el-image> -->
			<h3>{{ selectedProduct.name }}</h3>
			<p>{{ selectedProduct.description }}</p>
			<p>库存：{{ selectedProduct.stock }}</p>
			<p>销量：{{ selectedProduct.sales }}</p>
			<p>评分：{{ selectedProduct.rating }}</p>
			<el-button v-if="selectedProduct.status=='Approved'" type="danger" @click="confirmRemoveProduct(selectedProduct)">下架商品</el-button>
			<el-button v-if="selectedProduct.status=='Off'" type="success" @click="confirmReOnProduct(selectedProduct)">重新上架</el-button>
			<el-button type="text" @click="dialogVisible = false">关闭</el-button>
		</el-dialog>
	</div>
</template>

<script>
	import axios from 'axios'
	export default {
		data() {
			return {
				imageUrls: [],
				products: [],
				dialogVisible: false,
				selectedProduct: {}
			};
		},
		mounted() {
			var that = this;
			// this.fetchCategories();
			that.searchProduct().then(() => {
				// 所有异步请求完成后进行处理
				console.log(that.imageUrls);
			});
		},
		methods: {
			searchProduct() {
				var that = this;
				let businessId = JSON.parse(localStorage.getItem('business')).account;
				return new Promise((resolve, reject) => {
					// 发送搜索请求，获取符合条件的商品数据
					axios.get('http://localhost:8080/Goods/getGoodsByBusinessId', {
						params: {
							businessId: businessId
						}
					}).then(response => {
						console.log(response);
						that.products = response.data.data
						console.log("@@@@@@@@@@@@@@@@@");
						console.log(that.products);
						const products = that.products;
						// 使用 map 方法提取商品类型
						// const productTypes = products.map(product => product.type);
						// console.log(this.categories)
						// this.filterProducts();
						const ids = products.map(product => product.id);
						const promises = [];

						for (let i = 0; i < ids.length; i++) {
							const promise = axios.get('http://localhost:8080/Goods/getGoodsImages2', {
								params: {
									id: ids[i]
								},
								responseType: 'arraybuffer'
							}).then(function(response) {
								console.log(response.data);
								const blob = new Blob([response.data], {
									type: 'image/jpeg'
								});
								const url = URL.createObjectURL(blob);
								// that.$set(that.imageUrls, i, url);
								that.products.forEach(item => {
									if (item.id === ids[i]) {
										item.picture = url;
									}
								});
							}).catch(function(error) {
								console.log(error);
							});

							promises.push(promise);
						}

						Promise.all(promises).then(() => {
							resolve();
						}).catch(error => {
							reject(error);
						});
					}).catch(error => {
						// console.log(that.imageUrls);
						console.log(error);
						reject(error);
					});
				});
			},
			viewProduct(product) {
				this.selectedProduct = product;
				this.dialogVisible = true;
			},
			confirmRemoveProduct(product) {
				this.$confirm('确定要下架该商品吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					// 下架商品的逻辑
					axios.post('http://localhost:8080/Goods/offGoods', {
							id: product.id,
						
					}).then(response => {
						console.log(response);
					}).catch(error => {
						// console.log(that.imageUrls);
						console.log(error);
					});
					this.removeProduct(product);
					this.$message({
						type: 'success',
						message: '商品已下架'
					});
					this.dialogVisible = false;
				}).catch(() => {
					// 用户点击取消按钮，无需执行任何操作
				});
			},
			removeProduct(product) {
				// 在这里实现下架商品的逻辑
				// 可以根据具体需求修改products数组或向后端发送请求进行下架操作
				const index = this.products.findIndex(p => p.id === product.id);
				if (index !== -1) {
					// this.products.splice(index, 1);
					this.products[index].status = "Off";
				}
				// product.status == 'Off';
			},
			confirmReOnProduct(product) {
				this.$confirm('确定要重新上架该商品吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					// 下架商品的逻辑
					axios.post('http://localhost:8080/Goods/ReOnGoods', {
							id: product.id,
						
					}).then(response => {
						console.log(response);
					}).catch(error => {
						// console.log(that.imageUrls);
						console.log(error);
					});
					this.ReOnProduct(product);
					this.$message({
						type: 'success',
						message: '商品待管理员审核'
					});
					this.dialogVisible = false;
				}).catch(() => {
					// 用户点击取消按钮，无需执行任何操作
				});
			},
			ReOnProduct(product) {
				// 在这里实现下架商品的逻辑
				// 可以根据具体需求修改products数组或向后端发送请求进行下架操作
				const index = this.products.findIndex(p => p.id === product.id);
				if (index !== -1) {
					// this.products.splice(index, 1);
					this.products[index].status = "Pending";
				}
				// product.status == 'Off';
			}
		}
	};
</script>

<style scoped>
	.product-image {
		width: 100%;
		height: 200px;
		object-fit: cover;
	}

	.product-name {
		font-size: 16px;
		font-weight: bold;
	}

	.product-info {
		margin-right: 10px;
	}

	.product-card {
		max-width: 1200px;
		margin: 20px auto;
		border: none;
		overflow: hidden;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
	}

	.product-image {
		position: relative;
		height: 300px;
		background-size: cover;
		background-position: center center;
		transition: transform 0.2s ease-in-out;
		cursor: pointer;
	}

	.product-image:hover {
		transform: scale(1.05);
	}

	.product-actions {
		position: absolute;
		bottom: 0;
		left: 0;
		width: 100%;
		background-color: rgba(255, 255, 255, 0.8);
		display: flex;
		justify-content: space-between;
		padding: 5px 10px;
		box-sizing: border-box;
	}

	.product-info {
		flex: 1;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		padding: 10px;
		box-sizing: border-box;
	}

	.product-name {
		font-size: 18px;
		font-weight: bold;
		margin-bottom: 10px;
		height: 40px;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}

	.product-description {
		font-size: 14px;
		color: #999;
		margin-bottom: 10px;
		height: 60px;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: normal;
		word-wrap: break-word;
	}

	.product-price {
		font-size: 18px;
		color: #ff6700;
		font-weight: bold;
		margin-bottom: 10px;
	}

	.product-info1 {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.product-name {
		flex-grow: 1;
		/* 其他商品名称样式 */
	}

	.product-status {
		margin-left: 10px;
		/* 其他状态标签样式 */
	}
	.product-stats {
		display: flex;
		justify-content: space-between;
		font-size: 14px;
		color: #999;
	}
	
	.product-sold {
		margin-right: 10px;
	}
	
	.product-rating {
		display: flex;
		align-items: center;
	}
</style>
