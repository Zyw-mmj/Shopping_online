<template>
	<div class="order">
		<h2 class="page-title">待支付订单信息</h2>
		<h3 class="page-orderNO">订单NO.{{orderNO}}</h3>
		<el-table :data="orderItems" border>
			<el-table-column prop="goods_name" label="商品名称"></el-table-column>
			<el-table-column prop="selling_price" label="单价"></el-table-column>
			<el-table-column prop="goods_count" label="数量"></el-table-column>
			<el-table-column label="小计">
				<template slot-scope="{ row }">
					{{ row.selling_price * row.goods_count }}
				</template>
			</el-table-column>
			<el-table-column prop="coupon" label="优惠券">
				<template slot-scope="{ row }">
					<el-select v-model="row.coupon" placeholder="请选择" value-key="id" @change="handleCouponChange(row)">
						<el-option label="不使用" value={}></el-option>
						<el-option v-for="coupons in filteredUserCoupons(row)" :key="coupons.id"
							:label="coupons.couponName" :value="coupons">{{coupons.couponName}}
							抵扣金额：￥{{coupons.discount}}</el-option>
					</el-select>
				</template>
			</el-table-column>
			<el-table-column label="实付价格" prop="priceTotal">
				<template slot-scope="{ row }">
					{{ row.selling_price * row.goods_count - Number(row.coupon.discount?row.coupon.discount:0) }}
				</template>
			</el-table-column>
		</el-table>

		<!-- 		<h3 class="section-title">交易方式</h3>
		<el-radio-group v-model="transactionMethod">
			<el-radio label="express">快递</el-radio>
			<el-radio label="offline">线下交易</el-radio>
		</el-radio-group> -->

		<h3 class="section-title">支付方式</h3>
		<el-radio-group v-model="payMethod">
			<el-radio label="useAccount">账户余额抵扣</el-radio>
			<el-radio label="useWechat" disabled>微信支付</el-radio>
			<el-radio label="useAlipay" disabled>支付宝</el-radio>
		</el-radio-group>

		<h3 class="section-title">您的账户余额</h3>
		<div class="account-balance">{{ accountBalance }}</div>

		<template>
			<h3 class="section-title" @change="handleGeneralChange">平台通用红包</h3>
			<div>
				<el-select v-model="general" placeholder="请选择" value-key="id">
					<el-option label="不使用" value={}></el-option>
					<el-option v-for="generals in generateUserCoupons" :key="generals.id"
						:label="getOptionLabel(generals)" :value="generals">{{generals.couponName}}
						抵扣金额：￥{{generals.discount}}</el-option>
				</el-select>
			</div>
		</template>

		<h3 class="section-title">积分抵扣(剩余积分：{{leftPonints}})</h3>
		<el-input-number v-model="deductPoints" :min="0" :max="leftPonints"></el-input-number>

		<h3 class="section-title">订单实际支付{{isdelivery}}</h3>
		<div class="account-balance">￥{{ accountCost }}</div>

		<el-button type="primary" @click="placeOrder">下单</el-button>
		<el-button type="warning" @click="showConfirm">取消订单</el-button>
		<!-- <el-button type="warning" @click="recordCouponUsed">测试</el-button> -->
	</div>
</template>

