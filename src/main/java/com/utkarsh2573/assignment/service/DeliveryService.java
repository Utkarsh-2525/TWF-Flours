package com.utkarsh2573.assignment.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeliveryService {

    // Which center stocks which product
    private final Map<String, List<String>> productCenterMap = Map.of(
            "A", List.of("C1"),
            "B", List.of("C2"),
            "C", List.of("C3"),
            "D", List.of("C1"),
            "E", List.of("C2"),
            "F", List.of("C3"),
            "G", List.of("C1"),
            "H", List.of("C2"),
            "I", List.of("C3")
    );

    // Distance from center to L1
    private final Map<String, Integer> centerToL1 = Map.of(
            "C1", 10,
            "C2", 20,
            "C3", 30
    );

    // Distance between centers
    private final Map<String, Map<String, Integer>> centerToCenter = Map.of(
            "C1", Map.of("C2", 15, "C3", 25),
            "C2", Map.of("C1", 15, "C3", 10),
            "C3", Map.of("C1", 25, "C2", 10)
    );

    public int calculateMinimumCost(Map<String, Integer> order) {
        Set<String> involvedCenters = new HashSet<>();
        for (String product : order.keySet()) {
            List<String> centers = productCenterMap.get(product);
            if (centers != null) {
                involvedCenters.addAll(centers);
            }
        }

        List<List<String>> centerPermutations = generatePermutations(new ArrayList<>(involvedCenters));
        int minCost = Integer.MAX_VALUE;

        // Try all possible starting centers
        for (String startCenter : centerToL1.keySet()) {
            for (List<String> route : centerPermutations) {
                int cost = 0;
                String current = startCenter;
                Map<String, Integer> remaining = new HashMap<>(order);
                Set<String> visited = new HashSet<>();

                for (String stop : route) {
                    if (visited.contains(stop)) continue;

                    Map<String, Integer> picked = getProductsFromCenter(stop, remaining);
                    if (picked.isEmpty()) continue;

                    if (!current.equals(stop)) {
                        cost += getDistance(current, stop);
                    }
                    current = stop;

                    // Deliver to L1
                    cost += getDistance(current, "L1");
                    current = "L1";

                    for (Map.Entry<String, Integer> entry : picked.entrySet()) {
                        String product = entry.getKey();
                        int pickedQty = entry.getValue();
                        int remainingQty = remaining.getOrDefault(product, 0);

                        if (pickedQty >= remainingQty) {
                            remaining.remove(product);
                        } else {
                            remaining.put(product, remainingQty - pickedQty);
                        }
                    }
                    visited.add(stop);
                }

                if (remaining.isEmpty()) {
                    minCost = Math.min(minCost, cost);
                }
            }
        }

        return minCost;
    }

    private Map<String, Integer> getProductsFromCenter(String center, Map<String, Integer> remainingOrder) {
        Map<String, Integer> picked = new HashMap<>();
        for (Map.Entry<String, Integer> entry : remainingOrder.entrySet()) {
            String product = entry.getKey();
            int quantity = entry.getValue();
            List<String> centers = productCenterMap.getOrDefault(product, List.of());
            if (centers.contains(center) && quantity > 0)
                picked.put(product, quantity);
        }
        return picked;
    }

    private int getDistance(String from, String to) {
        if (from.equals("L1")) return centerToL1.getOrDefault(to, 0);
        if (to.equals("L1")) return centerToL1.getOrDefault(from, 0);
        return centerToCenter.getOrDefault(from, Map.of()).getOrDefault(to, 0);
    }

    private List<List<String>> generatePermutations(List<String> centers) {
        List<List<String>> result = new ArrayList<>();
        permute(centers, 0, result);
        return result;
    }

    private void permute(List<String> arr, int start, List<List<String>> result) {
        if (start == arr.size()) {
            result.add(new ArrayList<>(arr));
            return;
        }
        for (int i = start; i < arr.size(); i++) {
            Collections.swap(arr, i, start);
            permute(arr, start + 1, result);
            Collections.swap(arr, i, start);
        }
    }
}
