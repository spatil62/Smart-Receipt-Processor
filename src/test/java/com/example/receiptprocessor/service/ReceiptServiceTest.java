package com.example.receiptprocessor.service;

import com.example.receiptprocessor.model.Item;
import com.example.receiptprocessor.model.Receipt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ReceiptServiceTest {

    @InjectMocks
    private ReceiptService receiptService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessReceipt_ValidReceipt_ReturnsReceiptId() {
        Receipt receipt = createValidReceipt();
        String receiptId = receiptService.processReceipt(receipt);
        assertNotNull(receiptId);
    }

    @Test
    void testProcessReceipt_InvalidReceipt_ThrowsException() {
        Receipt receipt = new Receipt(); // Missing required fields
        Exception exception = assertThrows(IllegalArgumentException.class, () -> receiptService.processReceipt(receipt));
        assertEquals("Receipt items cannot be null or empty", exception.getMessage());
    }

    @Test
    void testCalculatePoints_ValidReceipt_ReturnsExpectedPoints() {
        Receipt receipt = createValidReceipt();
        int points = receiptService.calculatePoints(receipt);
        assertTrue(points > 0);
    }

    @Test
    void testGetPoints_ValidReceiptId_ReturnsPoints() {
        Receipt receipt = createValidReceipt();
        String receiptId = receiptService.processReceipt(receipt);
        int points = receiptService.getPoints(receiptId);
        assertTrue(points > 0);
    }

    @Test
    void testGetPoints_InvalidReceiptId_ReturnsZero() {
        int points = receiptService.getPoints("invalid-id");
        assertEquals(0, points);
    }

    private Receipt createValidReceipt() {
        Receipt receipt = new Receipt();
        receipt.setRetailer("Retail Store");
        receipt.setTotal(25.50);
        receipt.setPurchaseDate("2024-02-07");
        receipt.setPurchaseTime("14:30");

        List<Item> items = new ArrayList<>();
        Item item1 = new Item();
        item1.setShortDescription("Item One");
        item1.setPrice("10.00");
        items.add(item1);

        receipt.setItems(items);
        return receipt;
    }
}