<script>
	import axios from 'axios';
	import {
		Message
	} from 'element-ui';
	export default {
		name: 'Order',
		data() {
			return {
				selectedRow: null,
				payMethod: "useAccount",
				orderItems: [],
				transactionMethod: '',
				accountBalance: 0,
				deductPoints: 0,
				leftPonints: 10,
				deliveryFee: 15,
				isdelivery: "",
				orderNO: "",
				orderId: "",
				discount: 0,
				general: [],
				itemprice: 0,
				receivedIds: [],
				userCoupons: [],
				usercouponRecord: [],
				usedCoupons: [],
				generals: '',
			};
		},
		created() {
			this.getOrderIdAndNO();
			this.getAccountBalance();

		},
		computed: {
			accountCost: {
				get() {
					let all = this.getTotalPrice() - this.totalDiscount - this.handleGeneralChange()
					return all.toFixed(2)
				},
				set() {}
			},
			totalDiscount() {
				return this.orderItems.reduce((total, item) => {
					return total + (item.coupon.discount || 0); // 将每个订单项的优惠券抵扣金额累加
				}, 0);
			},
			filteredUserCoupons() {
				return (row) => {
					return this.userCoupons.filter(coupon => coupon.couponLimit === 1 && coupon.discount <= (row
						.selling_price * row.goods_count));
				};
			},
			generateUserCoupons() {
				return this.userCoupons.filter(coupon => coupon.couponLimit === 0);
			},
		},

		methods: {
			handleCouponChange(row) {
				const selectedCoupon = row.coupon;
				this.orderItems.forEach(item => {
					if (item !== row && item.coupon.id === selectedCoupon.id) {
						// item.coupon= null;= {
						// 	id: 0,
						// 	discount: 0
						// };
						item.coupon = null;
					}
				});
			},
			getOptionLabel(generals) {
				// console.log("VVVVVVVVVVVVVgenerals.couponNameVVVVVVVVVVVVVVVVV")
				// console.log(generals.couponName)
				return generals.couponName;
			},
			totalusedCouponIds() {
				for (let item of this.orderItems) {
					if (item.coupon.discount !== null && this.coupon != 0) {
						this.usedCoupons.push(item.coupon.id)
					}
					if (this.general.discount !== null && this.general != 0) {
						this.usedCoupons.push(this.general.id)
					}
				}
				// console.log("VVVVVVVVVVVVVVVVVVVusedCouponsVVVVVVVVVVVVVVVVVVV")
				// console.log(this.usedCoupons)
			},
			recordCouponUsed() {
				this.totalusedCouponIds();
				console.log("usercouponRecord:", this.usercouponRecord);
				console.log("usedCoupons:", this.usedCoupons);
				let couponUserIds = this.usercouponRecord
					.filter(record => this.usedCoupons.includes(record.couponId))
					.map(record => record.couponUserId);

				console.log("couponUserIds:", couponUserIds);
				console.log(couponUserIds)
				for (let cui of couponUserIds) {
					axios
						.put(`http://localhost:8080/userCoupon/${cui}/isDeleted`)
						.then((response) => {

							console.log(response.data)
						})
						.catch((error) => {
							console.error(error);
						});
				}
			},
			handleGeneralChange() {

				if (this.general.discount != null && this.general != 0) {
					console.log(this.general)
					return this.general.discount;
				} else {
					this.usedCoupons = [];
					return 0;
				}
			},
			getCoupons() {
				let userId = JSON.parse(localStorage.getItem('user')).Userid;
				axios
					.get(`http://localhost:8080/userCoupon/${userId}`)
					.then((response) => {
						this.usercouponRecord = response.data;
						this.usercouponRecord = this.usercouponRecord.filter(record => record.isDeleted === 0);
						for (let item of this.usercouponRecord)
							this.receivedIds.push(item.couponId)
						console.log(this.receivedIds)
						for (let i = 0; i < this.receivedIds.length; i++) {
							let id = this.receivedIds[i]

							axios
								.get(`http://localhost:8080/Coupon/${id}`)
								.then((response) => {
									this.userCoupons.push(response.data)
									console.log(this.userCoupons)
								})
								.catch((error) => {
									console.error(error);
								});
						}
					})
					.catch((error) => {
						console.error(error);
					});

			},
			showConfirm() {
				this.$confirm('确认取消订单吗？', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning',
				}).then(() => {
					this.cancelOrder();
				}).catch(() => {
					// 用户点击取消，不执行任何操作
				});
			},
			cancelOrder() {
				const orderId = this.orderId;
				const url = `http://localhost:8080/orders/${orderId}`;
				axios.delete(url).then(() => {
					this.$message({
						message: '订单取消成功',
						type: 'success',
					});
					this.$router.go(-1);
				}).catch((error) => {
					this.$message({
						message: '订单取消失败，' + error.response.data.message,
						type: 'error',
					});
				});
			},
			getItemPrice(row) {
				let total = 0;
				total = row.selling_price * row.goods_count;
				total -= this.discount;
				this.itemprice = total
				return total.toFixed(2);
			},
			getTotalPrice() {
				let total = 0;
				for (const item of this.orderItems) {
					total += item.selling_price * item.goods_count;
				}
				total -= this.deductPoints / 100;
				if (this.transactionMethod === 'express') {
					total += this.deliveryFee;
					this.isdelivery = "(已包含快递费用" + this.deliveryFee + "元)";
				}
				return total.toFixed(2); // 四舍五入保留两位小数
			},
			getOrderIdAndNO() {
				// const userId = 19;
				let userId = JSON.parse(localStorage.getItem('user')).Userid;
				console.log(userId);
				axios.get(`http://localhost:8080/orders/user/${userId}`)
					.then((response) => {
						// this.orderNO = response.data[response.data.length - 1].orderNo;
						// this.orderId =  response.data[response.data.length - 1].id;
						for (let i = response.data.length - 1; i >= 0; --i) {
							if (response.data[i].pay_status == 0) {
								//this.orderNO = response.data[i].order_no;
								this.orderNO = response.data[i].id;
								this.orderId = response.data[i].id;
								this.getOrderItems();
								if (this.orderId != "") {
									break;
								}
							}
						}
						if (this.orderId == null)
							Message.success("暂无待支付的订单");
						console.log(this.orderId);
						this.getOrderItems(this.orderId);
						this.getCoupons();
					})
					.catch((error) => {
						console.error(error);
					});

			},
			getOrderItems(orderId) {
				axios.get(`http://localhost:8080/orders/${orderId}/items`)
					.then((response) => {
						console.log(response);
						var l = response.data.length;
						var i = 0;
						var items = response.data
						for (; i < l; i++) {
							items[i] = {
								...items[i],
								coupon: '',
								priceTotal: items[i].selling_price * items[i].goods_count
							};
						}
						this.orderItems = items;
					})
					.catch((error) => {
						console.error(error);
					});
			},
			getAccountBalance() {
				let userId = JSON.parse(localStorage.getItem('user')).Userid;
				axios
					.get(`http://localhost:8080/userWallet/${userId}`)
					.then((response) => {
						this.accountBalance = response.data.balance;
						this.leftPonints = response.data.points;
					})
					.catch((error) => {
						console.error(error);
					});
			},
			updatePayPrice() {
				// for (const item of this.orderItems) {
				// 	console.log(item)
				// 	const itemId = item.order_item_id
				// 	console.log(typeof(itemId))
				// 	var totalprice = 0;
				// 	if (this.selectedRow) {
				// 		totalprice = this.selectedRow.itemtotalprice;
				// 		console.log(totalprice)
				// 	}


				// 	const url = `http://localhost:8080/orders/${itemId}/totalprice/${totalprice}`;
				// 	axios.put(url)
				// 		.catch(error => {
				// 			console.error("Failed to update order status:", error);
				// 		});
				// }
				for (const item of this.orderItems) {
					console.log("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&")
					console.log(item)
					const itemId = item.order_item_id
					console.log(typeof(itemId))
					var totalprice = 0;
					if (typeof(item.coupon.discount)===Number ) {
						totalprice = item.selling_price * item.goods_count - Number(item.coupon.discount);
						console.log("NOTNULL",totalprice)
					} else {
						console.log("NULL",totalprice)
						totalprice = item.selling_price * item.goods_count
					}
					console.log(totalprice)
					// if (this.selectedRow) {
					//  // totalprice = this.selectedRow.itemtotalprice;
					//  item.selling_price * item.goods_count - Number(item.coupon)
					// }

					console.log(totalprice)
					const url = `http://localhost:8080/orders/${itemId}/totalprice/${totalprice}`;
					axios.put(url)
						.catch(error => {
							console.error("Failed to update order status:", error);
						});
				}
			},
			placeOrder() {
				if (this.accountCost < 0) {
					Message.error("实际支付总金额不能小于0")
					this.orderItems.coupon = []
				} else {
					this.updatePayPrice();
					const orderId = this.orderId;
					const status = 1;
					const url = `http://localhost:8080/orders/${orderId}/status/${status}`;
					if (this.accountBalance < this.accountCost) {
						Message.error("余额不足，请充值后继续操作");
					} else {
						axios.put(url)
							.then(response => {
								
								this.payment(orderId);
								console.log(response);
								console.log("Order status updated successfully" + response.data);
								console.log('订单下单成功');
								Message.success("您的订单支付成功");

								// 执行页面刷新操作
								//this.fetchOrders(); // 假设有一个fetchOrders方法用于重新获取订单列表数据
								this.$router.push('/');
							})
							.catch(error => {
								console.error("Failed to update order status:", error);
							});
					}
				}
			},
			paymentRecoed(orderId) {
				let userId = JSON.parse(localStorage.getItem('user')).Userid;
				const url = `http://localhost:8080/paymentRecord`;
				const data = {
					userId: userId,
					orderId: Number(orderId),
					amount: Number(this.accountCost),
					channel: "官方",
					status: "1",
				};
				axios.post(url, data)
					.then(response => {
						Message.success("支付记录成功")
						console.log("Payment record successful: " + response.data);
					})
					.catch(error => {
						console.error("Payment failed:", error);
					});
			},
			payment(orderId) {
				let userId = JSON.parse(localStorage.getItem('user')).Userid;
				console.log(Number(this.accountCost))
				axios.post(`http://localhost:8080/userWallet/${userId}/payment`, null, {
					params: {
						orderId: Number(orderId),
						amount: Number(this.accountCost),
						point: Number(this.deductPoints),
					}
				}).then(response => {
					this.recordCouponUsed()
					this.paymentRecoed(orderId);
					this.ShopCartItems = response.data.data;
				}).catch(function(error) {
					console.log(error);
					if (userId === null) {
						alert('修改无效，请先登录');
					}
				})

			}
		},
	};
</script>

<style>
	.order {
		margin: 20px;
		background-color: #fff;
		padding: 20px;
		border-radius: 4px;
		box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
	}

	.page-title {
		font-size: 28px;
		font-weight: bold;
		margin-bottom: 10px;
	}

	.page-orderNO {
		font-size: 18px;
		margin-bottom: 20px;
	}

	.section-title {
		font-size: 20px;
		font-weight: bold;
		margin-top: 20px;
		margin-bottom: 10px;
	}

	.account-balance {
		font-size: 24px;
		margin-bottom: 20px;
		color: #333;
	}

	.payment-section {
		margin-bottom: 30px;
	}

	.coupon-section {
		margin-bottom: 30px;
	}

	.points-section {
		margin-bottom: 30px;
	}

	.total-section {
		margin-bottom: 30px;
	}

	.buttons-section {
		display: flex;
		justify-content: flex-end;
	}
</style>
