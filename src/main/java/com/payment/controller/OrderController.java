package com.payment.controller;

import com.payment.entity.CheckOutDto;
import com.payment.entity.StripeResponse;
import com.payment.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {

    public static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderService orderService;

    @PostMapping("/checkoutList")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckOutDto> checkOutDto, @RequestParam long userId) throws StripeException{
        Session session = orderService.checkoutList(checkOutDto,userId);
        StripeResponse stripeResponse = new StripeResponse(session.getUrl(),session.getId(),session.getPaymentIntent());

        return new ResponseEntity<>(stripeResponse , HttpStatus.OK);
    }

    @PutMapping("/updateQtyAndCartData")
    public void updateQtyAndCartData(@RequestBody List<CheckOutDto> checkOutDto, @RequestParam("sessionId") String sessionId) throws StripeException{
        try {
            logger.info("Inside updateQtyAndCartData() : " + sessionId);
            orderService.updateQtyAndCartData(checkOutDto,sessionId);
        } catch (Exception e) {
            logger.error("Error occured while registering user {} :Reason :{}", sessionId, e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
    @GetMapping("/refundPayments")
    public ResponseEntity<Map<String, String>> refundPayments(@RequestParam("paymentId") String paymentId) throws StripeException {
        try {
            logger.info("Inside updateQtyAndCartData() : " + paymentId);
            return  new ResponseEntity<>(orderService.refundPayments(paymentId),HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occured while registering user {} :Reason :{}", paymentId, e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}
