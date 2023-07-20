<template>
	<div class="admin-panel">
		<h1>商家管理</h1>
		<el-table :data="pagedUsers" style="width: 100%" border>
			<el-table-column prop="id" label="商家ID" min-width="100px"></el-table-column>
			<el-table-column prop="account" label="商家账号" min-width="120px"></el-table-column>
			<el-table-column prop="name" label="商家名称" min-width="120px"></el-table-column>
			<el-table-column prop="amount" label="交易额" min-width="100px"></el-table-column>
			<el-table-column prop="quantity" label="交易数量" min-width="100px"></el-table-column>
			<el-table-column prop="grade" label="商家等级" min-width="80px"></el-table-column>
			<el-table-column prop="fee" label="收取费用" min-width="100px"></el-table-column>
			<el-table-column label="操作" min-width="150px">
				<template slot-scope="{ row }">
					<div class="button-group">
						<el-button type="primary" size="mini" @click="showLevelDialog(row)">调整等级</el-button>
						<el-button type="primary" size="mini" @click="showMerchantDetails(row)">查看商品</el-button>
					</div>
				</template>
			</el-table-column>
		</el-table>

		<el-dialog title="调整商家等级" :visible.sync="showDialog" class="level-dialog">
			<el-form label-width="80px">
				<el-form-item label="商家ID:">
					{{ selectedMerchant.id }}
				</el-form-item>
				<el-form-item label="商家名称:">
					{{ selectedMerchant.name }}
				</el-form-item>
				<el-form-item label="交易额:">
					{{ selectedMerchant.amount }}
				</el-form-item>
				<el-form-item label="交易数量:">
					{{ selectedMerchant.quantity }}
				</el-form-item>
				<el-form-item label="商家等级:">
					<el-select v-model="selectedGrade" class="level-select">
						<el-option v-for="grade in grades" :key="grade" :label="grade" :value="grade"></el-option>
					</el-select>
				</el-form-item>
			</el-form>

			<span slot="footer" class="dialog-footer">
				<el-button @click="cancelLevelDialog">取消</el-button>
				<el-button type="primary" @click="saveMerchantLevel">保存</el-button>
			</span>
		</el-dialog>


		<!-- 分页器 -->
		<div class="pagination">
			<el-pagination :current-page="currentPage" :page-size="pageSize" :total="totalUsers"
				layout="prev, pager, next" @current-change="handlePageChange"></el-pagination>
		</div>

		<!-- 商家商品模态框 -->
		<el-dialog title="商家商品列表" :visible.sync="showProductModal" width="60%">
			<div v-if="selectedMerchant">
				<el-table :data="products" style="width: 100%" border>
					<el-table-column prop="id" label="商品ID"></el-table-column>
					<el-table-column prop="goodsname" label="商品名称"></el-table-column>
					<el-table-column prop="price" label="商品价格"></el-table-column>
					<!-- 其他商品字段... -->
					<el-table-column prop="status" label="商品状态">
						<template slot-scope="{ row }">
							<el-tag :type="getStatusTagType(row.status)">{{ getStatusText(row.status) }}</el-tag>
						</template>
					</el-table-column>
				</el-table>
			</div>
			<div v-if="selectedMerchant && selectedMerchant.status === 'Approved'">
				<el-button type="danger" size="small" @click="Forbidden">禁用店铺</el-button>
			</div>
			<div v-if="selectedMerchant && selectedMerchant.status === 'Rejected'">
				<el-button type="primary" size="small" @click="Recover">开启店铺</el-button>
			</div>
			<div v-if="selectedMerchant && selectedMerchant.status === 'Pending'">
				请先审核该商家！
			</div>
		</el-dialog>
	</div>
</template>

