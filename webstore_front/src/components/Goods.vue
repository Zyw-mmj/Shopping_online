<template>
	<div class="products-container">
		<h1 class="products-title">商品展示</h1>
		<div class="search-wrapper">
			<el-form :inline="true" class="search-form">
				<el-form-item label="商品搜索：">
					<el-input v-model="searchKeyword" placeholder="请输入搜索关键字" style="width: 200px;"
						@keyup.enter="searchProduct"></el-input>
				</el-form-item>
				<el-form-item class="search-s">
					<el-button type="primary" icon="el-icon-search" @click="searchProduct">搜索</el-button>
				</el-form-item>
				<el-form-item label="商品排序：" class="sort-wrapper" style="float: right;">
					<el-select v-model="selectedSort" placeholder="请选择排序方式" style="min-width: 120px;"
						@change="filterAndSortProducts">
						<el-option v-for="option in sortOptions" :key="option.value" :value="option.value"
							:label="option.label" />
					</el-select>
				</el-form-item>
				<el-form-item>
					<el-select v-model="selectedCategory" placeholder="请选择商品类别" @change="filterAndSortProducts">
						<el-option v-for="category in transformedCategories" :key="category" :label="category.label"
							:value="category">
						</el-option>
					</el-select>
				</el-form-item>
			</el-form>
		</div>
		<Carousel></Carousel>

		<div v-if="searchResult.length">
			<el-row :gutter="20" class="products-list">
				<el-col :xs="24" :sm="12" :md="8" :lg="6" v-for="product in filteredProducts" :key="product.id">
					<el-card :body-style="{ padding: '0px' }" class="product-card">
						<div class="product-image" :style="{backgroundImage:`url(${product.picture})`}">
							<div class=" product-actions">
								<el-button type="text" size="mini" @click="addToCart(product)">
									<i class="el-icon-shopping-cart"></i> 加入购物车
								</el-button>
								<el-button type="text" size="mini" @click="goToProductDetail(product.id)">
									<i></i> 查看详情
								</el-button>
							</div>
						</div>
						<div class="product-info">
							<h2 class="product-name">{{ product.goodsname }}</h2>
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
	</div>
</template>

<script>
	import axios from 'axios'
