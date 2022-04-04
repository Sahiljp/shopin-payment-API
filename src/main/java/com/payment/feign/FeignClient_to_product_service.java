package com.payment.feign;

import com.payment.entity.CheckOutDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "product-service", url = "localhost:9001")
public interface FeignClient_to_product_service {


    @PutMapping("/product/updateQtyAndCartData")
    public void updateQtyAndCartData(@RequestBody List<CheckOutDto> checkOutDto);

}
