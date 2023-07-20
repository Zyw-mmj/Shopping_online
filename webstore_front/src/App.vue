<template>
	<div id="page-container">
		<el-container class="el-container">
			<el-header class="header">
				<Header :isBusiness="isBusiness" />
			</el-header>
			<el-main>
				<router-view></router-view>
			</el-main>
			<el-footer class="footer">
				<Footer />
			</el-footer>
		</el-container>
	</div>
</template>

<script>
	import Header from './components/Header.vue'
	import Footer from './components/Footer.vue'

	export default {
		name: 'App',
		components: {
			Header,
			Footer
		},
		watch: {
			$route() {

			}
		},
		computed: {
			isBusiness: function() {
				// 判断页面
				// console.log(this.$route.path);

				if (this.$route.path == '/business' || this.$route.path == '/business/login' || this.$route.path ==
					'/business/register' ||
					this.$route.path == '/business/order' || this.$route.path == '/business/goods' || this.$route
					.path == '/business/Info') {
					return true
				} else {
					return false
				}
			}
		}
	}
</script>

<style>
	html,
	body {
		height: 100%;
		margin: 0;
		padding: 0;
	}

	#page-container {
		display: flex;
		flex-direction: column;
		min-height: 100vh;
	}

	.header {
		background-color: #545c64;
		padding: 0 60px !important;
	}

	.footer {
		background-color: #545c64;
		height: 60px !important;
		bottom: 0px;
	}
</style>
