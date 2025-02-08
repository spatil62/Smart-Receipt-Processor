

package com.example.receiptprocessor.model;

import lombok.Data;
import java.util.List;

@Data
public class Receipt {
    private String retailer;
    private double total;
    private List<Item> items;
    private String purchaseDate;
    private String purchaseTime;
}
