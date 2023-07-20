package com.example.webstore.controller;


import com.example.webstore.domain.GoodsBo;
import com.example.webstore.domain.ResultInfo;
import com.example.webstore.service.IFeedbackService;
import com.example.webstore.service.IGoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Goods")
@Slf4j
public class GoodsController {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IFeedbackService feedbackService;


    @GetMapping("/getGoods")
    public ResultInfo getGoods() {  // 查
        log.info("成功运行！");
        List<GoodsBo> goodsList = goodsService.getGoods();
        log.info("Goods Bos:{}", goodsList);

        return ResultInfo.success(goodsList);
    }

    @GetMapping("/searchGoods")
    public ResultInfo searchGoods(@RequestParam("keyword") String keyword) {  // 模糊查询
        log.info("成功运行！12312313");
        List<GoodsBo> goodsList = new ArrayList<>();

        // 根据关键字从数据库中获取符合条件的商品数据
        if (keyword != null && !keyword.isEmpty()) {
            goodsList = goodsService.findByTitleContaining(keyword);
        } else {
            goodsList = goodsService.getGoods();
        }

        log.info("Goods Bos:{}", goodsList);

        return ResultInfo.success(goodsList);
    }



    @GetMapping("/getGoodsImages2")
    public void getUserImage2(@RequestParam("id") String id, HttpServletResponse response) throws IOException {
        GoodsBo goodBo = goodsService.getGoodsByid(id);
        String path=goodBo.getPicture();
        log.info(path);
        FileInputStream inputStream = new FileInputStream(path);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        inputStream.close();
        response.setContentType("image/jpeg"); // 设置响应头
        response.getOutputStream().write(bytes); // 将图片数据写入响应输出流
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
    @GetMapping("/getGoodsById/{productId}")
    public ResultInfo getGoodsById(@PathVariable String productId) {  // 查
        log.info("成功运行！");
        //按照用户评价修改指定商品评分
//        Integer product_Id = Integer.valueOf(productId);
//        List<Integer> Allrating = feedbackService.getTotalRating(product_Id);
//        int sum = 0;
//        for (Integer integer : Allrating) {
//            sum += integer;
//        }
//        if(Allrating.size()!=0){
//            int rating = sum/Allrating.size();
//            Long product_id = Long.valueOf(productId);
//            Boolean t = goodsService.updategoodsRating(product_id,rating);
//        }
        GoodsBo goodBo = goodsService.getGoodsByid(productId);
        log.info("Goods Bos:{}", goodBo);

        return ResultInfo.success(goodBo);
    }


    @GetMapping("/getGoodsType")
    public ResultInfo getGoodsType() {  // 查
        log.info("成功运行！");
        List<String> goodTypes = goodsService.getGoodsType();
        log.info("Goods Bos:{}", goodTypes);

        return ResultInfo.success(goodTypes);
    }

    @GetMapping("/getGoodsByBusinessId")
    public ResultInfo getGoodsByBusinessId(@RequestParam("businessId") String businessId){
        log.info("获取商户的商品————————————————————————————————！");
        List<GoodsBo> goodsBos=goodsService.getGoodsByBusinessId(businessId);
        log.info("Goods Bos:{}", goodsBos);
        return ResultInfo.success(goodsBos);
    }

    @PostMapping("/addGoods")
    public ResultInfo addGoods(@RequestParam("file") MultipartFile file, @RequestParam("name") String goodsname,@RequestParam("type") String categorie,
                               @RequestParam("size") String size,@RequestParam("description") String describes,@RequestParam("quantity") int quantity,
                               @RequestParam("price") Double price,@RequestParam("paymentMethods") String tradeway,@RequestParam("businessId") String businessId)throws IOException{
        Long stock= Long.parseLong(String.valueOf(quantity)) ;
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        Path path = Paths.get("src/main/images/goodImages",timeStamp+"_"+file.getOriginalFilename());
        Files.copy(file.getInputStream(), path);
        // 手动删除临时文件夹中的临时文件   未完成
        file.getInputStream().close();
        // 将图片路径存储在数据库中
        String imagePath = path.toAbsolutePath().toString();
        GoodsBo goodsBo=new GoodsBo(goodsname,categorie,size,describes,stock,price,tradeway,businessId,imagePath);
        Boolean t= this.goodsService.createGoods(goodsBo);
        if (t){
            return ResultInfo.success(1);
        }else {
            return ResultInfo.error("创建失败");
        }
    }

    @DeleteMapping("/deleteGoods")
    public ResultInfo deleteGoods(@RequestParam("id") String id){
//        String theId=String.valueOf(id);
        Boolean t= this.goodsService.deleteGoods(id);
        if (t){
            return ResultInfo.success(1);
        }else {
            return ResultInfo.error("删除出错");
        }
    }

    @PutMapping("/updateProductStatus/{productId}")
    public ResultInfo updateProductStatus(@PathVariable String productId) {      // 改变商品状态
        log.info(productId);
        // 根据 productId 获取商品对象
        GoodsBo goodBo = goodsService.getGoodsByid(productId);
        // 更新商品状态
        // 根据实际需求修改商品状态{
        goodBo.setStatus("Approved");
        goodsService.saveGood(goodBo);
        log.info("成功运行！");

        return ResultInfo.success(goodBo);
    }

    @PutMapping("/updateProductStatus1/{productId}")
    public ResultInfo updateProductStatus1(@PathVariable String productId) {      // 改变商品状态
        log.info(productId);
        // 根据 productId 获取商品对象
        GoodsBo goodBo = goodsService.getGoodsByid(productId);
        // 更新商品状态
        // 根据实际需求修改商品状态{
        goodBo.setStatus("Rejected");
        goodsService.saveGood1(goodBo);
        log.info("成功运行！");

        return ResultInfo.success(goodBo);
    }

    @GetMapping("/getOrderGoods")
    public ResultInfo getOrderGoods(@RequestParam("account") String account) {  // 查
        log.info("成功运行！");
        List<GoodsBo> goodsBos=goodsService.findOrderGoods(account);
        log.info("Goods Bos:{}", goodsBos);
        return ResultInfo.success(goodsBos);
    }

    @PostMapping ("/offGoods")
    public ResultInfo offGoods(@RequestBody Map map){
        Integer s= (Integer) map.get("id");
        Long theId=Long.valueOf(String.valueOf(s) );
        Boolean t= this.goodsService.updateProductStatusOff(theId);
        if (t){
            return ResultInfo.success(1);
        }else {
            log.info("下架失败");
            return ResultInfo.error("下架失败");
        }
    }
    @GetMapping("/getShippedGoods")
    public ResultInfo getShippedGoods(@RequestParam("account") String account) {  // 查
        log.info("成功运行！");
        List<GoodsBo> goodsBos=goodsService.findShippedOrder(account);
        log.info("Goods Bos:{}", goodsBos);
        return ResultInfo.success(goodsBos);
    }

    @GetMapping("/getRefundGoods")
    public ResultInfo getRefundGoods(@RequestParam("account") String account) {  // 查
        log.info("成功运行！");
        List<GoodsBo> goodsBos=goodsService.findRefundOrder(account);
        log.info("Goods Bos:{}", goodsBos);
        return ResultInfo.success(goodsBos);
    }
    @GetMapping("/getFinishedGoods")
    public ResultInfo getFinishedGoods(@RequestParam("account") String account) {  // 查
        log.info("成功运行！");
        List<GoodsBo> goodsBos=goodsService.findFinishedOrder(account);
        log.info("Goods Bos:{}", goodsBos);
        return ResultInfo.success(goodsBos);
    }

    @PostMapping ("/ReOnGoods")
    public ResultInfo ReOnGoods(@RequestBody Map map){
        Integer s= (Integer) map.get("id");
        Long theId=Long.valueOf(String.valueOf(s) );
        Boolean t= this.goodsService.updateProductStatusPending(theId);
        if (t){
            return ResultInfo.success(1);
        }else {
            log.info("重新下架失败");
            return ResultInfo.error("下架失败");
        }
    }

    @GetMapping("/userFindGoing")
    public ResultInfo userFindGoing(@RequestParam("userId") String userId) {  // 查
        log.info("成功运行！");
        Long thid=Long.valueOf(userId);
        List<GoodsBo> goodsBos=goodsService.userFindGoing(thid);
        log.info("Goods Bos:{}", goodsBos);
        return ResultInfo.success(goodsBos);
    }

    @GetMapping("/userFindRefund")
    public ResultInfo userFindRefund(@RequestParam("userId") String userId) {  // 查
        log.info("成功运行！");
        Long thid=Long.valueOf(userId);
        List<GoodsBo> goodsBos=goodsService.userFindRefund(thid);
        log.info("Goods Bos:{}", goodsBos);
        return ResultInfo.success(goodsBos);
    }

    @GetMapping("/userFindPending")
    public ResultInfo userFindPending(@RequestParam("userId") String userId) {  // 查
        log.info("成功运行！");
        Long thid=Long.valueOf(userId);
        List<GoodsBo> goodsBos=goodsService.userFindPending(thid);
        log.info("Goods Bos:{}", goodsBos);
        return ResultInfo.success(goodsBos);
    }

    @GetMapping("/userFindFinished")
    public ResultInfo userFindFinished(@RequestParam("userId") String userId) {  // 查
        log.info("成功运行！");
        Long thid=Long.valueOf(userId);
        List<GoodsBo> goodsBos=goodsService.userFindFinished(thid);
        return ResultInfo.success(goodsBos);
    }

}
