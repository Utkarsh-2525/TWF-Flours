package com.utkarsh2573.assignment.controller;

import com.utkarsh2573.assignment.service.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/calculate")
public class DeliveryController {

    @PostMapping
    public ResponseEntity<?> calculateMinCost(
            @RequestBody Map<String, Integer> order // ← bind flat JSON directly
    ) {
        if (order == null || order.isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body("Invalid input. Make sure all values are integers.");
        }

        Integer cost = DeliveryService.calculateMinCost(order);
        if (cost == null) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Cannot fulfill order");
        }
        return ResponseEntity.ok(cost);
    }
}
