<template>
	<div class="history-evaluation">
		<el-collapse v-model="activePanel">
			<el-collapse-item v-for="evaluation in evaluations" :key="evaluation.id">
				<template slot="title">
					<div class="evaluation-title">
						<img :src="evaluation.avatar" alt="User Avatar" class="avatar">
						<span class="title-text">{{ evaluation.productName }}</span>
						商品评价：<el-rate :value="evaluation.productRating" :show-text="false" disabled class="rating"></el-rate>
						商家评价：<el-rate :value="evaluation.transactionRating" :show-text="false" disabled class="rating"></el-rate>
					</div>
				</template>
				<div class="evaluation-content">
					<div class="text">商品名：{{ evaluation.goods_name }}</div>
					<div class="text">店铺名：{{ evaluation.name }}</div>
					<div class="text">交易评价：{{ evaluation.transactionFeedback }}</div>
					<div class="text">商品评价：{{ evaluation.productFeedback }}</div>
					<div class="time">{{ formatDate(evaluation.createdDate) }}</div>
				</div>
			</el-collapse-item>
		</el-collapse>
	</div>
</template>

<script>
	import axios from 'axios';

	export default {
		data() {
			return {
				evaluations: [],
				activePanel: [] // 用于记录展开的面板索引
			};
		},
		mounted() {
			this.getUserFeedback();
		},
		methods: {
			formatDate(timestamp) {
				const date = new Date(timestamp);
				const year = date.getFullYear();
				const month = ("0" + (date.getMonth() + 1)).slice(-2);
				const day = ("0" + date.getDate()).slice(-2);
				const hours = ("0" + date.getHours()).slice(-2);
				const minutes = ("0" + date.getMinutes()).slice(-2);
				return `${year}-${month}-${day} ${hours}:${minutes}`;
			},
			getUserFeedback() {
				let userId = JSON.parse(localStorage.getItem('user')).Userid;
				axios
					.get(`http://localhost:8080/Feedback/UsergetFeedback/${userId}`)
					.then(response => {
						console.log(response);
						this.evaluations = response.data.data;
					})
					.catch(error => {
						console.log(error);
					});
			}
		}
	};
</script>

<style scoped>
	.history-evaluation {
		margin-bottom: 20px;
	}

	.evaluation-title {
		display: flex;
		align-items: center;
	}

	.avatar {
		width: 50px;
		height: 50px;
		border-radius: 50%;
		margin-right: 10px;
	}

	.title-text {
		flex-grow: 1;
		font-weight: bold;
	}

	.rating {
		margin-left: 10px;
	}

	.evaluation-content {
		padding: 10px;
		border: 1px solid #ccc;
		border-top: none;
		border-radius: 0 0 4px 4px;
	}

	.text {
		margin-bottom: 10px;
	}

	.time {
		color: #888;
		font-size: 12px;
	}
</style>
