

package com.example.receiptprocessor.service;

import com.example.receiptprocessor.model.Receipt;
import com.example.receiptprocessor.model.Item;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class ReceiptService {

    private final Map<String, Integer> receiptPointsMap = new HashMap<>();

    public String processReceipt(Receipt receipt) {
        validateReceipt(receipt);
        String id = UUID.randomUUID().toString();
        int points = calculatePoints(receipt);
        receiptPointsMap.put(id, points);
        return id;
    }

    public Integer getPoints(String id) {
        return receiptPointsMap.getOrDefault(id, 0);
    }

    private void validateReceipt(Receipt receipt) {
        if (receipt == null || receipt.getItems() == null || receipt.getItems().isEmpty()) {
            throw new IllegalArgumentException("Receipt items cannot be null or empty");
        }

        if (receipt.getRetailer() == null || receipt.getRetailer().trim().isEmpty()) {
            throw new IllegalArgumentException("Retailer name cannot be null or empty");
        }

        if (receipt.getPurchaseDate() == null || receipt.getPurchaseDate().trim().isEmpty()) {
            throw new IllegalArgumentException("Purchase date cannot be null or empty");
        }

        if (receipt.getPurchaseTime() == null || receipt.getPurchaseTime().trim().isEmpty()) {
            throw new IllegalArgumentException("Purchase time cannot be null or empty");
        }

        if (!isValidDate(receipt.getPurchaseDate()) || !isValidTime(receipt.getPurchaseTime())) {
            throw new IllegalArgumentException("Invalid date or time format");
        }

        if (receipt.getTotal() <= 0) {
            throw new IllegalArgumentException("Total should be a positive number");
        }
    }

    public int calculatePoints(Receipt receipt) {
        int points = 0;
        points += calculateRetailerNamePoints(receipt.getRetailer());
        points += calculateRoundDollarPoints(receipt.getTotal());
        points += calculateMultipleOfQuarterPoints(receipt.getTotal());
        points += calculateItemPairPoints(receipt.getItems().size());
        points += calculateItemDescriptionPoints(receipt.getItems());
        points += calculateTotalGreaterThan10Points(receipt.getTotal());
        points += calculateOddDayPoints(receipt.getPurchaseDate());
        points += calculatePurchaseTimePoints(receipt.getPurchaseTime());
        return points;
    }

    private int calculateRetailerNamePoints(String retailer) {
        return retailer.replaceAll("[^a-zA-Z0-9]", "").length();
    }

    private int calculateRoundDollarPoints(double total) {
        return total % 1 == 0 ? 50 : 0;
    }

    private int calculateMultipleOfQuarterPoints(double total) {
        return total % 0.25 == 0 ? 25 : 0;
    }

    private int calculateItemPairPoints(int itemCount) {
        return (itemCount / 2) * 5;
    }

    private int calculateItemDescriptionPoints(Iterable<Item> items) {
        int points = 0;
        for (Item item : items) {
            String description = item.getShortDescription().trim();
            if (description.length() % 3 == 0) {
                try {
                    double price = Double.parseDouble(item.getPrice());
                    if (price < 0) {
                        throw new IllegalArgumentException("Item price cannot be negative");
                    }
                    points += Math.ceil(price * 0.2);
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Invalid price format for item: " + item.getShortDescription());
                }
            }
        }
        return points;
    }

    private int calculateTotalGreaterThan10Points(double total) {
        return total > 10.00 ? 5 : 0;
    }

    private int calculateOddDayPoints(String purchaseDate) {
        int day = Integer.parseInt(purchaseDate.split("-")[2]);
        return day % 2 != 0 ? 6 : 0;
    }

    private int calculatePurchaseTimePoints(String purchaseTime) {
        String[] timeParts = purchaseTime.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        return (hour == 14 || (hour == 15 && minute == 0)) ? 10 : 0;
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private boolean isValidTime(String time) {
        try {
            LocalTime.parse(time);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
