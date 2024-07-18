package com.example.webstore.web;

import lombok.Data;

/**
 * Класс сущности для создания заказа. Хранит id товара и его количество в заказе
 */
@Data
public class GoodQuantity {
    private int goodId;
    private int goodQuantity;
    
    public GoodQuantity(int goodId, int goodQuantity) {
        this.goodId = goodId;
        this.goodQuantity = goodQuantity;
    }

    public GoodQuantity() {

    }
    
}
