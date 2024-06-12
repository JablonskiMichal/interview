package com.verygoodbank.tes.web.config;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;
import com.verygoodbank.tes.web.model.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class ProductListConfig {
    private static final String PRODUCT_CSV = "product.csv";

    @Bean
    public Map<String, String> getProductMap() throws IOException {
        try (CSVReader reader = new CSVReader(new InputStreamReader(new ClassPathResource(PRODUCT_CSV).getInputStream(), StandardCharsets.UTF_8))) {
            List<Product> products = new CsvToBeanBuilder<Product>(reader)
                    .withType(Product.class)
                    .build()
                    .parse();
            return products.stream().collect(Collectors.toMap(Product::getProductId, Product::getProductName));
        }
    }
}
