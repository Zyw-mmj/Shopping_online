<template>
	<div>
		<h2>已完成订单</h2>
		<el-table :data="pagedOrders" style="width: 100%">
			<el-table-column prop="order_id" label="订单号"></el-table-column>
			<el-table-column prop="productName" label="货品名称"></el-table-column>
			<el-table-column prop="price" label="价格"></el-table-column>
			<el-table-column prop="size" label="尺码"></el-table-column>
			<el-table-column prop="quantity" label="数量"></el-table-column>
			<el-table-column prop="status" label="状态"></el-table-column>
			<el-table-column label="操作" min-width="150px">
				<template slot-scope="scope">
					<div class="button-group">
						<el-button type="primary" size="small" @click="showFeedbackDialog2(scope.row)">查看商家评价
						</el-button>
						<el-button type="warning" size="small" @click="showFeedbackDialog(scope.row)">评价</el-button>
						<el-button v-if="scope.row.status=='已完成'" type="danger" size="small"
							@click="showRefundDialog(scope.row)">退货</el-button>
						<el-button v-if="scope.row.status=='被驳回'" type="success" size="small"
							@click="showRefuseDialog(scope.row)">查看驳回理由</el-button>
					</div>
				</template>
			</el-table-column>
		</el-table>

		<div class="pagination">
			<el-pagination :page-size="pageSize" :total="orders.length" layout="prev, pager, next"
				@current-change="handlePageChange"></el-pagination>
		</div>

		<el-dialog :visible.sync="dialogVisible3" title="评价列表" width="80%">
			<el-table :data="feedbackList" border>
				<el-table-column prop="goodsname" label="商品名称"></el-table-column>
				<el-table-column prop="comment" label="商品评价"></el-table-column>
				<el-table-column prop="rating" label="评分">
					<template slot-scope="scope">
						<el-rate :value="scope.row.rating" :show-text="false" disabled></el-rate>
					</template>
				</el-table-column>
				<el-table-column prop="created_at" label="评价时间">
					<template slot-scope="scope">
						{{ formatDate(scope.row.created_at) }}
					</template>
				</el-table-column>
			</el-table>
		</el-dialog>

		<!-- 		<el-dialog :visible.sync="shipDialogVisible" title="发货" @close="resetShipDialog">
			<p>确定要发货吗？</p>
			<span slot="footer" class="dialog-footer">
				<el-button @click="resetShipDialog">取消</el-button>
				<el-button type="primary" @click="shipOrder">确定</el-button>
			</span>
		</el-dialog> -->
		<el-dialog :visible.sync="dialogVisible">
			<span>该订单收货已满24小时，不能退换。</span>
			<span slot="footer" class="dialog-footer">
				<el-button @click="dialogVisible = false">关闭</el-button>
			</span>
		</el-dialog>
		<el-dialog :visible.sync="shipDialogVisible2" title="退货" @close="resetShipDialog2">
			<p>确定要退货吗？</p>
			<span slot="footer" class="dialog-footer">
				<el-button @click="resetShipDialog2">取消</el-button>
				<el-button type="primary" @click="refundOrder">确定</el-button>
			</span>
		</el-dialog>
		<el-dialog :visible.sync="dialogVisibleFeedback" width="30%">
			<div>
				<h2>交易反馈</h2>
				<el-form ref="feedbackForm" :model="feedbackData" :rules="feedbackRules" label-width="120px">
					<el-form-item label="交易评价" prop="transactionFeedback">
						<el-input type="textarea" v-model="feedbackData.transactionFeedback" placeholder="请输入交易评价">
						</el-input>
					</el-form-item>
					<el-form-item label="商品评价" prop="productFeedback">
						<el-input type="textarea" v-model="feedbackData.productFeedback" placeholder="请输入商品评价">
						</el-input>
					</el-form-item>
					<el-form-item label="交易评分" prop="transactionRating">
						<el-rate v-model="feedbackData.transactionRating" :max="5" show-text></el-rate>
					</el-form-item>
					<el-form-item label="商品评分" prop="productRating">
						<el-rate v-model="feedbackData.productRating" :max="5" show-text></el-rate>
					</el-form-item>
				</el-form>
			</div>
			<span slot="footer" class="dialog-footer">
				<el-button @click="dialogVisibleFeedback = false">取消</el-button>
				<el-button type="primary" @click="submitFeedback">提交反馈</el-button>
			</span>
		</el-dialog>

		<el-dialog :visible.sync="refuseDialogVisible" title="被驳回理由" @close="resetRefuseDialog">
			<p>{{this.rejectReason}}</p>
		</el-dialog>

	</div>
