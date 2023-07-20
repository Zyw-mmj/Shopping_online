<template>
	<div class="register-management">
		<h2>注册审核列表</h2>
		<el-table :data="pagedUsers" border stripe class="custom-table">
			<el-table-column prop="name" label="用户名"></el-table-column>
			<el-table-column prop="account" label="账号"></el-table-column>
			<el-table-column prop="email" label="邮箱"></el-table-column>
			<el-table-column prop="shoptype" label="用户类型"></el-table-column>
			<el-table-column prop="status" label="审核状态">
				<template slot-scope="{ row }">
					<el-tag :type="getStatusTagType(row.status)" class="custom-tag">{{ getStatusText(row.status) }}
					</el-tag>
				</template>
			</el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<div class="button-group">
						<el-button type="primary" @click="approveUser(scope.row)" size="mini" class="action-button">
							审核通过</el-button>
						<el-button type="danger" @click="rejectUser(scope.row)" size="mini" class="action-button">
							审核不通过</el-button>
					</div>
				</template>
			</el-table-column>
		</el-table>

		<!-- 分页器 -->
		<div class="pagination">
			<el-pagination :current-page="currentPage" :page-size="pageSize" :total="totalUsers"
				layout="prev, pager, next" @current-change="handlePageChange"></el-pagination>
		</div>

	</div>
</template>

<script>
	import axios from 'axios';
	export default {
		data() {
			return {
				userInfoList: [],
				userId: 0,
				currentPage: 1,
				pageSize: 3,
				totalUsers: 0,
			}
		},
		mounted() {
			this.fetchUsers();
			this.fetchBusiness();
		},
		computed: {
			pagedUsers() {
				// 根据当前页码和每页数量计算当前页显示的商品
				const startIndex = (this.currentPage - 1) * this.pageSize;
				const endIndex = startIndex + this.pageSize;
				return this.userInfoList.slice(startIndex, endIndex);
			},
		},
		methods: {
			fetchUsers() {
				var that = this;
				axios.get('http://localhost:8080/User/getUserDetail')
					.then(response => {
						console.log(response.data.data);
						// that.users = response.data.data;
						that.userInfoList = that.userInfoList.concat(response.data.data);
						this.totalUsers = this.userInfoList.length;
					})
			},
			fetchBusiness() {
				var that = this;
				axios.get('http://localhost:8080/Business/getBusinesses')
					.then(response => {
						console.log(response.data.data);
						// that.users = response.data.data;
						that.userInfoList = that.userInfoList.concat(response.data.data);
						this.totalUsers = this.userInfoList.length;
					})
			},
			approveUser(user) {
				if(user.shoptype === "个体用户"){
					axios.post('http://localhost:8080/Admin/approveUser', {
							userId: user.id
						})
						.then(response => {
							// 更新用户状态
							this.updateUserStatus(user.id, 'Approved');
							console.log(response.data);
						})
						.catch(error => {
							console.error(error);
						});
				}else{
					axios.post('http://localhost:8080/Admin/approveBusiness', {
							userId: user.id
						})
						.then(response => {
							// 更新用户状态
							this.updateUserStatus(user.id, 'Approved');
							console.log(response.data);
						})
						.catch(error => {
							console.error(error);
						});
				}
			},
			rejectUser(user) {
				if(user.shoptype === "个体用户"){
					axios.post('http://localhost:8080/Admin/rejectUser', {
							userId: user.id
						})
						.then(response => {
							// 更新用户状态
							this.updateUserStatus(user.id, 'Rejected');
							console.log(response.data);
						})
						.catch(error => {
							console.error(error);
						});
				}else{
					axios.post('http://localhost:8080/Admin/rejectBusiness', {
							userId: user.id
						})
						.then(response => {
							// 更新用户状态
							this.updateUserStatus(user.id, 'Rejected');
							console.log(response.data);
						})
						.catch(error => {
							console.error(error);
						});
				}
			},
			updateUserStatus(userId, status) {
				// 更新用户状态
				const user = this.userInfoList.find(u => u.id === userId);
				if (user) {
					user.status = status;
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
					default:
						return '';
				}
			},
			getStatusText(status) {
				switch (status) {
					case 'Pending':
						return '待审核';
					case 'Approved':
						return '审核通过';
					case 'Rejected':
						return '审核不通过';
					default:
						return '';
				}
			},
			handlePageChange(currentPage) {
				// 分页器页码改变时的回调函数
				this.currentPage = currentPage;
			},
		}
	}
</script>
<style scoped>
	.button-group {
		display: flex;
		align-items: center;
		gap: 10px;
	}

	.action-button {
		display: flex;
		gap: 10px;
		/* 按钮之间的间距 */
		justify-content: flex-start;
		/* 左对齐按钮 */
	}

	.custom-table {
		margin-top: 20px;
	}

	.custom-tag {
		padding: 4px 10px;
		font-size: 12px;
	}

	.el-table {
		border: 1px solid #ebeef5;
		border-radius: 4px;
	}

	.el-table__header {
		background-color: #f5f7fa;
	}

	.el-table__body {
		background-color: #fff;
	}

	.el-table__row {
		line-height: 40px;
		/* 根据实际需要调整行高 */
	}

	.el-table th {
		min-width: 100px;
		/* 根据实际需要调整列宽 */
	}

	.el-table td {
		min-width: 100px;
		/* 根据实际需要调整列宽 */
	}

	.el-table th {
		font-weight: bold;
		color: #333;
		background-color: #f5f7fa;
		border-bottom: 1px solid #ebeef5;
		padding: 10px;
	}

	.el-table__row:nth-child(even) {
		background-color: #f9f9f9;
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
</style>
