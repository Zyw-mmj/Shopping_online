<template>
	<div>
		<el-menu :default-active="setActiveIndex" mode="horizontal" background-color="#545c64" text-color="#fff"
			active-text-color="#ffd04b" router>
			<el-menu-item v-for="(item, i) in lists" :key="i" :index="item.index" :class="item.class">
				<i :class="item.i_class"></i>{{ item.des }}
			</el-menu-item>
		</el-menu>
	</div>
</template>

<script>
	export default {
		data() {
			return {
				// setActiveIndex: '',
				menuItem: {
					index: '',
					name: '',
				},

			}
		},
		props: {
			isBusiness: Boolean
		},
		computed: {
			// 页面头部渲染列表
			lists: function() {
				if (!this.isBusiness) {
					// 用户页面
					return [{
							index: '/',
							class: '',
							i_class: 'el-icon-shopping-bag-1',
							des: '网上购物系统'
						},
						{
							index: '/UserReviews',
							class: '',
							i_class: 'el-icon-s-comment',
							des: '历史评价'
						},
						{
							index: this.menuItem.index,
							class: 'item',
							i_class: 'el-icon-s-check',
							des: this.menuItem.name
						},
						{
							index: '/MyGoods',
							class: 'item',
							i_class: 'el-icon-star-on',
							des: '我的物品'
						},
						{
							index: '/order',
							class: 'item',
							i_class: 'el-icon-s-order',
							des: '我的订单',
						},
						{
							index: '/shopCart',
							class: 'item',
							i_class: 'el-icon-shopping-cart-2',
							des: '购物车'
						},
						{
							index: '/UserWallet',
							class: 'item',
							i_class: 'el-icon-wallet',
							des: '我的钱包'
						},
						{
							index: '/couponRelease',
							class: 'item',
							i_class: 'el-icon-shopping-cart-1',
							des: '优惠卷领取'
						}
					];
				} else {
					// 管理员页面
					return [{
							index: '/business',
							class: '',
							i_class: 'el-icon-shopping-bag-1',
							des: '网上购物系统-商品橱窗'
						},
						{
							index: this.menuItem.index,
							class: 'item',
							i_class: 'el-icon-s-check',
							des: this.menuItem.name
						},
						// {
						//     index: '/business/login',
						//     class: 'item',
						//     i_class: 'el-icon-s-check',
						//     des: '登录'
						// },
						{
							index: '/business/order',
							class: 'item',
							i_class: 'el-icon-s-order',
							des: '订单管理'
						},
						{
							index: '/business/goods',
							class: 'item',
							i_class: 'el-icon-shopping-cart-2',
							des: '商品管理'
						},
						// {
						// 	index: '/admin',
						// 	class: '',
						// 	i_class: 'el-icon-shopping-bag-1',
						// 	des: '网上购物系统-上架商品'
						// },
						// {
						// 	index: '/',
						// 	class: 'item',
						// 	i_class: 'el-icon-error',
						// 	des: '退出'
						// },
						// {
						// 	index: '/adminOrder',
						// 	class: 'item',
						// 	i_class: 'el-icon-s-order',
						// 	des: '订单'
						// },
					]
				}
			},
			setActiveIndex() {
				if (this.isBusiness) {
					return this.$route.path; // 设置为你想要的激活状态的值
				} else {
					return '/'; // 设置为你想要的非激活状态的值
				}
			}
		},

		methods: {},
		created() {
			console.info(this.$route.path);
			if (!this.isBusiness) {
				if (!localStorage.getItem('user')) {
					this.menuItem.index = '/login';
					this.menuItem.name = '登陆 / 注册';
				} else {
					this.menuItem.index = '/user';
					this.menuItem.name = '个人中心';
				}
			} else {
				if (!localStorage.getItem('business')) {
					this.menuItem.index = '/business/login';
					this.menuItem.name = '登陆  ';
				} else {
					this.menuItem.index = '/business/Info';
					this.menuItem.name = '商户信息';
				}
			}
			console.log(this.$route.path);
		},
		beforeUpdate() {
			console.info(this.$route.path);
			if (!this.isBusiness) {
				if (!localStorage.getItem('user')) {
					this.menuItem.index = '/login';
					this.menuItem.name = '登陆 / 注册';
				} else {
					this.menuItem.index = '/user';
					this.menuItem.name = '个人中心';
				}
			} else {
				if (!localStorage.getItem('business')) {
					this.menuItem.index = '/business/login';
					this.menuItem.name = '登陆  ';
				} else {
					this.menuItem.index = '/business/Info';
					this.menuItem.name = '商户信息';
				}
			}
			console.log(this.$route.path);
		}
	};
</script>

<style scoped>
	.item {
		float: right !important;
	}
</style>
