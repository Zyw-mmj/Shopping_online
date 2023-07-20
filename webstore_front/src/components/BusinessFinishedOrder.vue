<template>
	<div>
		<h2>已完成订单</h2>
		<el-table :data="pagedOrders" style="width: 100%">
			<el-table-column prop="productName" label="货品名称"></el-table-column>
			<el-table-column prop="price" label="价格"></el-table-column>
			<el-table-column prop="size" label="尺码"></el-table-column>
			<el-table-column prop="quantity" label="数量"></el-table-column>
			<el-table-column prop="status" label="状态"></el-table-column>
			<el-table-column label="操作" min-width="150px">
				<template slot-scope="scope">
					<div class="button-group">
						<el-button type="warning" size="small" v-if="scope.row.status != '退货' "
							@click="showFeedbackDialog(scope.row)">评价</el-button>
						<el-button type="success" size="small" v-if="scope.row.status != '退货' "
							@click="showRatingDialog(scope.row)">用户好评率</el-button>
					</div>
				</template>
			</el-table-column>
		</el-table>

		<div class="pagination">
			<el-pagination :page-size="pageSize" :total="orders.length" layout="prev, pager, next"
				@current-change="handlePageChange"></el-pagination>
		</div>

		<el-dialog :visible.sync="dialogVisibleRating" title="用户好评率">
			<div class="dialog-content">
				<div class="rating-info">
					<div class="text">用户名：{{ username }}</div>
					用户好评率：<el-rate :value="positiveRate" :show-text="false" disabled class="rating"></el-rate>
				</div>
			</div>
			<span slot="footer" class="dialog-footer">
				<el-button @click="dialogVisibleRating = false">关闭</el-button>
			</span>
		</el-dialog>

		<el-dialog :visible.sync="shipDialogVisible" title="发货" @close="resetShipDialog">
			<p>确定要发货吗？</p>
			<span slot="footer" class="dialog-footer">
				<el-button @click="resetShipDialog">取消</el-button>
				<el-button type="primary" @click="shipOrder">确定</el-button>
			</span>
		</el-dialog>

		<el-dialog :visible.sync="returnDialogVisible" title="退货处理" @close="resetReturnDialog">
			<p>请选择对退货订单的处理：</p>
			<span slot="footer" class="dialog-footer">
				<el-button @click="resetReturnDialog">取消</el-button>
				<el-button type="success" @click="approveReturn">通过</el-button>
				<el-button type="danger" @click="rejectReturn">驳回</el-button>
			</span>
		</el-dialog>

		<el-dialog :visible.sync="dialogVisibleFeedback" width="30%">
			<h2>商家反馈</h2>
			<div>
				<el-form ref="feedbackForm" :model="feedbackData" :rules="feedbackRules" label-width="120px">
					<el-form-item label="商家评价:" prop="comment">
						<el-input type="textarea" v-model="feedbackData.comment" placeholder="请输入商家评价">
						</el-input>
					</el-form-item>
					<el-form-item label="用户评分:" prop="rating">
						<el-rate v-model="feedbackData.rating" :max="5" show-text></el-rate>
					</el-form-item>
				</el-form>
			</div>
			<span slot="footer" class="dialog-footer">
				<el-button @click="dialogVisibleFeedback = false">取消</el-button>
				<el-button type="primary" @click="submitFeedback">提交反馈</el-button>
			</span>
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
				orders: '',
				username: '',
				currentPage: 1,
				pageSize: 5,
				shipDialogVisible: false,
				returnDialogVisible: false,
				selectedOrder: {},
				dialogVisibleFeedback: false,
				dialogVisibleRating: false,
				feedbackData: {
					good_id:'',
					account: '',
					order_id: '',
					comment: '',
					rating: null
				},
				positiveRate: '',
				feedbackRules: {
					comment: [{
						required: true,
						message: '请输入用户评价',
						trigger: 'blur'
					}],
					userRating: [{
						required: true,
						message: '请选择用户评分',
						trigger: 'change'
					}],
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
			showFeedbackDialog(order) {
				this.selectedOrder = {
					...order,
					order_id: order.order_id
				};
				console.log(this.selectedOrder);
				this.dialogVisibleFeedback = true;
			},
			showRatingDialog(order) {
				this.selectedOrder = {
					...order,
					order_id: order.order_id
				};
				console.log(this.selectedOrder.order_id);
				this.dialogVisibleRating = true;
				axios.get(`http://localhost:8080/User/getUserRating/${this.selectedOrder.order_id}`)
					.then(response => {
						console.log(response);
						this.positiveRate = response.data.data.rating;
						this.username = response.data.data.name;
					})
			},
			submitFeedback() {
				// console.log(this.selectedOrder.order_id);
				let businessAccount = JSON.parse(localStorage.getItem('business')).account;
				this.feedbackData.good_id = this.selectedOrder.id;
				this.feedbackData.account = businessAccount;
				this.feedbackData.order_id = this.selectedOrder.order_id;
				console.log(this.feedbackData.good_id);
				console.log(this.feedbackData);
				this.$refs.feedbackForm.validate(valid => {
					if (valid) {
						// 反馈表单验证通过
						// 调用后端接口提交反馈数据
						axios.post('http://localhost:8080/Feedback/Businessfeedback', this.feedbackData)
							.then(response => {
								// 提交成功后的处理
								console.log(response);
								console.log('反馈提交成功');
								this.dialogVisibleFeedback = false;
								Message.success("反馈提交成功");
								this.feedbackData.comment = null;
								this.feedbackData.rating = null;
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
			getOrder() {
				var that = this;
				let businessId = JSON.parse(localStorage.getItem('business')).account;
				axios.get('http://localhost:8080/Goods/getFinishedGoods', {
					params: {
						account: businessId
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
							status = '已驳回';
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
			resetShipDialog() {
				this.selectedOrder = null;
				this.shipDialogVisible = false;
			},
			shipOrder() {
				// 发货操作的逻辑处理
				// 可以在此处发送请求给后端进行发货操作，更新订单状态等

				// 示例：将订单标记为已发货
				console.log(this.selectedOrder.id);
				axios.post('http://localhost:8080/orders/deliverGoods', {

					orderId: this.selectedOrder.order_id,
					id: this.selectedOrder.id,
					quantity: this.selectedOrder.quantity,

				}).then(response => {
					console.log(response.data.data);
					// that.orders = response.data.data

				}).catch(error => {
					// console.log(that.imageUrls);
					console.log(error);
					// reject(error);
				});

				this.selectedOrder.status = '已发货';

				// 示例：提示发货成功
				this.$message.success('订单发货成功');

				this.resetShipDialog();
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
			approveReturn() {
				// 退货通过操作的逻辑处理
				// 可以在此处发送请求给后端进行退货通过操作，更新订单状态等

				// 示例：将订单标记为退货已通过
				this.selectedOrder.status = '退货已通过';

				// 示例：提示退货通过成功
				this.$message.success('退货已通过');

				this.resetReturnDialog();
			},
			rejectReturn() {
				// 退货驳回操作的逻辑处理
				// 可以在此处发送请求给后端进行退货驳回操作，更新订单状态等

				// 示例：将订单标记为退货已驳回
				this.selectedOrder.status = '退货已驳回';

				// 示例：提示退货驳回成功
				this.$message.success('退货已驳回');

				this.resetReturnDialog();
			}
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

	.rating {
		margin-left: 10px;
	}
</style>
