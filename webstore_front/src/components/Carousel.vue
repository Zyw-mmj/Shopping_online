<template>
	<div class="product-carousel">
		<el-carousel :autoplay="true" :interval="4000">
			<el-carousel-item v-for="product in products" :key="product.id">
				<img :src="product.picture" alt="Product Image" class="carousel-image">
				<div class="carousel-content">
					<h3>{{ product.goodsname }}</h3>
					<p>{{ product.describes }}</p>
					<span>Price: {{ product.price }}</span>
				</div>
			</el-carousel-item>
		</el-carousel>
	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return {
				products: [],
				imageUrls: [],
			};
		},
		mounted() {
			var that = this;
			that.fetchCarouselData().then(() => {
				// 所有异步请求完成后进行处理
				const carouselData = this.products;
				console.log(carouselData); 
				
			});
		},
		methods: {
			fetchCarouselData() {
				// axios.get('http://localhost:8080/Admin/getCarouselData').
				// then(response => {
				// 	console.log("sdasdasdas");
				// 	console.log(response);
				// 	this.products = response.data.data;

				// 	const products = this.products;
				// 	const ids = products.map(product => product.id);
				// 	const promises = [];
				var that = this;
				return new Promise((resolve, reject) => {
					// 发送搜索请求，获取符合条件的商品数据
					axios.get('http://localhost:8080/Admin/getCarouselData')
						.then(response => {
							// console.log(response);
							that.products = response.data.data
							console.log("下面时返回的products");
							const products = that.products;
							const ids = products.map(product => product.id);
							const promises = [];
							console.log(ids);
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
									// console.log(url);
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
			getImageStyle(index) {
				const styles = {
					backgroundImage: `url(${this.imageUrls[index]})`,
					// Add more styles as needed
				};
				return styles;
			},
		}
	};
</script>

<style scoped>
	.carousel-image {
		width: 100%;
		height: 300px;
		object-fit: cover;
	}

	.carousel-content {
		background-color: #fff;
		padding: 20px;
		text-align: center;
	}

	.product-image {
		width: 100%;
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
</style>
