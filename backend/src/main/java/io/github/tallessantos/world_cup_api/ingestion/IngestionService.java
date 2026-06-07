package io.github.tallessantos.world_cup_api.ingestion;

import io.github.tallessantos.world_cup_api.core.domain.WorldCupEntity;
import io.github.tallessantos.world_cup_api.core.service.CsvImportService;
import io.github.tallessantos.world_cup_api.infra.repository.WorldCupRepository;
import io.github.tallessantos.world_cup_api.infra.csv.CsvDataSource;
import io.github.tallessantos.world_cup_api.infra.csv.PlayerCsvRow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class IngestionService implements CommandLineRunner {

    @Value("${app.data.world-cups-after-2014-path}")
    private String worldCupsAfter2014;

    @Value("${app.data.world-cups-matches-2018-path}")
    private String worldCupMatches2018;

    @Value("${app.data.world-cups-matches-2022-path}")
    private String worldCupMatches2022;

    @Value("${app.data.world-cups-players-2018-path}")
    private String worldCupPlayers2018;

    @Value("${app.data.world-cups-players-2022-path}")
    private String worldCupPlayers2022;

    @Autowired
    private WorldCupRepository worldCupRepository;

    @Autowired
    private CsvImportService csvImportService;

    @Autowired
    private CsvDataSource csvDataSource;

    @Override
    public void run(String... args) throws Exception {

        try {

            dataEntryUpToThe2014WorldCup();
            dataEntryAfterToThe2014WorldCup();
            log.info("Ingestion successfully ended");

        } catch (Exception e) {

            log.error("Error in ingestion data: {}", e.getMessage());
        }
    }

    public void dataEntryUpToThe2014WorldCup() {

        if (!csvImportService.isWorldCupTableEmpty()) {
            log.warn("Already has data of world cups in database");
            return;
        }

        csvImportService.worldCupDataIngestion(csvDataSource.loadWorldCups());
        csvImportService.worldCupMatchesDataIngestion(csvDataSource.loadMatches());
        csvImportService.worldCupCountriesDataIngestion(csvDataSource.loadMatches());
        csvImportService.worldCupAppearenceDataIngestion(csvDataSource.loadPlayers());
        log.info("Ingestion data ended");
    }

    public void dataEntryAfterToThe2014WorldCup() {

        Optional<WorldCupEntity> worldCup2018 = worldCupRepository.findByReference("world-cup-2018");
        if (worldCup2018.isPresent()) {
            log.info("Already has 2018 world cup data in database");
            return;
        }

        csvImportService.worldCupDataIngestion(csvDataSource.loadWorldCups(Path.of(worldCupsAfter2014)));
        csvImportService.worldCupMatchesDataIngestion(csvDataSource.loadMatches(Path.of(worldCupMatches2018)));
        csvImportService.worldCupMatchesDataIngestion(csvDataSource.loadMatches(Path.of(worldCupMatches2022)));
        List<PlayerCsvRow> players2018 = csvDataSource.loadPlayers(Path.of(worldCupPlayers2018));
        if (!players2018.isEmpty()) {
            csvImportService.worldCupAppearenceDataIngestion(players2018);
        } else {
            log.warn("Csv file don't have data");
        }

        List<PlayerCsvRow> players2022 = csvDataSource.loadPlayers(Path.of(worldCupPlayers2022));
        if (!players2022.isEmpty()) {
            csvImportService.worldCupAppearenceDataIngestion(players2022);
        } else {
            log.warn("Csv file don't have data");
        }
        log.info("Ingestion data ended");
    }
}