</template>

<script>
	import axios from 'axios'
	import {
		Message
	} from 'element-ui';
	export default {
		data() {
			return {
				feedbackList: [],
				orders: '',
				currentPage: 1,
				pageSize: 5,
				shipDialogVisible: false,
				shipDialogVisible2: false,
				returnDialogVisible: false,
				dialogVisible3: false,
				selectedOrder: {},
				OrderId: '',
				ProductId: '',
				good_id: '',
				business_account:'',
				refuseDialogVisible: false,
				rejectReason: '',
				dialogVisible: false,
				dialogVisibleFeedback: false,
				feedbackData: {
					orderId: '',
					productId: '',
					transactionFeedback: '',
					productFeedback: '',
					transactionRating: null,
					productRating: null
				},
				feedbackRules: {
					transactionFeedback: [{
						required: true,
						message: '请输入交易评价',
						trigger: 'blur'
					}],
					productFeedback: [{
						required: true,
						message: '请输入商品评价',
						trigger: 'blur'
					}],
					transactionRating: [{
						required: true,
						message: '请选择交易评分',
						trigger: 'change'
					}],
					productRating: [{
						required: true,
						message: '请选择商品评分',
						trigger: 'change'
					}]
				}
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
			showFeedbackDialog2(order) {
				// console.log(order.order_id);
				this.good_id = order.id;
				this.OrderId = order.order_id;
				this.business_account = order.business_account;
				this.dialogVisible3 = true;
				this.getSellerFeedback();
			},
			formatDate(timestamp) {
				const date = new Date(timestamp);
				const year = date.getFullYear();
				const month = ("0" + (date.getMonth() + 1)).slice(-2);
				const day = ("0" + date.getDate()).slice(-2);
				const hours = ("0" + date.getHours()).slice(-2);
				const minutes = ("0" + date.getMinutes()).slice(-2);
				return `${year}-${month}-${day} ${hours}:${minutes}`;
			},
			getSellerFeedback() {
				let userId = JSON.parse(localStorage.getItem('user')).Userid;
				// console.log(this.OrderId);
				console.log(this.business_account);
				axios
					.get('http://localhost:8080/Feedback/BusinessgetFeedback', {
						params: {
							user_id: userId,
							order_id: this.OrderId,
							good_id : this.good_id,
							business_account : this.business_account,
						}
					})
					.then(response => {
						console.log(response);
						this.feedbackList = response.data.data;

					})
					.catch(error => {
						console.log(error);
					});
			},
			// 在这里使用 feedbackData 属性的值进行处理
			submitFeedback() {
				console.log(this.selectedOrder.order_id);
				console.log(this.selectedOrder.id);
				this.feedbackData.productId = this.selectedOrder.id;
				this.feedbackData.orderId = this.selectedOrder.order_id;
				console.log(this.feedbackData);
				this.$refs.feedbackForm.validate(valid => {
					if (valid) {
						// 反馈表单验证通过
						// 调用后端接口提交反馈数据
						axios.post('http://localhost:8080/Feedback/', this.feedbackData)
							.then(response => {
								// 提交成功后的处理
								console.log(response);
								console.log('反馈提交成功');
								this.dialogVisibleFeedback = false;
								Message.success("反馈提交成功");
								this.feedbackData.productFeedback = null;
								this.feedbackData.productRating = null;
								this.feedbackData.transactionFeedback = null;
								this.feedbackData.transactionRating = null;
							})
							.catch(error => {
								// 提交失败后的处理
								console.error('反馈提交失败', error);
							});
					} else {
						// 反馈表单验证不通过，进行相应提示
						this.$message.error('请正确填写评价内容和评分');
					}
				});
			},
			showFeedbackDialog(order) {
				this.selectedOrder = {
					...order,
					id: order.id,
					order_id: order.order_id
				};
				this.dialogVisibleFeedback = true;
			},
			getOrder() {
				var that = this;
				let userId = JSON.parse(localStorage.getItem('user')).Userid;
				axios.get('http://localhost:8080/Goods/userFindFinished', {
					params: {
						userId: userId
					}
				}).then(response => {
					console.log(response.data.data);
					// that.orders = response.data.data
					let orderArray = response.data.data.map(item => {
						let status = '';
						if (item.goods_status == 4) {
							status = '已完成';
						} else if (item.goods_status == 6) {
							status = '已退货';
						} else if (item.goods_status == 7) {
							status = '被驳回';
						} else {
							status = '已发货';
						}
						return {
							order_id: item.order_id,
							id: item.id,
							productName: item.goodsname,
							price: item.price,
							size: item.size,
							quantity: item.goods_count,
							status: status,
							business_account:item.businessAccount,
						};
					});
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
			showShipDialog(order) {
				this.selectedOrder = {
					...order,
					id: order.id
				};
				// this.selectedOrder = { ...order };
				this.shipDialogVisible = true;
			},
			showRefundDialog(order) {
				this.selectedOrder = {
					...order,
					id: order.id
				};
				// this.selectedOrder = { ...order };
				this.RefundOrder();
			},
			showRefuseDialog(order) {
				this.selectedOrder = {
					...order,
					id: order.id
				};
				this.getRejectReason();
				// this.selectedOrder = { ...order };

			},
			resetRefuseDialog() {
				this.selectedOrder = null;
				this.refuseDialogVisible = false;
			},
			getRejectReason() {
				console.log("%%%%%%%%%%%%%%%%%%%%");
				axios.post('http://localhost:8080/orders/getRefuseReason', {
					orderId: this.selectedOrder.order_id,
					id: this.selectedOrder.id,
				}).then(response => {
					console.log(response.data);
					this.rejectReason = response.data.data;
					this.refuseDialogVisible = true;
					// if (response.data == false) {
					//  this.dialogVisible = true;
					// }
					// if (response.data == true) {
					//  this.shipDialogVisible2 = true;
					// }
					// that.orders = response.data.data

				}).catch(error => {
					// console.log(that.imageUrls);
					console.log(error);
					// reject(error);
				});
			},
			resetShipDialog() {
				this.selectedOrder = null;
				this.shipDialogVisible = false;
			},
			resetShipDialog2() {
				this.selectedOrder = null;
				this.shipDialogVisible2 = false;
			},
			RefundOrder() {
				console.log("%%%%%%%%%%%%%%%%%%%%");
				axios.post('http://localhost:8080/orders/checkItemPermission', {
					orderId: this.selectedOrder.order_id,
					id: this.selectedOrder.id,
				}).then(response => {
					console.log(response.data);
					if (response.data == false) {
						this.dialogVisible = true;
					}
					if (response.data == true) {
						this.shipDialogVisible2 = true;
					}
					// that.orders = response.data.data

				}).catch(error => {
					// console.log(that.imageUrls);
					console.log(error);
					// reject(error);
				});
			},
			refundOrder() {
				console.log(this.selectedOrder.id);
				axios.post('http://localhost:8080/orders/refundGoods', {
					orderId: this.selectedOrder.order_id,
					id: this.selectedOrder.id,
				}).then(response => {
					console.log(response.data.data);
					// that.orders = response.data.data

				}).catch(error => {
					// console.log(that.imageUrls);
					console.log(error);
					// reject(error);
				});
				const index = this.orders.findIndex(p => p.id === this.selectedOrder.id);
				if (index !== -1) {
					this.orders.splice(index, 1);
					// this.products[index].status = "Off";
				}

				// this.selectedOrder.status = '待处理';

				// 示例：提示发货成功
				this.$message.success('退货申请成功');

				this.resetShipDialog2();
			},
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

	.button-group {
		display: flex;
		align-items: center;
		gap: 10px;
	}
</style>
