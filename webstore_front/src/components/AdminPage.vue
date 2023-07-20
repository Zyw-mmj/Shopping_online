<template>
	<div class="admin-page">
		<div class="sidebar">
			<!-- 管理员侧边栏 -->
			<el-menu default-active="1" @select="handleMenuSelect">
				<el-menu-item index="1">
					<i class="el-icon-s-home"></i>
					<span>商品管理</span>
				</el-menu-item>
				<el-menu-item index="2">
					<i class="el-icon-s-goods"></i>
					<span>注册管理</span>
				</el-menu-item>
				<el-menu-item index="3">
					<i class="el-icon-s-order"></i>
					<span>用户管理
					</span>
				</el-menu-item>
				<el-menu-item index="4">
					<i class="el-icon-s-data"></i>
					<span>商家管理
					</span>
				</el-menu-item>
			</el-menu>
		</div>
		<div class="content">
			<!-- 管理员内容区域 -->
			<!-- <router-view></router-view> -->
			<template v-if="activeMenu  === '1'">
				<h1>商品管理</h1>
				<!-- 显示首页相关内容 -->
				<product-management></product-management>
			</template>
			<template v-else-if="activeMenu === '2'">
				<!-- <h1>用户管理</h1> -->
				<!-- 显示数据统计相关内容 -->
				<register-management></register-management>
			</template>
			<template v-else-if="activeMenu === '3'">
				<h1>用户管理</h1>
				<!-- 显示数据统计相关内容 -->
				<UserManagement></UserManagement>
			</template>
			<template v-else-if="activeMenu === '4'">
				<!-- <h1>卖家等级调整</h1> -->
				<!-- 显示数据统计相关内容 -->
				<AdminPanel></AdminPanel>
			</template>
		</div>
	</div>
</template>

<script>
	import ProductManagement from './ProductManagement.vue';
	import RegisterManagement from './RegisterManagement.vue';
	import UserManagement from './UserManagement.vue';
	import AdminPanel from './AdminPanel.vue';
	export default {
		name: 'AdminPage',
		components: {
			ProductManagement,
			RegisterManagement,
			UserManagement,
			AdminPanel,
		},
		data() {
			return {
				activeMenu: '1', // 当前选中的菜单项，默认为首页
			};
		},
		methods: {
			handleMenuSelect(index) {
				this.activeMenu = index; // 更新当前选中的菜单项
			},
		},
		// 组件样式
		styles: {
			// 页面容器样式
			'.admin-page': {
				display: 'flex',
				height: '100vh',
				backgroundColor: '#f5f5f5',
			},
			// 侧边栏样式
			'.sidebar': {
				width: '200px',
				backgroundColor: '#fff',
			},
			// 内容区域样式
			'.content': {
				flex: '1',
				padding: '20px',
			},
		},
	};
</script>

<style scoped>
	/* 组件样式 */
	.admin-page {
		display: flex;
		height: 100vh;
		background-color: #f5f5f5;
	}

	.sidebar {
		width: 200px;
		background-color: #fff;
	}

	.content {
		flex: 1;
		padding: 20px;
	}
</style>
