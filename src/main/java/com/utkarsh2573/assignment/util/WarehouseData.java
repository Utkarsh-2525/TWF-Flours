package com.utkarsh2573.assignment.util;

import java.util.HashMap;
import java.util.Map;

public class WarehouseData {
    public static final double PRODUCT_WEIGHT = 0.5;

    public static final Map<String, Map<String, Integer>> distances = Map.of(
            "C1", Map.of("C2", 10, "C3", 20, "L1", 30),
            "C2", Map.of("C1", 10, "C3", 15, "L1", 25),
            "C3", Map.of("C1", 20, "C2", 15, "L1", 35),
            "L1", Map.of("C1", 30, "C2", 25, "C3", 35)
    );

    public static final Map<String,Map<String,Integer>> inventory = Map.of(
            "C1", Map.of("A",1000,"B",1000,"C",1000,"D",1000,"E",1000),
            "C2", Map.of("F",1000,"G",1000,"H",1000),
            "C3", Map.of("I",1000)
    );
}
