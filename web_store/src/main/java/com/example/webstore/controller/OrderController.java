package com.example.webstore.controller;

import com.example.webstore.dao.model.FeeRate;
import com.example.webstore.dao.model.OrderItem;
import com.example.webstore.dao.model.ShopOrder;
import com.example.webstore.domain.*;
import com.example.webstore.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {

    private final IShopOrderService orderService;

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    public OrderController(IShopOrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    private IBusinessService businessService;

    @Autowired
    private IFeeService feeRateService;
//    @GetMapping("/{id}")
//    public ShopOrder getOrderById(@PathVariable("id") Long id) {
//
//        return orderService.getOrderById(id);
//    }
    @Autowired
    private IUserWalletService userWalletService;

    @Autowired
    private IPaymentRecordService paymentRecordService;

    @GetMapping("/{id}")
    public ShopOrder getOrderById(@PathVariable("id") Long id) {

        return orderService.getOrderById(id);
    }

    @GetMapping
    public List<ShopOrder> getAllOrders() {
        return orderService.getAllOrders();
    }

//    @PostMapping
//    public ShopOrder createOrder(@RequestBody ShopOrder order) {
//        return orderService.createOrder(order);
//    }

    @PostMapping
    public ShopOrder createOrder(@RequestBody Map map) {
        Long userId = Long.valueOf(String.valueOf(map.get("userId")));
        Integer total_Price = Integer.valueOf(String.valueOf(map.get("totalPrice")));
        double totalPrice = total_Price.doubleValue();
        System.out.println(totalPrice);
        int payStatus = Integer.parseInt(String.valueOf(map.get("payStatus")));
        int payType = Integer.parseInt(String.valueOf(map.get("payType")));
        int orderStatus = Integer.parseInt(String.valueOf(map.get("orderStatus")));
        String userName = String.valueOf(map.get("userName"));
        String userPhone = String.valueOf(map.get("userPhone"));
        String userAddress = String.valueOf(map.get("userAddress"));
        ShoporderBo shoporderBo = new ShoporderBo(userId,totalPrice,payStatus,payType,orderStatus,userName,userPhone,userAddress);
        return orderService.createOrder(shoporderBo);
    }

    @PutMapping("/{id}")
    public ShopOrder updateOrder(@PathVariable("id") Long id, @RequestBody ShopOrder order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{id}")
    public ResultInfo deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrder(id);
        return ResultInfo.success("成功删除 "+id);
    }

//--------------------------------------------------------------//

//    @GetMapping("/user/{userId}")
//    public List<ShopOrder> getOrdersByUserId(@PathVariable Long userId) {
//        return orderService.getOrdersByUserId(userId);
//    }
    @GetMapping("/user/{userId}")
    public List<ShopOrder> getOrdersByUserId(@PathVariable Long userId) {

        return orderService.getOrdersByUserId(userId);
    }

    @PostMapping("/item")
    public OrderItem createOrderItem(@RequestBody Map map) {
        Long orderId = Long.valueOf(String.valueOf(map.get("orderId")));
        Long goodsId = Long.valueOf(String.valueOf(map.get("goodsId")));
        String goodsName = String.valueOf(map.get("goodsName"));
        Integer sellingPrice = Integer.valueOf(String.valueOf(map.get("sellingPrice")));
        Integer goodsCount = Integer.valueOf(String.valueOf(map.get("goodsCount")));
        OrderItemBo orderItemBo = new OrderItemBo(orderId,goodsId,goodsName,sellingPrice,goodsCount);

        return orderService.createOrderItem(orderItemBo);
    }

    @GetMapping("/{order_id}/items")
    public OrderItem[] getOrderItemsByOrderId(@PathVariable Long order_id) {
        System.out.println(order_id);
        return orderService.getOrderItemsByOrderId(order_id);
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public void deleteOrderItem(@PathVariable Long orderId, @PathVariable Long itemId) {
        orderService.deleteOrderItem(orderId, itemId);
        log.info("已删除购物车内物品");
    }

    @PostMapping("/deliverGoods")
    public ResultInfo deliverGoods(@RequestBody Map map) {
        Long orderId=  Long.valueOf(String.valueOf(map.get("orderId")));
        log.info(orderId.toString());
        Long id=Long.valueOf(String.valueOf(map.get("id")));
        log.info(id.toString());
        int quantity= (int) map.get("quantity");
        log.info(String.valueOf(quantity));
        log.info("_____________________________________________________________________");
        boolean t1=this.goodsService.deliverGoods(id,quantity);
        log.info(String.valueOf(t1));
        boolean t2=this.orderService.deliverGoods(orderId,id);
        log.info(String.valueOf(t2));
        if (t1&&t2){
            return ResultInfo.success(1);
        }else {
            return ResultInfo.error("失败了！！！！！！！！！！！！！");
        }
    }
    @PutMapping("/{orderId}/status/{status}")
    public void updateOrderStatus(@PathVariable Long orderId, @PathVariable int status) {
        orderService.updateOrderStatus(orderId, status);
    }
    @PostMapping("/confirmGoods")
    public ResultInfo confirmGoods(@RequestBody Map map) {
        Long orderId=  Long.valueOf(String.valueOf(map.get("orderId")));
        log.info(orderId.toString());
        Long id=Long.valueOf(String.valueOf(map.get("id")));   //商品的id
        log.info(id.toString());
        int quantity= (int) map.get("quantity");
        double price=Double.valueOf(String.valueOf(map.get("price")));
        BigDecimal x=this.orderService.getItemTotalPrice(orderId,id);
        double geishanghudqiang=Double.valueOf(x.toString());
        String businessAccount=String.valueOf(map.get("businessAccount"));
        log.info(String.valueOf(quantity));
        log.info("_____________________________________________________________________");
        BusinessBo businessBo1 = businessService.getBusinessByAccount(businessAccount);
        FeeRate feeRate = feeRateService.getByGrade(businessBo1.getGrade());
        log.info(String.valueOf(businessBo1.getGrade()));
        log.info("**********************");
        log.info(businessAccount);

        double rate=feeRate.getRate();
        double costFare=geishanghudqiang*1.00*rate;
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedNumber = df.format(costFare);
        String formattedNumber2 = df.format(geishanghudqiang-costFare);
        costFare=Double.valueOf(formattedNumber);
        double valueToBusiness=Double.valueOf(formattedNumber2);
        boolean t1=this.businessService.finishOrder(geishanghudqiang,valueToBusiness,costFare,businessAccount);
        log.info(String.valueOf(t1));
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String finishTime=currentTime.format(formatter);
//        System.out.println(currentTime.format(formatter));
        boolean t2=this.orderService.confirmGoods(orderId,id,finishTime);
        log.info(String.valueOf(t2));
        if (t1&&t2){
            return ResultInfo.success(1);
        }else {
            return ResultInfo.error("失败了！！！！！！！！！！！！！");
        }
    }

    @PostMapping("/checkItemPermission")
    public Boolean checkItemPermission(@RequestBody Map map) {
        Long orderId=  Long.valueOf(String.valueOf(map.get("orderId")));
        Long id=Long.valueOf(String.valueOf(map.get("id")));
        OrderItemBo orderItemBo=this.orderService.getItemsByOrderIdGoods_id(orderId,id);

        String time=orderItemBo.getFinishtime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime finishTime = LocalDateTime.parse(time, formatter);
        LocalDateTime currentTime = LocalDateTime.now();
        if (currentTime.isBefore(finishTime.plusHours(24))){
            return true;
        }else {
            return false;
        }

    }

    @PostMapping("/refundGoods")
    public ResultInfo refundGoods(@RequestBody Map map) {
//        @RequestParam("orderId") Long orderId,
//        @RequestParam("id") Long id,@RequestParam("quantity") int quantity,
        Long orderId=  Long.valueOf(String.valueOf(map.get("orderId")));
        log.info(orderId.toString());
        Long id=Long.valueOf(String.valueOf(map.get("id")));
        log.info(id.toString());
        boolean t2=this.orderService.refundGoods(orderId,id);
        log.info(String.valueOf(t2));
        if (t2){
            return ResultInfo.success(1);
        }else {
            return ResultInfo.error("失败了！！！！！！！！！！！！！");
        }
    }

    @PostMapping("/undoGoods")
    public ResultInfo undoGoods(@RequestBody Map map) {
        Long orderId=  Long.valueOf(String.valueOf(map.get("orderId")));
        log.info(orderId.toString());
        Long id=Long.valueOf(String.valueOf(map.get("id")));
        log.info(id.toString());
        boolean t2=this.orderService.undoGoods(orderId,id);
        log.info(String.valueOf(t2));
        if (t2){
            return ResultInfo.success(1);
        }else {
            return ResultInfo.error("失败了！！！！！！！！！！！！！");
        }
    }
    @PutMapping("/{itemId}/totalprice/{totalprice}")
    public void updateOrderStatus(@PathVariable Long itemId, @PathVariable BigDecimal totalprice) {

        orderService.updateItemPrice(itemId, totalprice);
    }

    @PostMapping("/testTotalPrice")
    public ResultInfo testTotalPrice(@RequestBody Map map) {
        Long orderId = Long.valueOf(String.valueOf(map.get("orderId")));
        Long goodsId = Long.valueOf(String.valueOf(map.get("goodsId")));
        BigDecimal r=this.orderService.getItemTotalPrice(orderId,goodsId);
//        OrderItemBo orderItemBo = new OrderItemBo(orderId,goodsId,goodsName,sellingPrice,goodsCount);
        return ResultInfo.success(r);
    }


    @PostMapping("/refuseRefund")
    public ResultInfo refuseRefund(@RequestBody Map map) {
        Long orderId=  Long.valueOf(String.valueOf(map.get("orderId")));
        log.info(orderId.toString());
        Long id=Long.valueOf(String.valueOf(map.get("id")));
        log.info(id.toString());
//        int quantity= (int) map.get("quantity");
//        log.info(String.valueOf(quantity));
        String rejectReason=String.valueOf(map.get("rejectionReason"));
        log.info("_____________________________________________________________________");
        boolean t2=this.orderService.refuseRefund(orderId,id,rejectReason);
        log.info(String.valueOf(t2));
        if (t2){
            return ResultInfo.success(1);
        }else {
            return ResultInfo.error("失败了！！！！！！！！！！！！！");
        }
    }

    @PostMapping("/acceptRefund")
    public ResultInfo acceptRefund(@RequestBody Map map) {
        Long orderId=  Long.valueOf(String.valueOf(map.get("orderId")));
        Long id=Long.valueOf(String.valueOf(map.get("id")));
        int quantity= (int) map.get("quantity");
        String businessAccount=String.valueOf(map.get("businessAccount"));
        log.info(String.valueOf(quantity));
        log.info("_____________________________________________________________________");
        boolean t1=this.goodsService.refundGoods(id,quantity);
        BigDecimal x =this.orderService.getItemTotalPrice(orderId,id);
        Double amount=Double.valueOf(x.toString());
        BusinessBo businessBo1 = businessService.getBusinessByAccount(businessAccount);
        FeeRate feeRate = feeRateService.getByGrade(businessBo1.getGrade());
        double rate=feeRate.getRate();
        double costFare=amount*1.00*rate;
        DecimalFormat df = new DecimalFormat("#.##");
        String formattedNumber = df.format(costFare);
        String formattedNumber2 = df.format(amount*1.00-costFare);
        costFare=Double.valueOf(formattedNumber);
        double valueToBusiness=Double.valueOf(formattedNumber2);
        boolean t2=this.businessService.refundMoney(amount,valueToBusiness,costFare,businessAccount);
        boolean t3=this.orderService.acceptRefund(orderId,id);
        //Todo 用户钱包+钱  添加记录
        //用户加钱
        Long userId=this.orderService.getUserIdByOrderId(orderId);
        this.userWalletService.rechargeUserWallet(userId,x);
        //添加记录
        PaymentRecordBo paymentRecordBo=new PaymentRecordBo(orderId,userId,x,"-1");
        this.paymentRecordService.createRefundRecord(paymentRecordBo);
        if (t1&&t2&&t3){
            return ResultInfo.success(1);
        }else {
            return ResultInfo.error("失败了！！！！！！！！！！！！！");
        }
    }

    @PostMapping("/getRefuseReason")
    public ResultInfo getRefuseReason(@RequestBody Map map) {
        Long orderId=  Long.valueOf(String.valueOf(map.get("orderId")));
        log.info(orderId.toString());
        Long id=Long.valueOf(String.valueOf(map.get("id")));
        log.info(id.toString());
        String s=this.orderService.getRefuseReason(orderId,id);
        return ResultInfo.success(s);
    }
}
