<template>
  <!-- <div class="hello">
    <h1>hell发的说法o {{data}}</h1>
  </div> -->
  <el-container>
    <el-header>Header {{data}}</el-header>
    <el-main>Main</el-main>
  </el-container>
</template>


<script>
	import axios from "axios"
	export default {
	  name: 'HelloWorld',
	  props: {
		msg: String
	  },
	  data() {
	  	return {
			data:"45613"
		}
	  },
	  beforeCreate:function(){
	  	console.log('beforeCreate');
	  	axios.get('http://localhost:8080/home/b').then(res=>{
	  	    console.log(res.data);
	  		this.data=res.data;
	  	}).catch(err=>{
	  	    console.log("获取数据失败" + err);
	  	})
	  	console.log("-----------");
	  	// axios.get('http://127.0.0.1/worklist/done').then(res=>{
	  	//     console.log(res.data);
	  	// 	this.doneData=res.data;
	  	// }).catch(err=>{
	  	//     console.log("获取数据失败" + err);
	  	// })
	  }
	  
	}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style>
  .el-header, .el-footer {
    background-color: #B3C0D1;
    color: #333;
    text-align: center;
    line-height: 60px;
  }
  
  .el-aside {
    background-color: #D3DCE6;
    color: #333;
    text-align: center;
    line-height: 200px;
  }
  
  .el-main {
    background-color: #E9EEF3;
    color: #333;
    text-align: center;
    line-height: 160px;
  }
  
  body > .el-container {
    margin-bottom: 40px;
  }
  
  .el-container:nth-child(5) .el-aside,
  .el-container:nth-child(6) .el-aside {
    line-height: 260px;
  }
  
  .el-container:nth-child(7) .el-aside {
    line-height: 320px;
  }
</style>
