<template>
	<div class="coupon-distribution">
		<h2>优惠券</h2>
		<div v-if="coupons.length === 0">
			<p>暂无可领取的优惠券</p>
		</div>
		<div v-else>
			<ul class="coupon-list">
				<li v-for="coupon in processedCoupons" :key="coupon.couponId" class="coupon-item">
					<div class="coupon-header">
						<h3>{{ coupon.couponName }}</h3>
						<button v-if="!coupon.received" @click="receiveCoupon(coupon.id)">领取优惠券</button>
					</div>
					<div class="coupon-details">
						<p>{{ coupon.couponDesc }}</p>
						<div class="coupon-info">
							<div class="coupon-info-item">
								<span class="info-label">抵扣价格:</span>
								<span class="info-value">{{ coupon.discount }}</span>
							</div>
							<div class="coupon-info-item">
								<span class="info-label">类型:</span>
								<span class="info-value">{{ coupon.couponType }}</span>
							</div>
							<div class="coupon-info-item">
								<span class="info-label">适用商品类型:</span>
								<span class="info-value">{{ coupon.couponLimit }}</span>
							</div>
							<div class="coupon-info-item">
								<span class="info-label">开始时间:</span>
								<span class="info-value">{{ coupon.couponStartTime }}</span>
							</div>
							<div class="coupon-info-item">
								<span class="info-label">截止时间:</span>
								<span class="info-value">{{ coupon.couponEndTime }}</span>
							</div>
							<div class="coupon-info-item">
								<span class="info-label">领取状态:</span>
								<span class="info-value" :class="{ 'isDeleted': coupon.isDeleted}">
									<span class="info-value" :class="{ 'received': coupon.received}">
										<!-- {{ coupon.received ? '已领取' : (coupon.isDeleted ? '已使用' : '未领取') }} -->
										{{ coupon.isDeleted ? '已使用' : ( coupon.received  ? '已领取': '未领取')}}
									</span>
								</span>
							</div>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</div>
</template>

<script>
	import axios from 'axios';
	import {
		Message
	} from 'element-ui';
	export default {
		data() {
			return {
				coupons: [],
				receivedIds: [],
				isUsed: [],
			};
		},
		created() {
			this.loadCoupon();
			this.getReceivedStatus();
		},
		computed: {
			processedCoupons() {
				return this.coupons.map(coupon => ({
					...coupon,
					received: this.receivedIds.includes(coupon.couponId),
					isDeleted: this.isUsed.includes(coupon.couponId)

				}));
			}
		},
		methods: {
			getReceivedStatus() {
				let userId = JSON.parse(localStorage.getItem('user')).Userid;
				axios
					.get(`http://localhost:8080/userCoupon/${userId}`)
					.then((response) => {
						for (const item of response.data) {
							this.receivedIds.push(item.couponId)
							console.log(item);
							if (item.isDeleted === 1) {
								this.isUsed.push(item.couponId)
							}
						}

						console.log(this.receivedIds)
					})
					.catch((error) => {
						console.error(error);
					});


			},
			receiveCoupon(id) {
				let userId = JSON.parse(localStorage.getItem('user')).Userid;
				console.log(userId);
				const url = `http://localhost:8080/userCoupon/`
				const data = {
					userId: userId,
					couponId: id,
				}
				axios.post(url, data)
					.then(response => {
						Message.success("优惠卷领取成功")
						console.log("Payment record successful: " + response.data);
						this.getReceivedStatus();
					})
					.catch(error => {
						console.error("Payment failed:", error);
					});

			},
			loadCoupon() {
				axios
					.get(`http://localhost:8080/Coupon`)
					.then((response) => {
						console.log(response.data)
						this.coupons = response.data;
					})
					.catch((error) => {
						console.error(error);
					});
			}
		}
	}
</script>

<style scoped>
	.coupon-item {
		border: 2px solid #ccc;
		border-radius: 10px;
		background-color: #f5f5f5;
		padding: 20px;
		margin: 10px;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
		height: 100%;
	}

	.coupon-header {
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.coupon-header h3 {
		font-size: 20px;
		font-weight: bold;
		color: #ff6633;
		margin: 0;
	}

	button {
		background-color: #ff6633;
		color: white;
		border: none;
		padding: 8px 12px;
		border-radius: 5px;
		cursor: pointer;
	}

	.coupon-details {
		flex-grow: 1;
		display: flex;
		flex-direction: column;
		justify-content: space-between;
	}

	.coupon-details p {
		font-size: 14px;
		color: #666;
		margin: 0;
	}

	.coupon-info {
		display: flex;
		flex-direction: column;
	}

	.coupon-info-item {
		display: flex;
		align-items: center;
		margin-bottom: 5px;
	}

	.info-label {
		font-weight: bold;
		margin-right: 5px;
	}

	.info-value {
		color: #333;
	}

	.received {
		color: green;
	}

	.coupon-distribution {
		width: 600px;
		margin: 20px auto;
		padding: 20px;
		border: 1px solid #ccc;
	}

	.coupon-list {
		list-style: none;
		padding: 0;
	}
</style>