package com.verygoodbank.tes.web.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class Product {
    @CsvBindByName(column = "product_id")
    String productId;
    @CsvBindByName(column = "product_name")
    String productName;
}