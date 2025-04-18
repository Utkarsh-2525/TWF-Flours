package com.utkarsh2573.assignment.model;

import java.util.Map;

public class ProductRequest {
    private Map<String, Integer> products;

    public Map<String, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<String, Integer> products) {
        this.products = products;
    }
}
