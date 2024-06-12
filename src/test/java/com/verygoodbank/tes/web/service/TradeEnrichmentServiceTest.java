package com.verygoodbank.tes.web.service;

import com.verygoodbank.tes.web.model.Trade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TradeEnrichmentServiceTest {

    @Mock
    private Map<String, String> productMap;

    @InjectMocks
    private TradeEnrichmentService tradeEnrichmentService;

    @Test
    public void testEnrichTradesWithValidData() {
        // Given
        String csvContent = "date,product_id,currency,price\n20230101,1,USD,100.0";
        MultipartFile file = new MockMultipartFile("file", "trade.csv", "text/csv", csvContent.getBytes(StandardCharsets.UTF_8));

        when(productMap.get("1")).thenReturn("Product Name");

        // When
        List<Trade> result = tradeEnrichmentService.enrichTrades(file);

        // Then
        assertEquals(1, result.size());
        assertEquals("Product Name", result.get(0).getProductName());
        verify(productMap, times(1)).get("1");
    }

    @Test
    public void testEnrichTradesWithInvalidDate() {
        // Given
        String csvContent = "date,product_id,currency,price\ninvalid_date,1,USD,100.0";
        MultipartFile file = new MockMultipartFile("file", "trade.csv", "text/csv", csvContent.getBytes(StandardCharsets.UTF_8));

        // When
        List<Trade> result = tradeEnrichmentService.enrichTrades(file);

        // Then
        assertTrue(result.isEmpty());
        verify(productMap, never()).get(anyString());
    }

    @Test
    public void testEnrichTradesWithMissingProductName() {
        // Given
        String csvContent = "date,product_id,currency,price\n20230101,2,USD,100.0";
        MultipartFile file = new MockMultipartFile("file", "trade.csv", "text/csv", csvContent.getBytes(StandardCharsets.UTF_8));

        when(productMap.get("2")).thenReturn(null);

        // When
        List<Trade> result = tradeEnrichmentService.enrichTrades(file);

        // Then
        assertEquals(1, result.size());
        assertEquals("Missing Product Name", result.get(0).getProductName());
        verify(productMap, times(1)).get("2");
    }
}