import { Message } from 'element-ui';
	import Carousel from './Carousel.vue';
	export default {
		name: "Products",
		components: {
			Carousel
		},
		data() {
			return {
				searchKeyword: '',
				searchResult: [],
				categories: [], // 存储商品类别列表
				selectedCategory: '', // 当前选中的商品类别
				selectedSort: '',
				imageUrls: [],
				sortOptions: [ // 排序方式选项
					{
						label: '价格升序',
						value: 'priceAsc'
					},
					{
						label: '价格降序',
						value: 'priceDesc'
					},
					{
						label: '销量升序',
						value: 'salesAsc'
					},
					{
						label: '销量降序',
						value: 'salesDesc'
					},
				],
			};
		},
		mounted() {
			var that = this;
			this.fetchCategories();
			that.searchProduct().then(() => {
				// 所有异步请求完成后进行处理
				// console.log(that.imageUrls);
			});
		},
		methods: {
			addToCart(product) {
				// let userid = JSON.parse(localStorage.getItem('user')).Userid;
				var x=JSON.parse(localStorage.getItem('user'));
				console.log(x);
				if (x == null) {
					alert('请先登录');
				} else {
					let userid = JSON.parse(localStorage.getItem('user')).Userid;
					const requestData = {
						productId: product.id,
						quantity: 1,
						price: product.price,
						id: userid
					};

					axios.post('http://localhost:8080/Cart/addCartItem', requestData).then(response => {
							console.log('Item added to cart:', response.data);
							Message.success("添加购物车成功！");
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
			fetchCategories() {
				axios.get('http://localhost:8080/Goods/getGoodsType')
					.then(response => {
						console.log(response);
						this.categories = response.data.data;
					})
					.catch(error => {
						console.error('Failed to fetch categories:', error);
					});
			},
			searchProduct() {
				var that = this;
				return new Promise((resolve, reject) => {
					// 发送搜索请求，获取符合条件的商品数据
					axios.get('http://localhost:8080/Goods/searchGoods', {
						params: {
							keyword: that.searchKeyword
						}
					}).then(response => {
						// console.log(response);
						that.searchResult = response.data.data
						console.log("下面时返回的products");
						// console.log(that.searchResult[0].status);
						const products = that.searchResult;
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
								that.searchResult.forEach(item => {
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

				// 	for (let i = 0; i < ids.length; i++) {
				// 		// console.log(ids[i]);
				// 		axios.get('http://localhost:8080/Goods/getGoodsImages', {
				// 			params: {
				// 				id: ids[i]
				// 			},
				// 			responseType: 'arraybuffer'
				// 		}).then(function(response) {
				// 			console.log(response.data)
				// 			const blob = new Blob([response.data], {
				// 				type: 'image/jpeg'
				// 			}); // 将响应数据转换为Blob对象
				// 			const url = URL.createObjectURL(blob); // 创建一个URL地址
				// 			(function(index){
				// 				that.$set(that.imageUrls,index,url);
				// 			})(i);
				// 		}).catch(function(error) {
				// 			console.log(error);
				// 		})
				// 	}
				// }).catch(error => {
				// 	// console.log(that.imageUrls)
				// 	console.log(error)
				// })
			},
			goToProductDetail(id) {
				// 导航到商品详情页，传递商品的唯一标识符
				this.$router.push({
					name: 'productDetail',
					params: {
						id
					}
				});
			},
			getImageStyle(index) {
				const styles = {
					backgroundImage: `url(${this.imageUrls[index]})`,
					// Add more styles as needed
				};
				return styles;
			},
		},
		computed: {
			// filteredProducts() {
			// 	const goods = [...this.searchResult]
			// 	if (this.sortValue === 'price_asc') {
			// 		goods.sort((a, b) => a.price - b.price)
			// 	} else if (this.sortValue === 'price_desc') {
			// 		goods.sort((a, b) => b.price - a.price)
			// 	} else if (this.sortValue === 'rating_desc') {
			// 		goods.sort((a, b) => b.rating - a.rating)
			// 	} else if (this.sortValue === 'sales_desc') {
			// 		goods.sort((a, b) => b.sales - a.sales)
			// 	}
			// 	return goods
			// },
			filteredProducts() {
				// 过滤商品数据
				let filtered = this.searchResult;
				console.log(this);
				if (this.selectedCategory !== '') {
					// 根据选中的商品类别过滤商品数据
					filtered = filtered.filter(product => product.categorie === this
						.selectedCategory);
				}

				// 排序商品数据
				if (this.selectedSort === 'priceAsc') {
					filtered.sort((a, b) => a.price - b.price);
				} else if (this.selectedSort === 'priceDesc') {
					filtered.sort((a, b) => b.price - a.price);
				} else if (this.selectedSort === 'salesAsc') {
					filtered.sort((a, b) => a.sales - b.sales);
				} else if (this.selectedSort === 'salesDesc') {
					filtered.sort((a, b) => b.sales - a.sales);
				}

				return filtered;
			},
			transformedCategories() {
				return this.categories.map(category => {
					if (category === 'shoes') {
						return '鞋类';
					} else if (category === 'clothing') {
						return '服装类';
					} else {
						return category;
					}
				})
			},
		}
	};
</script>
<style scoped>
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



	.search-wrapper {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.sort-wrapper {
		display: flex;
		justify-content: flex-end;
		margin-left: auto;
	}

	.search-form .el-form-item {
		margin-right: 20px;
	}

	.search-item {
		width: 300px;
	}

	.search-s {
		margin-top: 0px;
	}
</style>
