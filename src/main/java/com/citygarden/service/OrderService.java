package com.citygarden.service;

import com.citygarden.config.SecurityConfiguration;
import com.citygarden.domain.Order;
import com.citygarden.repository.OrderRepository;
import com.citygarden.security.SecurityUtils;
import com.citygarden.service.util.PaymentUtil;
import com.citygarden.web.rest.dto.OrderDTO;
import com.citygarden.web.rest.dto.PayOrderDTO;
import com.citygarden.web.rest.util.CloudxEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
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
        String username = SecurityUtils.getCurrentUserLogin();
        order_new.setTotalPrice(order.getTotalPrice());
        order_new.setDate(order.getDate());
        order_new.setDeliveryAddress(order.getDeliveryAddress());
        order_new.setOrderItemList(order.getOrderItemList());
        order_new.setOrderStatus(order.getOrderStatus());
        order_new.setDeliveryWay(order.getDeliveryWay());
        order_new.setUsername(username);
        return  orderRepository.save(order_new);
    }

    public String payment(PayOrderDTO payOrder) throws IOException {
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

        return sb.toString();
    }

    public String backPay() throws Exception {
		/*
		 * 1. 获取12个参数
		 */
        HttpServletRequest req = null;
		String p1_MerId = req.getParameter("p1_MerId");
		String r0_Cmd = req.getParameter("r0_Cmd");
		String r1_Code = req.getParameter("r1_Code");
		String r2_TrxId = req.getParameter("r2_TrxId");
		String r3_Amt = req.getParameter("r3_Amt");
		String r4_Cur = req.getParameter("r4_Cur");
		String r5_Pid = req.getParameter("r5_Pid");
		String r6_Order = req.getParameter("r6_Order");
		String r7_Uid = req.getParameter("r7_Uid");
		String r8_MP = req.getParameter("r8_MP");
		String r9_BType = req.getParameter("r9_BType");
		String hmac = req.getParameter("hmac");
		/*
		 * 2. 获取keyValue
		 */
        String keyValue = env.getProperty("payment.keyValue");
		/*
		 * 3. 调用PaymentUtil的校验方法来校验调用者的身份
		 *   >如果校验失败：保存错误信息，转发到msg.jsp
		 *   >如果校验通过：
		 *     * 判断访问的方法是重定向还是点对点，如果要是重定向
		 *     修改订单状态，保存成功信息，转发到msg.jsp
		 *     * 如果是点对点：修改订单状态，返回success
		 */
		boolean bool = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId,
				r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType,
				keyValue);
		if(!bool) {
			//req.setAttribute("code", "error");
			//req.setAttribute("msg", "无效的签名，支付失败！（你不是好人）");
			return "error";
		}
		if(r1_Code.equals("1")) {
			updateOrderStatus(r6_Order, CloudxEnums.OrderStatusEnum.PAYANDUNDELIVE);
			if(r9_BType.equals("1")) {
//				req.setAttribute("code", "success");
//				req.setAttribute("msg", "恭喜，支付成功！");
	         	return "success";
			} else if(r9_BType.equals("2")) {
				resp.getWriter().print("success");
			}
		}
        return null;
    }

    private void updateOrderStatus(String orderId, String i) {
        Order order = orderRepository.findOne(orderId);
        order.setOrderStatus(i);
        orderRepository.save(order);
    }
}
