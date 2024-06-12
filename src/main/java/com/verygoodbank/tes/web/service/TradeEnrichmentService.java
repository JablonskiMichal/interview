package com.verygoodbank.tes.web.service;


import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.verygoodbank.tes.web.exception.CsvParseException;
import com.verygoodbank.tes.web.model.Trade;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
@Slf4j
public class TradeEnrichmentService {
    private final Map<String, String> productMap;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    public List<Trade> enrichTrades(MultipartFile file) {
        List<Trade> trades = parseCsvFile(file);

        return filterTrades(trades);
    }

    private List<Trade> filterTrades(List<Trade> trades) {
        return trades.stream().filter(trade -> {
            if (!isValidDate(trade.getDate())) {
                log.info("Invalid date format: {}", trade.getDate());
                return false;
            }
            String productName = productMap.get(trade.getProductId());
            if (productName == null) {
                log.info("Missing product name for product ID: {}", trade.getProductId());
                productName = "Missing Product Name";
            }
            trade.setProductName(productName);
            return true;
        }).toList();
    }

    private List<Trade> parseCsvFile(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<Trade> csvToBean = new CsvToBeanBuilder<Trade>(reader)
                    .withType(Trade.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        } catch (IOException exception) {
            log.error("Failed to parse CSV file: {}", exception.getMessage());
            throw new CsvParseException("Failed to parse CSV file");
        }
    }

    private boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr, DATE_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
