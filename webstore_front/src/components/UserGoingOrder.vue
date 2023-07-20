<template>
	<div>
		<h2>待发货订单</h2>
		<el-table :data="pagedOrders" style="width: 100%">
			<el-table-column prop="order_id" label="订单号"></el-table-column>
			<el-table-column prop="create_time" label="下单时间"></el-table-column>
			<el-table-column prop="productName" label="货品名称"></el-table-column>
			<el-table-column prop="price" label="价格(元)"></el-table-column>
			<el-table-column prop="size" label="尺码"></el-table-column>
			<el-table-column prop="quantity" label="数量"></el-table-column>
			<el-table-column prop="itemtotalprice" label="支付金额"></el-table-column>
			<el-table-column prop="status" label="状态"></el-table-column>
			<!-- 			<el-table-column label="操作">
				<template slot-scope="scope">
					<el-button type="primary" size="small" 
						@click="showShipDialog(scope.row)">查看</el-button>
				</template>
			</el-table-column> -->
		</el-table>

		<div class="pagination">
			<el-pagination :page-size="pageSize" :total="orders.length" layout="prev, pager, next"
				@current-change="handlePageChange"></el-pagination>
		</div>

		<!-- 		<el-dialog :visible.sync="shipDialogVisible" title="发货" @close="resetShipDialog">
			<p>确定要发货吗？</p>
			<span slot="footer" class="dialog-footer">
				<el-button @click="resetShipDialog">取消</el-button>
				<el-button type="primary" @click="shipOrder">确定</el-button>
			</span>
		</el-dialog> -->

	</div>
</template>

<script>
	import axios from 'axios'
	export default {
		data() {
			return {
				orders: '',
				currentPage: 1,
				pageSize: 5,
				shipDialogVisible: false,
				returnDialogVisible: false,
				selectedOrder: {}
			};
		},
		mounted() {
			var that = this;
			// this.fetchCategories();
			that.getOrder();
		},
		computed: {
			pagedOrders() {
				const startIndex = (this.currentPage - 1) * this.pageSize;
				const endIndex = startIndex + this.pageSize;
				return this.orders.slice(startIndex, endIndex);
			}
		},
		methods: {
			getOrder() {
				var that = this;
				let userId = JSON.parse(localStorage.getItem('user')).Userid;
				axios.get('http://localhost:8080/Goods/userFindGoing', {
					params: {
						userId: userId
					}
				}).then(response => {
					console.log(response.data.data);
					// that.orders = response.data.data
					let orderArray = response.data.data.map(item => ({
						order_id: item.order_id,
						id: item.id,
						productName: item.goodsname,
						price: item.price,
						size: item.size,
						quantity: item.goods_count,
						status: item.goods_status == 1 ? '待发货' : '已发货',
						create_time: item.create_time,
						itemtotalprice: item.itemtotalprice,
					}));
					that.orders = orderArray;
				}).catch(error => {
					// console.log(that.imageUrls);
					console.log(error);
					// reject(error);
				});

			},
			handlePageChange(currentPage) {
				this.currentPage = currentPage;
			},
			// showShipDialog(order) {
			// 	this.selectedOrder = {
			// 		...order,
			// 		id: order.id
			// 	};
			// 	// this.selectedOrder = { ...order };
			// 	this.shipDialogVisible = true;
			// },
			// resetShipDialog() {
			// 	this.selectedOrder = null;
			// 	this.shipDialogVisible = false;
			// },
			// shipOrder() {
			// 	// 发货操作的逻辑处理
			// 	// 可以在此处发送请求给后端进行发货操作，更新订单状态等

			// 	// 示例：将订单标记为已发货
			// 	console.log(this.selectedOrder.id);
			// 	axios.post('http://localhost:8080/orders/deliverGoods', {

			// 		orderId: this.selectedOrder.order_id,
			// 		id: this.selectedOrder.id,
			// 		quantity: this.selectedOrder.quantity,

			// 	}).then(response => {
			// 		console.log(response.data.data);
			// 		// that.orders = response.data.data

			// 	}).catch(error => {
			// 		// console.log(that.imageUrls);
			// 		console.log(error);
			// 		// reject(error);
			// 	});
			// 	const index = this.orders.findIndex(p => p.id === this.selectedOrder.id);
			// 	if (index !== -1) {
			// 		this.orders.splice(index, 1);
			// 		// this.products[index].status = "Off";
			// 	}

			// 	this.selectedOrder.status = '已发货';

			// 	// 示例：提示发货成功
			// 	this.$message.success('订单发货成功');

			// 	this.resetShipDialog();
			// },
			showReturnDialog(order) {
				this.selectedOrder = order;
				this.returnDialogVisible = true;
			},
			resetReturnDialog() {
				this.selectedOrder = null;
				this.returnDialogVisible = false;
			},
			handleReturn(order) {
				this.showReturnDialog(order);
			},
			handleReject(order) {
				this.showReturnDialog(order);
			},
		}
	};
</script>

<style>
	.pagination {
		margin-top: 20px;
		text-align: right;
	}
</style>
