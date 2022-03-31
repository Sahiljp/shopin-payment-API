package com.payment.controller;

import com.payment.entity.CheckOutDto;
import com.payment.entity.StripeResponse;
import com.payment.service.OrderService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/checkoutList")
    public ResponseEntity<StripeResponse> checkoutList(@RequestBody List<CheckOutDto> checkOutDto, @RequestParam long userId) throws StripeException{
        Session session = orderService.checkoutList(checkOutDto,userId);
        StripeResponse stripeResponse = new StripeResponse(session.getUrl(),session.getId());

        return new ResponseEntity<>(stripeResponse , HttpStatus.OK);
    }

}
