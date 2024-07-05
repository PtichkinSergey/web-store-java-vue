package com.example.webstore.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс сущности для создания заказа. Хранит id товара и его количество в заказе
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoodQuantity {
    private int goodId;
    private int goodQuantity;
}
