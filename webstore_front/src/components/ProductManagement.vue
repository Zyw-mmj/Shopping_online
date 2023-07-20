<template>
	<div class="product-management">
		<!-- <h1>商品管理页面</h1> -->
		<!-- 搜索栏 -->

		<el-form :inline="true" class="search-form">
			<el-form-item>
				<el-input v-model="searchKeyword" placeholder="请输入关键词" @keyup.enter="searchProduct"></el-input>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="searchProducts">搜索</el-button>
			</el-form-item>
			<el-form-item>
				<el-button type="primary" @click="showTimeSettingDialog">设置禁止发货时间</el-button>
			</el-form-item>

		</el-form>

		<!-- 商品列表 -->
		<el-table :data="pagedProducts" style="width: 100%" border>
			<el-table-column prop="goodsname" label="商品名称"></el-table-column>
			<el-table-column prop="price" label="价格"></el-table-column>
			<el-table-column prop="categorie" label="分类"></el-table-column>
			<el-table-column prop="businessAccount" label="商家账号"></el-table-column>
			<el-table-column prop="status" label="审核状态">
				<template slot-scope="{ row }">
					<el-tag :type="getStatusTagType(row.status)">{{ getStatusText(row.status) }}</el-tag>
				</template>
			</el-table-column>

			<el-table-column label="操作" width="200">
				<template slot-scope="scope">
					<div class="button-group">
						<el-button type="primary" @click="showConfirmationDialog(scope.row)" size="mini"
							class="action-button">编辑
						</el-button>
						<el-button type="danger" @click="deleteProduct(scope.row)" size="mini" class="action-button">删除
						</el-button>
					</div>
				</template>
			</el-table-column>
		</el-table>

		<!-- 分页器 -->
		<div class="pagination">
			<el-pagination :current-page="currentPage" :page-size="pageSize" :total="totalProducts"
				layout="prev, pager, next" @current-change="handlePageChange"></el-pagination>
		</div>

		<div v-if="showConfirmation" class="overlay">
			<div class="confirmation-dialog">
				<h3>审核通过</h3>
				<p>是否审核通过该商品？</p>
				<div class="dialog-buttons">
					<el-button @click="cancelConfirmation">不通过</el-button>
					<el-button type="primary" @click="confirmProduct">通过</el-button>
				</div>
			</div>
		</div>

		<el-dialog title="设置发布时间" :visible.sync="showTimeDialog" width="30%">
			<el-form ref="timeForm" :model="timeForm" label-width="100px">
				<el-form-item label="开始时间">
					<el-date-picker v-model="timeForm.startTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
						:picker-options="pickerOptions"></el-date-picker>
				</el-form-item>
				<el-form-item label="结束时间">
					<el-date-picker v-model="timeForm.endTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
						:picker-options="pickerOptions"></el-date-picker>
				</el-form-item>
			</el-form>
			<div slot="footer" class="dialog-footer">
				<el-button @click="cancelTimeDialog">取消</el-button>
				<el-button type="primary" @click="confirmTimeDialog">确定</el-button>
			</div>
		</el-dialog>


	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return {
				searchKeyword: '',
				products: [],
				showTimeDialog: false, // 对话框显示状态
				timeForm: {
					startTime: '', // 开始时间
					endTime: '' // 结束时间
				},
				pickerOptions: {
					disabledDate(time) {
						return time.getTime() < Date.now() - 8.64e7; // 禁止选择过去的时间
					},
					disabledDateTime(time) {
						// 如果有开始时间（start），禁止选择小于start的时间
						const start = this.timeForm.startTime;
						if (start) {
							return time.getTime() < start.getTime();
						}
						return false;
					}
				},
				currentPage: 1,
				pageSize: 6,
				totalProducts: 0,
				showConfirmation: false, // 弹窗显示状态
				selectedProduct: null, // 当前选中的商品
			};
		},
		mounted() {
			// var that = this;
			this.searchProducts();
		},
		computed: {
			pagedProducts() {
				// 根据当前页码和每页数量计算当前页显示的商品
				const startIndex = (this.currentPage - 1) * this.pageSize;
				const endIndex = startIndex + this.pageSize;
				return this.products.slice(startIndex, endIndex);
			},
		},
		methods: {
			searchProducts() {
				return new Promise((resolve, reject) => {
					axios.get('http://localhost:8080/Admin/searchGoods', {
						params: {
							keyword: this.searchKeyword
						}
					}).then(response => {
						console.log(response);
						this.products = response.data.data;
						this.totalProducts = this.products.length;
						resolve(); // 搜索商品完成后解析Promise
					}).catch(error => {
						reject(error); // 搜索商品出错时拒绝Promise
					});
				});
			},
			showTimeSettingDialog() {
				this.timeForm.startTime = ''; // 清空开始时间
				this.timeForm.endTime = ''; // 清空结束时间
				this.showTimeDialog = true; // 显示对话框
			},
			cancelTimeDialog() {
				this.showTimeDialog = false; // 隐藏对话框
			},
			confirmTimeDialog() {
				this.showTimeDialog = false; // 隐藏对话框
				// 在这里处理设置发布时间的逻辑，可以使用this.timeForm.startTime和this.timeForm.endTime来获取用户设置的时间
				// ...
				// 获取用户选择的开始时间和结束时间
				const selectedStartTime = this.timeForm.startTime;
				const selectedEndTime = this.timeForm.endTime;

				// 验证开始时间小于结束时间
				if (selectedStartTime >= selectedEndTime) {
					// 开始时间大于等于结束时间，显示错误消息或执行处理
					alert('开始时间必须小于结束时间');
					this.showTimeDialog = true; // 隐藏对话框
					return; // 中止方法执行
				}
				console.log(this.timeForm.startTime);
				console.log(this.timeForm.endTime);
				axios.post('http://localhost:8080/Admin/insertStartAndEndTime', null, {
					params: {
						start: this.timeForm.startTime,
						end: this.timeForm.endTime,
					}
				}).then(response => {
					console.log(response);
				})
			},
			showConfirmationDialog(product) {
				// console.log("123123");
				// console.log(this.selectedProduct);
				this.selectedProduct = product; // 保存选中的商品
				// console.log(this.selectedProduct);
				this.showConfirmation = true; // 显示弹窗
				// console.log(this.showConfirmation);
			},
			deleteProduct(product) {
				this.selectedProduct = product;
				var p_id = this.selectedProduct.id;
				var b_acc = this.selectedProduct.businessAccount;

				axios.delete('http://localhost:8080/Admin/deleteGood', {
					params: {
						id: p_id,
						businessAccount: b_acc,
					}
				}).then(response => {
					console.log(response);
					this.searchProducts().then(() => {
						if (this.pagedProducts.length === 0 && this.currentPage > 1) {
							// 当前页为空且当前页不是第一页
							if ((this.currentPage - 1) * this.pageSize < this.totalProducts) {
								// 当前页的起始索引小于总商品数，将当前页设置为第一页
								this.currentPage = 1;
							} else {
								// 当前页的起始索引大于等于总商品数，将当前页设置为最后一页
								this.currentPage = Math.ceil(this.totalProducts / this.pageSize);
							}
						}
					});
				})

			},
			cancelConfirmation() {
				if (this.selectedProduct) {
					const updatedProduct = {
						...this.selectedProduct,
						status: '审核不通过' // 根据实际需求修改状态值
					};
					console.log(updatedProduct.id);
					axios.put(`http://localhost:8080/Goods/updateProductStatus1/${updatedProduct.id}`, updatedProduct)
						.then(response => {
							console.log(response.data);
							this.searchProducts();
							this.selectedProduct = null; // 清空选中的商品
							this.showConfirmation = false; // 隐藏弹窗
						})
						.catch(error => {
							console.log(error);
						});
				}
				this.showConfirmation = false; // 隐藏弹窗
				this.selectedProduct = null; // 清空选中的商品
			},
			confirmProduct() {
				// 在这里可以执行审核通过的逻辑操作
				// 可以使用this.selectedProduct来获取选中的商品对象
				// ...
				console.log(this.selectedProduct.id);
				if (this.selectedProduct) {
					const updatedProduct = {
						...this.selectedProduct,
						status: '审核通过' // 根据实际需求修改状态值
					};
					console.log(updatedProduct.id);
					axios.put(`http://localhost:8080/Goods/updateProductStatus/${updatedProduct.id}`, updatedProduct)
						.then(response => {
							console.log(response.data);
							this.searchProducts();
							this.selectedProduct = null; // 清空选中的商品
							this.showConfirmation = false; // 隐藏弹窗
						})
						.catch(error => {
							console.log(error);
						});
				}
			},
			getStatusTagType(status) {
				switch (status) {
					case 'Pending':
						return 'info';
					case 'Approved':
						return 'success';
					case 'Rejected':
						return 'danger';
					case 'Off':
						return 'danger';
					default:
						return '';
				}
			},
			getStatusText(status) {
				switch (status) {
					case 'Pending':
						return '待审核';
					case 'Approved':
						return '已发布';
					case 'Rejected':
						return '审核不通过';
					case 'Off':
						return '未上架';
					default:
						return '';
				}
			},
			handlePageChange(currentPage) {
				// 分页器页码改变时的回调函数
				this.currentPage = currentPage;
			},
		},
	};
