package com.verygoodbank.tes.web.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;

@Data
public class Trade {
    @CsvBindByName(column = "date")
    private String date;
    @CsvBindByName(column = "product_id")
    private String productId;
    private String productName;
    @CsvBindByName(column = "currency")
    private String currency;
    @CsvBindByName(column = "price")
    private String price;
}
