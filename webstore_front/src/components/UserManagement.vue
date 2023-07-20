<template>
	<div class="user-management">
		<el-table :data="pagedUsers" border stripe class="custom-table">
			<el-table-column prop="name" label="用户名"></el-table-column>
			<el-table-column prop="email" label="邮箱"></el-table-column>
			<el-table-column prop="phonenumber" label="手机号码"></el-table-column>
			<el-table-column prop="shoptype" label="用户类型"></el-table-column>
			<el-table-column label="操作">
				<template slot-scope="scope">
					<div class="button-group">
						<el-button type="primary" @click="editUser(scope.row)" size="mini" class="action-button">
							修改
						</el-button>
						<el-button type="danger" @click="editBan(scope.row)" size="mini" class="action-button">禁用
						</el-button>
					</div>
				</template>
			</el-table-column>
		</el-table>

		<el-dialog :visible="showEditDialog" v-model="showEditDialog" @close="closeEditDialog">
			<el-form>
				<el-form-item label="用户名">
					<el-input v-model="editingUser.name"></el-input>
				</el-form-item>
				<el-form-item label="邮箱">
					<el-input v-model="editingUser.email"></el-input>
				</el-form-item>
				<el-form-item label="手机号码">
					<el-input v-model="editingUser.phonenumber"></el-input>
				</el-form-item>
			</el-form>
			<div slot="footer">
				<el-button @click="saveUserChanges" type="primary">保存</el-button>
				<el-button @click="closeEditDialog">取消</el-button>
			</div>
		</el-dialog>

		<el-dialog :visible="showDialog" v-model="showEditDialog" @close="closeEditDialog">
			<span>请选择禁用或恢复用户 "{{ editingUser.name }}"</span>
			<span>用户ID: {{ editingUser.id }}</span>
			<div slot="footer"> 
				<el-button @click="Ban" type="primary">禁用</el-button>
				<el-button @click="Recover">恢复</el-button>
			</div>
		</el-dialog>
		
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
				showEditDialog: false,
				showDialog: false,
				editingUser: {},
				currentPage: 1,
				pageSize: 2,
				totalUsers: 0,
			};
		},
		mounted() {
			this.fetchUsers();
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
						that.userInfoList = response.data.data;
						that.totalUsers = that.userInfoList.length;
						
					})
					.catch(error => {
						console.error(error);
					});
			},
			Ban() {
				const user = {
					id: this.editingUser.id,
				}
				axios.post('http://localhost:8080/Admin/rejectUser', {
						userId: user.id
					})
					.then(response => {
						// 更新用户状态
						this.updateUserStatus(user.id, 'Rejected');
						console.log(response.data);
						this.showDialog = false;
					})
					.catch(error => {
						console.error(error);
					});
			},
			Recover() {
				const user = {
					id: this.editingUser.id,
				}
				axios.post('http://localhost:8080/Admin/approveUser', {
						userId: user.id
					})
					.then(response => {
						// 更新用户状态
						this.updateUserStatus(user.id, 'Approved');
						console.log(response.data);
						this.showDialog = false;
					})
					.catch(error => {
						console.error(error);
					});
			},
			editUser(user) {
				this.editingUser = {
					...user
				}; // 创建一个副本以避免直接修改原始对象
				console.log(this.editingUser);
				this.showEditDialog = true; // 打开编辑对话框
			},
			editBan(user) {
				this.editingUser = {
					...user
				}; // 创建一个副本以避免直接修改原始对象
				console.log(this.editingUser);
				this.showDialog = true; // 打开编辑对话框
			},
			saveUserChanges() {
				//向服务器发送修改用户信息的请求
				const user = {
					id: this.editingUser.id,
					name: this.editingUser.name,
					email: this.editingUser.email,
					phonenumber: this.editingUser.phonenumber
				}
				console.log(user);
				axios.put('http://localhost:8080/Admin/updateUser', user)
					.then(response => {
						// 请求成功的处理逻辑
						console.log(response.data);
						this.fetchUsers();
					})
					.catch(error => {
						// 请求失败的处理逻辑
						console.error(error);
					});
				// 关闭编辑对话框
				this.closeEditDialog();
			},
			updateUserStatus(userId, status) {
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
			closeEditDialog() {
				this.showEditDialog = false;
				this.showDialog = false;
				this.editingUser = {}; // 重置正在编辑的用户对象
			},
			handlePageChange(currentPage) {
				// 分页器页码改变时的回调函数
				this.currentPage = currentPage;
			},
		},
	};
</script>

<style scoped>
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
	}

	.el-table th {
		min-width: 100px;
	}

	.el-table td {
		min-width: 100px;
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

	.button-group {
		display: flex;
		align-items: center;
		gap: 10px;
	}

	.action-button {
		display: flex;
		gap: 10px;
		justify-content: flex-start;
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
