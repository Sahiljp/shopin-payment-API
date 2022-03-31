package com.payment.serviceImpl;

import com.payment.entity.CheckOutDto;
import com.payment.entity.OrderEntity;
import com.payment.repository.OrderRepository;
import com.payment.service.OrderService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    public static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Value("${stripe.apikey}")
    String stripeKey;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Session checkoutList(List<CheckOutDto> checkOutDtoList, long userId) throws StripeException {
        Stripe.apiKey = stripeKey;

        List<SessionCreateParams.LineItem> sessionItemList = new ArrayList<>();
        for (CheckOutDto checkOutDto: checkOutDtoList) {
            sessionItemList.add(createSessionLineItem(checkOutDto));
        }
        //in stripe
        SessionCreateParams params= SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setCancelUrl("https://example.com/cancel")
                .setSuccessUrl("https://example.com/success")
                .addAllLineItem(sessionItemList)
                .build();
        Session session = Session.create(params);
        //save in database
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setSessionId(session.getId());
        orderEntity.setTotalprice(session.getAmountTotal()/100);
        orderEntity.setUserId(userId);
         orderRepository.save(orderEntity);
        return session;
    }

    private SessionCreateParams.LineItem createSessionLineItem(CheckOutDto checkOutDto) {
        return SessionCreateParams.LineItem.builder()
                .setPriceData(createPriceData(checkOutDto))
                .setQuantity((long) checkOutDto.getQuantity())
                .build();
    }

    private SessionCreateParams.LineItem.PriceData createPriceData(CheckOutDto checkOutDto) {
        return SessionCreateParams.LineItem.PriceData.builder()
                .setCurrency("INR")
                .setUnitAmount((long) (checkOutDto.getPrice()*100))
                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName(checkOutDto.getProductName()).build()).build();
    }
}
