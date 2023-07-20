<template>
	<div class="wallet-page">
		<div class="sidebar">
			<el-menu>
				<el-menu-item @click="showRechargeForm">
					<i class="el-icon-plus"></i>
					<span>充值账户</span>
				</el-menu-item>
				<el-menu-item @click="showRechargeRecords">
					<i class="el-icon-money"></i>
					<span>充值记录</span>
				</el-menu-item>
				<el-menu-item @click="showPaymentRecords">
					<i class="el-icon-shopping-cart-full"></i>
					<span>支付记录</span>
				</el-menu-item>
			</el-menu>
		</div>

		<div class="main-content">
			<h1>用户钱包</h1>
			<h3>账号: {{ username }}</h3>
			<!-- 	<h3>ID: {{ user.id }}</h3> -->
			<h3>用户积分: {{ user.points }}</h3>
			<h3>账户余额: {{ user.balance }}</h3>

			<div v-if="rechargeFormVisible">
				<h2>充值账户</h2>
				<el-form label-position="top">
					<el-form-item label="充值金额">
						<el-input v-model="rechargeAmount" type="number" placeholder="请输入充值金额" :min="0" :max="1000000"
							step="0.01"></el-input>

					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="recharge">确认充值</el-button>
					</el-form-item>
				</el-form>
			</div>

			<div v-if="rechargeTableVisible">
				<h2>充值记录</h2>
				<el-table :data="rechargeRecord" border>
					<el-table-column prop="id" label="充值ID"></el-table-column>
					<el-table-column prop="amount" label="金额"></el-table-column>
					<el-table-column prop="channel" label="充值渠道"></el-table-column>
					<el-table-column prop="status" label="支付状态"><template slot-scope="scope">
							<span v-if="scope.row.status === '1'">支付成功</span>
							<span v-else>{{ scope.row.status }}</span>
						</template></el-table-column>
					<el-table-column prop="createTime" label="订单创建时间"></el-table-column>
				</el-table>
			</div>

			<div v-if="paymentTableVisible">
				<h2>支付记录</h2>
				<el-table :data="paymentRecord" border>
					<el-table-column prop="id" label="消费ID"></el-table-column>
					<el-table-column prop="orderId" label="订单ID"></el-table-column>
					<el-table-column prop="amount" label="消费金额"></el-table-column>
					<el-table-column prop="channel" label="消费支付渠道"></el-table-column>
					<el-table-column prop="status" label="支付状态"><template slot-scope="scope">
							<span v-if="scope.row.status === '1'">支付成功</span>
							<span v-else>{{ scope.row.status }}</span>
						</template></el-table-column>
					<el-table-column prop="createTime" label="支付时间"></el-table-column>
				</el-table>
			</div>
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
				username: JSON.parse(localStorage.getItem('user')).account,
				user: {},
				rechargeRecord: [],
				paymentRecord: [],
				rechargeFormVisible: false,
				rechargeTableVisible: false,
				paymentTableVisible: false,
				rechargeAmount: 0,
			};
		},
		mounted() {
			this.fetchUser();
			this.fetchRechargeRecord();
			this.fetchPaymentRecord();
		},
		methods: {
			fetchUser() {
				let userId = JSON.parse(localStorage.getItem('user')).Userid;
				axios.get(`http://localhost:8080/userWallet/${userId}`)
					.then(response => {
						this.user = response.data;
					})
					.catch(error => {
						console.error(error);
					});
			},
			fetchRechargeRecord() {
				// Make an API request to fetch recharge records by user ID
				let userId = JSON.parse(localStorage.getItem('user')).Userid;
				axios.get(`http://localhost:8080/rechargeRecord/user/${userId}`)
					.then(response => {
						console.log(response);
						this.rechargeRecord = response.data;
					})
					.catch(error => {
						console.error(error);
					});
			},
			fetchPaymentRecord() {
				// Make an API request to fetch payment records by user ID
				let userId = JSON.parse(localStorage.getItem('user')).Userid;
				axios.get(`http://localhost:8080/paymentRecord/user/${userId}`)
					.then(response => {
						console.log("》》》》》》》》》》》》》" + this.paymentRecord);
						this.paymentRecord = response.data;
					})
					.catch(error => {
						console.error(error);
					});
			},
			recharge() {
				let userId = JSON.parse(localStorage.getItem('user')).Userid;
				const url = `http://localhost:8080/userWallet/${userId}/recharge`;
				const data = {
					amount: this.rechargeAmount,
				};

				axios.post(url, data)
					.then(response => {
						this.createChargeInfo();
						Message.success("充值成功")
						console.log("Payment successful: " + response.data);
					})
					.catch(error => {
						console.error("Payment failed:", error);
					});
			},
			createChargeInfo() {
				let userId = JSON.parse(localStorage.getItem('user')).Userid;
				const url = `http://localhost:8080/rechargeRecord`;
				const data = {
					userId: userId,
					amount: this.rechargeAmount,
					channel: "官方",
					status: "1",
				};
				axios.post(url, data)
					.catch(error => {
						console.error("Payment failed:", error);
					});
			},
			showRechargeForm() {
				this.rechargeFormVisible = true;
				this.rechargeTableVisible = false;
				this.paymentTableVisible = false;
			},
			showRechargeRecords() {
				this.rechargeFormVisible = false;
				this.rechargeTableVisible = true;
				this.paymentTableVisible = false;
			},
			showPaymentRecords() {
				this.rechargeFormVisible = false;
				this.rechargeTableVisible = false;
				this.paymentTableVisible = true;
			},
		},
	};
</script>

<style scoped>
	.wallet-page {
		display: flex;
		height: 100vh;
	}

	.sidebar {
		width: 20%;
		background-color: #f0f0f0;
	}

	.main-content {
		flex-grow: 1;
		padding: 20px;
	}

	h1 {
		font-size: 24px;
		margin-bottom: 10px;
	}

	h2 {
		font-size: 20px;
		margin-bottom: 10px;
	}

	h3 {
		font-size: 16px;
		margin-bottom: 5px;
	}
</style>