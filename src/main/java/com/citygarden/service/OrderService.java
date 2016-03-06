package com.citygarden.service;

import com.citygarden.domain.Order;
import com.citygarden.repository.OrderRepository;
import com.citygarden.service.util.PaymentUtil;
import com.citygarden.web.rest.dto.OrderDTO;
import com.citygarden.web.rest.dto.PayOrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016/3/4 0004.
 */
@Service
public class OrderService {

    private Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Inject
    private OrderRepository orderRepository;

    @Inject
    private Environment env;

    @Inject
    private HttpServletResponse resp;



    public Order save(OrderDTO order) {
        Order order_new = new Order();
        order_new.setTotalPrice(order.getTotalPrice());
        order_new.setDate(order.getDate());
        order_new.setDeliveryAddress(order.getDeliveryAddress());
        order_new.setOrderItemList(order.getOrderItemList());
        order_new.setOrderStatus(order.getOrderStatus());
        order_new.setDeliveryWay(order.getDeliveryWay());
        return  orderRepository.save(order_new);
    }

    public ModelAndView payment(PayOrderDTO payOrder) throws IOException {
		/*
		 * 1. 准备13个参数
		 */
        String p0_Cmd = "Buy";//业务类型，固定值Buy
        String p1_MerId = env.getProperty("payment.p1_MerId");//商号编码，在易宝的唯一标识
        String p2_Order = payOrder.getId();//订单编码
        String p3_Amt = "0.01";//支付金额
        String p4_Cur = "CNY";//交易币种，固定值CNY
        String p5_Pid = "";//商品名称
        String p6_Pcat = "";//商品种类
        String p7_Pdesc = "";//商品描述
        String p8_Url = env.getProperty("payment.p8_Url");;//在支付成功后，易宝会访问这个地址。
        String p9_SAF = "";//送货地址
        String pa_MP = "";//扩展信息
        String pd_FrpId = payOrder.getBankCode();//支付通道
        String pr_NeedResponse = "1";//应答机制，固定值1

		/*
		 * 2. 计算hmac
		 * 需要13个参数
		 * 需要keyValue
		 * 需要加密算法
		 */
        String keyValue = env.getProperty("payment.keyValue");
        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
            p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
            pd_FrpId, pr_NeedResponse, keyValue);

		/*
		 * 3. 重定向到易宝的支付网关
		 */
        StringBuilder sb = new StringBuilder("https://www.yeepay.com/app-merchant-proxy/node");
        sb.append("?").append("p0_Cmd=").append(p0_Cmd);
        sb.append("&").append("p1_MerId=").append(p1_MerId);
        sb.append("&").append("p2_Order=").append(p2_Order);
        sb.append("&").append("p3_Amt=").append(p3_Amt);
        sb.append("&").append("p4_Cur=").append(p4_Cur);
        sb.append("&").append("p5_Pid=").append(p5_Pid);
        sb.append("&").append("p6_Pcat=").append(p6_Pcat);
        sb.append("&").append("p7_Pdesc=").append(p7_Pdesc);
        sb.append("&").append("p8_Url=").append(p8_Url);
        sb.append("&").append("p9_SAF=").append(p9_SAF);
        sb.append("&").append("pa_MP=").append(pa_MP);
        sb.append("&").append("pd_FrpId=").append(pd_FrpId);
        sb.append("&").append("pr_NeedResponse=").append(pr_NeedResponse);
        sb.append("&").append("hmac=").append(hmac);

       // resp.sendRedirect(sb.toString());
        resp.reset();
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Headers ","origin, x-requested-with, x-http-method-override, content-type");
        resp.addHeader("Access-Control-Allow-Methods","PUT, GET, POST, DELETE, OPTIONS");
        System.out.println(resp);
        return new ModelAndView("redirect:" + sb.toString());
    }
}
