package com.example.webstore.requests;

import lombok.Data;

/**
 * Класс сущности для создания заказа. Хранит id товара и его количество в заказе
 */
@Data
public class GoodQuantity {
    private int goodId;
    private int quantity;
    
    public GoodQuantity(int goodId, int quantity) {
        this.goodId = goodId;
        this.quantity = quantity;
    }

    public GoodQuantity() {

    }
    
}
