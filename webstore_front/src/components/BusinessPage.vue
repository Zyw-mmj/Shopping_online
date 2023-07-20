<template>
	<div class="order-management">
		<div class="sidebar">
			<!-- 管理员侧边栏 -->
			<el-menu default-active="1" @select="handleMenuSelect">
				<el-menu-item index="1">
					<i class="el-icon-s-order"></i>
					<span>待发货订单</span>
				</el-menu-item>
				<el-menu-item index="2">
					<i class="el-icon-s-check"></i>
					<span>已发货订单</span>
				</el-menu-item>
				<el-menu-item index="3">
					<i class="el-icon-s-order"></i>
					<span>退货订单</span>
				</el-menu-item>
				<el-menu-item index="4">
					<i class="el-icon-s-check"></i>
					<span>已完成订单</span>
				</el-menu-item>
			</el-menu>
		</div>
		<div class="content">
			<!-- 订单内容区域 -->
			<template v-if="activeMenu === '1'">
				<BusinessOrder></BusinessOrder>
			</template>
			<template v-else-if="activeMenu === '2'">
				<BusinessShippedOrder></BusinessShippedOrder>
			</template>
			<template v-else-if="activeMenu === '3'">
				<BusinessRefundOrder></BusinessRefundOrder>
			</template>
			<template v-else-if="activeMenu === '4'">
				<BusinessFinishedOrder></BusinessFinishedOrder>
			</template>
		</div>
	</div>
</template>

<script>
	import BusinessOrder from './BusinessOrder.vue';
	import BusinessShippedOrder from './BusinessShippedOrder.vue';
	import BusinessRefundOrder from './BusinessRefundOrder.vue'
	import BusinessFinishedOrder from './BusinessFinishedOrder.vue'
	// import BusinessOrder from './BusinessOrder.vue ;
	// import ShippedOrders from './ShippedOrders.vue';
	// import ReturnOrders from './ReturnOrders.vue';
	// import CompletedOrders from './CompletedOrders.vue';

	export default {
		name: 'OrderManagement',
		components: {
			BusinessOrder,
			BusinessShippedOrder,
			BusinessRefundOrder,
			BusinessFinishedOrder,
		},
		data() {
			return {
				activeMenu: '1', // 当前选中的菜单项，默认为待发货订单
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
			'.order-management': {
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
	.order-management {
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
>
