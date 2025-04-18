package com.utkarsh2573.assignment.service;

import com.utkarsh2573.assignment.util.WarehouseData;

import java.util.*;

public class DeliveryService {

    public static Integer calculateMinCost(Map<String, Integer> orderInput) {
        int minCost = Integer.MAX_VALUE;

        for (String start : List.of("C1", "C2", "C3")) {
            Integer cost = findBestCost(start, orderInput);
            if (cost != null) {
                minCost = Math.min(minCost, cost);
            }
        }
        return (minCost == Integer.MAX_VALUE) ? null : minCost;
    }

    private static Integer findBestCost(String start, Map<String, Integer> originalOrder) {
        List<String> warehouses = List.of("C1", "C2", "C3");
        List<List<String>> permutations = generatePermutations(warehouses);

        int minCost = Integer.MAX_VALUE;

        for (List<String> sequence : permutations) {
            if (!sequence.get(0).equals(start)) continue;

            Map<String, Integer> order = new HashMap<>(originalOrder);
            Map<String, Map<String, Integer>> inventory = cloneInventory();

            int cost = 0;
            String current = sequence.get(0);
            Map<String, Integer> carriedItems = new HashMap<>();

            for (String stop : sequence) {
                Map<String, Integer> picked = pickFromWarehouse(order, inventory.get(stop));
                if (picked.isEmpty()) continue;

                if (!current.equals(stop)) {
                    cost += travelCost(current, stop, carriedItems);
                }

                for (var entry : picked.entrySet()) {
                    carriedItems.merge(entry.getKey(), entry.getValue(), Integer::sum);
                }

                cost += travelCost(stop, "L1", carriedItems);
                carriedItems.clear();
                current = "L1";
            }

            if (order.values().stream().allMatch(q -> q == 0)) {
                minCost = Math.min(minCost, cost);
            }
        }
        return (minCost == Integer.MAX_VALUE) ? null : minCost;
    }

    private static Map<String, Integer> pickFromWarehouse(Map<String, Integer> order, Map<String, Integer> stock) {
        Map<String, Integer> picked = new HashMap<>();
        for (String item : order.keySet()) {
            int needed = order.get(item);
            int available = stock.getOrDefault(item, 0);
            int take = Math.min(needed, available);
            if (take > 0) {
                picked.put(item, take);
                order.put(item, needed - take);
                stock.put(item, available - take);
            }
        }
        return picked;
    }

    private static int travelCost(String from, String to, Map<String, Integer> items) {
        int distance = WarehouseData.distances.get(from).get(to);
        double weight = items.values().stream().mapToInt(i -> i).sum() * WarehouseData.PRODUCT_WEIGHT;
        return (int) (distance * weight);
    }

    private static Map<String, Map<String, Integer>> cloneInventory() {
        Map<String, Map<String, Integer>> clone = new HashMap<>();
        WarehouseData.inventory.forEach((k, v) -> clone.put(k, new HashMap<>(v)));
        return clone;
    }

    private static List<List<String>> generatePermutations(List<String> elements) {
        List<List<String>> results = new ArrayList<>();
        permute(elements, 0, results);
        return results;
    }

    private static void permute(List<String> arr, int index, List<List<String>> result) {
        if (index == arr.size() - 1) {
            result.add(new ArrayList<>(arr));  // Make a copy of the list
            return;
        }
        for (int i = index; i < arr.size(); i++) {
            // Convert the immutable list to a mutable one before swapping
            List<String> mutableArr = new ArrayList<>(arr);
            Collections.swap(mutableArr, i, index);  // Swap on the mutable list
            permute(mutableArr, index + 1, result);  // Recursively permute
        }
    }
}