</script>

<style scoped>
	.product-management {
		padding: 20px;
	}

	.action-button {
		display: flex;
		gap: 10px;
		/* 按钮之间的间距 */
		justify-content: flex-start;
		/* 左对齐按钮 */
	}

	.button-group {
		display: flex;
		align-items: center;
		gap: 10px;
	}


	.search-form {
		margin-bottom: 20px;
	}

	.pagination {
		display: flex;
		justify-content: center;
		margin-top: 20px;
	}

	.el-pagination {
		display: inline-block;
		font-size: 0;
		margin: 0;
		padding: 0;
	}

	.el-pagination .el-pager {
		margin: 0 5px;
	}

	.el-pagination .el-pager:not(.is-disabled):hover {
		color: #1890ff;
	}

	.el-pagination .el-pager.is-active {
		background-color: #1890ff;
		color: #fff;
		border-color: #1890ff;
	}

	.el-pagination .el-pager.is-active:hover {
		color: #fff;
		background-color: #1890ff;
		border-color: #1890ff;
	}

	.overlay {
		position: fixed;
		top: 0;
		left: 0;
		width: 100%;
		height: 100%;
		background-color: rgba(0, 0, 0, 0.5);
		display: flex;
		align-items: center;
		justify-content: center;
		z-index: 9999;
	}

	.confirmation-dialog {
		background-color: #fff;
		padding: 20px;
		border-radius: 4px;
		text-align: center;
	}

	.dialog-buttons {
		margin-top: 20px;
	}
</style>
