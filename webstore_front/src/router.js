import Vue from 'vue';
import Router from 'vue-router';
// import Footer from './components/Footer.vue';
// import Header from './components/Footer.vue';
import LoginOne from './components/LoginOne.vue'
import RegisterOne from './components/RegisterOne.vue'
import Goods from './components/Goods.vue'
import User from './components/User.vue'
import LoginTwoVue from './components/LoginTwo.vue';
import RegisterTwoVue from './components/RegisterTwo.vue';
import BusinessInfoVue from './components/BusinessInfo.vue';
import BusinessVue from './components/Business.vue';
import BusinessGoodsVue from './components/BusinessGoods.vue';
import ProductDetail from './components/ProductDetail.vue';
import ShopCart from './components/ShopCart.vue';
import AdminPage from './components/AdminPage.vue';
import ProductManagement from './components/ProductManagement.vue';
import RegisterManagement from './components/RegisterManagement.vue';
import BusinessPageVue from './components/BusinessPage.vue';
import coupons from './components/coupons.vue'
import Order from './components/Order.vue'
import UserWallet from './components/UserWallet.vue'
import MyGoodsVue from './components/MyGoods.vue';
import UserReviews from './components/UserReviews.vue';
import couponRelease from './components/couponRelease.vue';
Vue.use(Router);

export default new Router({
	routes: [{
			path: '/',
			name: 'home',
			component: Goods
		},
		{
			path: '/login',
			name: 'login',
			component: LoginOne
		},
		{
			path: '/register',
			name: 'register',
			component: RegisterOne
		},
		{
			path: '/user',
			name: 'user',
			component: User
		},
		{
			path: '/business/login',
			name: 'loginTwo',
			component: LoginTwoVue
		},
		{
			path: '/business/register',
			name: 'registerTwo',
			component: RegisterTwoVue
		},
		{
			path: '/business/info',
			name: 'businessInfo',
			component: BusinessInfoVue
		},
		{
			path: '/business',
			name: 'business',
			component: BusinessVue
		},
		{
			path: '/business/goods',
			name: 'businessGoods',
			component: BusinessGoodsVue
		},
		{
			path: '/products/:id',
			name: 'productDetail',
			component: ProductDetail
		},
		{
			path: '/shopCart',
			name: 'ShopCart',
			component: ShopCart
		},
		{
			path: '/admin',
			name: 'AdminPage',
			component: AdminPage
		},
		{
			path: '/product',
			name: 'ProductManagement',
			component: ProductManagement,
		},
		{
			path: '/registerManagement',
			name: 'RegisterManagement',
			component: RegisterManagement,
		},
		{
			path: '/coupons',
			name: 'coupons',
			component: coupons,
		},
		{
			path: '/business/order',
			name: 'BusinessPageVue',
			component: BusinessPageVue,
		},
		{
			path: '/Order',
			name: 'Order',
			component: Order,
		}, {
			path: '/UserWallet',
			name: 'UserWallet',
			component: UserWallet,
		},
		{
			path: '/MyGoods',
			name: 'Mygoods',
			component: MyGoodsVue,
		},
		{
			path: '/UserReviews',
			name: 'UserReviews',
			component: UserReviews,
		}, 
		{
			path: '/couponRelease',
			name: 'couponRelease',
			component: couponRelease,
		}
	]
});
