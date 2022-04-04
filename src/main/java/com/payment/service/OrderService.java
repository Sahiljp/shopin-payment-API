package com.payment.service;

import com.payment.entity.CheckOutDto;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import java.util.List;
import java.util.Map;

public interface OrderService {

    Session checkoutList(List<CheckOutDto> checkOutDto, long userId) throws StripeException;

    public void updateQtyAndCartData(List<CheckOutDto> checkOutDto, String sessionId) throws StripeException;
}
