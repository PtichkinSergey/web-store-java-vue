package com.example.webstore.web;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GoodQuantity {
    private int goodId;
    private int goodQuantity;
}
