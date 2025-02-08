

package com.example.receiptprocessor.controller;

import com.example.receiptprocessor.model.Receipt;
import com.example.receiptprocessor.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/receipt")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @PostMapping("/process")
    public ResponseEntity<String> processReceipt(@RequestBody Receipt receipt) {
        try {
            String receiptId = receiptService.processReceipt(receipt);
            return new ResponseEntity<>(receiptId, HttpStatus.CREATED); // Return ID with 201 status
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST); // Handle invalid inputs
        }
    }

    @GetMapping("/points/{id}")
    public ResponseEntity<?> getPoints(@PathVariable String id) {
        Integer points = receiptService.getPoints(id);
        if (points == 0) {
            return new ResponseEntity<>("Receipt ID not found: " + id, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(points, HttpStatus.OK);
    }
}
