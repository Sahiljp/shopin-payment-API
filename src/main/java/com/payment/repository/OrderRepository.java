package com.payment.repository;

import com.payment.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<OrderEntity,Long> {

    @Query("SELECT o From OrderEntity o Where o.paymentId = :paymentId")
    OrderEntity getDataByPaymentId(String paymentId);
}
