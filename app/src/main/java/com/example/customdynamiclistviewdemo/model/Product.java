package com.example.customdynamiclistviewdemo.model;

/**
 * Created by Promlert on 2017-01-21.
 */

public class Product {

    public final String productName;
    public final int stockAvailable;
    public int quantity = 0;
    public int palletNumber = 0;

    public Product(String productName, int stockAvailable) {
        this.productName = productName;
        this.stockAvailable = stockAvailable;
    }
}
