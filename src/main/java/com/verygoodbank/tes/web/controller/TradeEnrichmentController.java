package com.verygoodbank.tes.web.controller;


import com.verygoodbank.tes.web.model.Trade;
import com.verygoodbank.tes.web.service.TradeEnrichmentService;
import com.verygoodbank.tes.web.util.CSVHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
public class TradeEnrichmentController {

    private final TradeEnrichmentService enrichmentService;

    @PostMapping(value = "/enrich", produces = "text/csv")
    public ResponseEntity<?> enrich(@RequestParam("file") MultipartFile file) {
        String resultName = "enriched.csv";
        List<Trade> trades = enrichmentService.enrichTrades(file);
        ByteArrayInputStream byteArrayInputStream = CSVHelper.tradesToCSV(trades);
        InputStreamResource resultFile = new InputStreamResource(byteArrayInputStream);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + resultName)
                .contentType(MediaType.parseMediaType("application/csv"))
                .body(resultFile);

    }

}


