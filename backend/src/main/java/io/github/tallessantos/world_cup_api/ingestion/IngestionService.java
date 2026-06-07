package io.github.tallessantos.world_cup_api.ingestion;

import io.github.tallessantos.world_cup_api.core.service.CsvImportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class IngestionService implements CommandLineRunner {

    @Autowired
    private CsvImportService csvImportService;

    @Override
    public void run(String... args) throws Exception {

        try {

            csvImportService.dataEntryUpToThe2014WorldCup();
            csvImportService.dataEntryAfterToThe2014WorldCup();
            log.info("Ingestion successfully ended");

        } catch (Exception e) {

            log.error("Error in ingestion data: {}", e.getMessage());
        }
    }
}
