package com.prasad.ecommerence.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.prasad.ecommerence.exception.OrderException;
import com.prasad.ecommerence.model.Order;
import com.prasad.ecommerence.repository.OrderRepository;
import com.prasad.ecommerence.responce.PaymentLinkResponse;
import com.prasad.ecommerence.service.OrderService;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;



// while import value form the property file use constructor not using allargsconstructor
@RestController
@RequestMapping("api/payments")
@Component
public class PaymentController {

    @Value("${razorpay.api.key}")
    private String apiKey;

    @Value("${razorpay.api.secret}")
    private String apiSecret;

    private  OrderService orderService;
    private  OrderRepository orderRepository;
    

    public PaymentController(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }


    @PostMapping("{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPaymentLink(@PathVariable("orderId") Long orderId,@RequestHeader("Authorization") String jwt) throws RazorpayException, OrderException {
        Order order = orderService.findOrderById(orderId);
        RazorpayClient razorPay = new RazorpayClient("111", "111");
        try {
            JSONObject paymentLinkRequest = new JSONObject();
            paymentLinkRequest.put("amount", order.getTotalPrice());
            paymentLinkRequest.put("currency", "INR");

            JSONObject customer = new JSONObject();
            customer.put("name", order.getUser().getFirstName());
            customer.put("email", order.getUser().getEmail());
            
            paymentLinkRequest.put("customer", customer);

            JSONObject notify = new JSONObject();
            notify.put("sms", true);
            notify.put("email", true);

            paymentLinkRequest.put("notify", notify);

            paymentLinkRequest.put("callback_url", "required page link here ----");
            paymentLinkRequest.put("callback_method", "get");

            PaymentLink payment = razorPay.paymentLink.create(paymentLinkRequest);
            String paymentLinkId = payment.get("id");
            String paymentLinkUrl  = payment.get("short_url");

            PaymentLinkResponse res = new PaymentLinkResponse();
            res.setPayment_link_id(paymentLinkId);
            res.setPayment_link_url(paymentLinkUrl);
            return new ResponseEntity<>(res,HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RazorpayException(e.getMessage());
        }
    }
    

    public ResponseEntity<String> redirect (@RequestParam(name = "payment_id") String paymentId,
    @RequestParam("order_id") Long orderId  ) throws OrderException, RazorpayException{
        Order order = orderService.findOrderById(orderId);
        RazorpayClient razorPay = new RazorpayClient("111", "111");
        try {
            Payment payment = razorPay.payments.fetch(paymentId);
            if(payment.get("status").equals("captured")){
                order.getPaymentDetails().setPaymentId(paymentId);
                order.getPaymentDetails().setStatus("COMPLETED");
                order.setOrderStatus("PLACED");
                orderRepository.save(order);
            }
            return new ResponseEntity<>("Your order get placed",HttpStatus.ACCEPTED);
        } catch (Exception e) {
            throw new RazorpayException(e.getMessage());
        }
     }
    
}

