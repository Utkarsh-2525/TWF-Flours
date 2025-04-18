package com.utkarsh2573.assignment.controller;

import com.utkarsh2573.assignment.model.OrderRequest;
import com.utkarsh2573.assignment.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping("/calculate")
    public int calculateDeliveryCost(@RequestBody OrderRequest request) {
        return deliveryService.calculateMinimumCost(request.getProducts());
    }
}
