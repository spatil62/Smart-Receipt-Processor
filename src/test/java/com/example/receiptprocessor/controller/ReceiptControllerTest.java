package com.example.receiptprocessor.controller;

import com.example.receiptprocessor.model.Receipt;
import com.example.receiptprocessor.service.ReceiptService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReceiptControllerTest {

    @InjectMocks
    private ReceiptController receiptController;

    @Mock
    private ReceiptService receiptService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testProcessReceipt_ValidReceipt_ReturnsCreatedStatus() {
        Receipt receipt = new Receipt();
        when(receiptService.processReceipt(receipt)).thenReturn("12345");

        ResponseEntity<String> response = receiptController.processReceipt(receipt);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("12345", response.getBody());
    }

    @Test
    void testProcessReceipt_InvalidReceipt_ReturnsBadRequest() {
        doThrow(new IllegalArgumentException("Invalid receipt")).when(receiptService).processReceipt(any(Receipt.class));

        ResponseEntity<String> response = receiptController.processReceipt(new Receipt());

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid receipt", response.getBody());
    }

    @Test
    void testGetPoints_ValidId_ReturnsPoints() {
        when(receiptService.getPoints("12345")).thenReturn(100);

        ResponseEntity<?> response = receiptController.getPoints("12345");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(100, response.getBody());
    }

    @Test
    void testGetPoints_InvalidId_ReturnsNotFound() {
        when(receiptService.getPoints("invalid-id")).thenReturn(0);

        ResponseEntity<?> response = receiptController.getPoints("invalid-id");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Receipt ID not found: invalid-id", response.getBody());
    }
}