<script>
	import axios from 'axios';

	export default {
		data() {
			return {
				merchants: [], // 商家列表数据
				products: [], // 商品列表数据
				showDialog: false, // 调整等级对话框的显示状态
				showProductModal: false, // 商品模态框显示状态
				selectedMerchant: {}, // 当前选中的商家
				selectedGrade: '', // 选中的商家等级
				grades: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10'], // 商家等级列表
				currentPage: 1,
				pageSize: 2,
				totalUsers: 0,
			};
		},
		mounted() {
			this.fetchMerchants();
		},
		computed: {
			pagedUsers() {
				// 根据当前页码和每页数量计算当前页显示的商品
				const startIndex = (this.currentPage - 1) * this.pageSize;
				const endIndex = startIndex + this.pageSize;
				return this.merchants.slice(startIndex, endIndex);
			},
		},
		methods: {
			fetchMerchants() {
				axios
					.get('http://localhost:8080/Admin/getMerchants')
					.then(response => {
						console.log(response);
						this.merchants = response.data.data;
						this.totalUsers = this.merchants.length;
					})
					.catch(error => {
						console.log(error);
					});
			},
			showMerchantDetails(merchant) {
				axios.get(`http://localhost:8080/Admin/getProductsByMerchantAccount/${merchant.account}`)
					.then(response => {
						this.products = response.data.data;
						console.log(response);
						this.showProductModal = true;
						this.selectedMerchant = merchant;
					})
					.catch(error => {
						console.log(error);
					});
			},
			showLevelDialog(merchant) {
				this.selectedMerchant = merchant;
				this.selectedGrade = merchant.grade;
				this.showDialog = true;
			},
			cancelLevelDialog() {
				this.showDialog = false;
				this.selectedMerchant = {};
				this.selectedGrade = '';
			},
			saveMerchantLevel() {
				if (this.selectedMerchant && this.selectedGrade) {
					const Marchent = {
						id: this.selectedMerchant.id,
						grade: this.selectedGrade,
						amount: this.selectedMerchant.amount,
						quantity: this.selectedMerchant.quantity,
					}
					axios
						.put(
							'http://localhost:8080/Admin/updateMerchantLevel',
							Marchent
						)
						.then(response => {
							console.log(response.data.data);
							console.log(response.data.data.grade);
							this.fetchMerchants();
							this.cancelLevelDialog();
						})
						.catch(error => {
							console.log(error);
						});
				}
			},
			
			Forbidden() {
				// 下架全部商品的逻辑
				if (this.selectedMerchant) {
					// 发起下架全部商品的请求，例如使用axios库
					axios
						.post(`http://localhost:8080/Admin/withdrawAllProducts/${this.selectedMerchant.account}`)
						.then(response => {
							// 下架成功，处理相应的逻辑
							console.log(response);
							this.showProductModal = false;
							this.fetchMerchants();
							alert('已禁用该商铺');
						})
						.catch(error => {
							// 处理错误情况
							console.log('下架商品出错', error);
						});
				}
			},
			Recover() {
				// 下架全部商品的逻辑
				if (this.selectedMerchant) {
					// 发起下架全部商品的请求，例如使用axios库
					axios
						.post(`http://localhost:8080/Admin/recoverAllProducts/${this.selectedMerchant.account}`)
						.then(response => {
							// 下架成功，处理相应的逻辑
							console.log(response);
							this.showProductModal = false;
							this.fetchMerchants();
							alert('已重新开放该商铺');
						})
						.catch(error => {
							// 处理错误情况
							console.log('上架商品出错', error);
						});
				}
			},
			handlePageChange(currentPage) {
				// 分页器页码改变时的回调函数
				this.currentPage = currentPage;
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
		},
	};
</script>

<style>
	.admin-panel {
		padding: 20px;
	}

	.level-dialog {
		width: 700px;
		max-width: 90%;
		margin: 0 auto;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.level-dialog .el-dialog__body {
		flex-grow: 1;
		overflow-y: auto;
	}

	.dialog-footer {
		display: flex;
		justify-content: flex-end;
		margin-top: 20px;
		padding-top: 20px;
		border-top: 1px solid #ccc;
	}

	.dialog-footer button {
		margin-left: 10px;
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

	.button-group {
		display: flex;
		align-items: center;
		gap: 10px;
	}

	.el-table {
		white-space: nowrap;
	}

	.el-table__body-wrapper {
		overflow-x: auto;
	}
</style>
