package com.verygoodbank.tes.web.util;

import com.opencsv.CSVWriter;
import com.verygoodbank.tes.web.model.Trade;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class CSVHelper {
    public static String TYPE = "text/csv";

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static ByteArrayInputStream tradesToCSV(List<Trade> trades) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8));
             CSVWriter csvWriter = new CSVWriter(writer)) {

            // Write header
            csvWriter.writeNext(new String[]{"date", "product_name", "currency", "price"}, false);

            for (Trade trade : trades) {
                csvWriter.writeNext(new String[]{
                        trade.getDate(),
                        trade.getProductName(),
                        trade.getCurrency(),
                        trade.getPrice()
                }, false);
            }

            writer.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to convert trades to CSV: " + e.getMessage());
        }
    }
}
