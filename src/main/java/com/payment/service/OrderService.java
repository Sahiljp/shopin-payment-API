package com.payment.service;

import com.payment.entity.CheckOutDto;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

import java.util.List;

public interface OrderService {

    Session checkoutList(List<CheckOutDto> checkOutDto, long userId) throws StripeException;
}
