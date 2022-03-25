<template>
 <div>
   <div>


   </div>
   <el-row :gutter="12" >
     <!-- date遍历循环的数据 -->
     <el-col :span="6" v-for="item in players" :key="item.id" style="flex: auto">
       <el-card shadow="hover">
         <!-- 卡片显示的内容 -->
         <div>
           <img :src="item.avatarUrl" alt=""
                   style="width: 30px;border-radius: 50%;position: relative;top:10px;right: 5px"/>
         </div>
         <div>
           <el-descriptions class="margin-top" :title="item.name" :column="3" border>
             <el-descriptions-item>
               <template slot="label">
                 <i class="el-icon-user"></i>
                 Name
               </template>
               {{item.name}}
             </el-descriptions-item>
             <el-descriptions-item>
               <template slot="label">
                 <i class="el-icon-mobile-phone"></i>
                 手机号
               </template>
               18100000000
             </el-descriptions-item>
             <el-descriptions-item>
               <template slot="label">
                 <i class="el-icon-location-outline"></i>
                 居住地
               </template>
               苏州市
             </el-descriptions-item>
             <el-descriptions-item>
               <template slot="label">
                 <i class="el-icon-tickets"></i>
                 备注
               </template>
               <el-tag size="small">学校</el-tag>
             </el-descriptions-item>
             <el-descriptions-item>
               <template slot="label">
                 <i class="el-icon-office-building"></i>
                 联系地址
               </template>
               江苏省苏州市吴中区吴中大道 1188 号
             </el-descriptions-item>
           </el-descriptions>
         </div>
       </el-card>
     </el-col>
   </el-row>

   <!--    分页插件    -->
   <div style="padding: 10px 0">
     <el-pagination
         @size-change="handleSizeChange"
         @current-change="handleCurrentChange"
         :current-page="pageNum"
         :page-sizes="[5, 8, 10, 15]"
         :page-size="pageSize"
         layout="total, sizes, prev, pager, next, jumper"
         :total="total">
     </el-pagination>
   </div>

 </div>


</template>

<script>

import request from "@/utils/request";

export default {
  name: "Home",
  data(){
    return{
      vis:false,
      players:[],
      pageNum:1,
      pageSize:2,
      total:0

    }
  },
  methods:{
    load(){
      request.get("/player/findPlayers").then(res=>{
            console.log(res)
            this.players=res.data
          }
      )
    },
    handleSizeChange(){},
    handleCurrentChange(){},


  },
  created() {
    this.load()
  },
  mounted(){// 页面元素渲染之后再触发

  }

}



</script>

<style scoped>
.avatar {
  width: 138px;
  height: 138px;
  display: block;
}
</style>