package io.github.tallessantos.world_cup_api.infra.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class CsvDataSource {

    private final Path worldCupsCsvPath;
    private final Path matchesCsvPath;
    private final Path playersCsvPath;

    public CsvDataSource(
            @Value("${app.data.world-cups-path}") String worldCupsCsvPath,
            @Value("${app.data.world-cups-matches-path}") String matchesCsvPath,
            @Value("${app.data.world-cups-players-path}") String playersCsvPath
    ) {
        this.worldCupsCsvPath = Path.of(worldCupsCsvPath);
        this.matchesCsvPath = Path.of(matchesCsvPath);
        this.playersCsvPath = Path.of(playersCsvPath);
    }

    public List<WorldCupCsvRow> loadWorldCups() {
        return read(worldCupsCsvPath).stream()
                .map(this::toWorldCupRow)
                .toList();
    }

    public List<WorldCupCsvRow> loadWorldCups(Path csvMatchesPath) {
        return read(csvMatchesPath).stream()
                .map(this::toWorldCupRow)
                .toList();
    }

    public List<MatchCsvRow> loadMatches() {
        return read(matchesCsvPath).stream()
                .filter(this::isValidMatchRecord)
                .map(this::toMatchRow)
                .toList();
    }

    public List<MatchCsvRow> loadMatches(Path matchesCsvPath) {
        return read(matchesCsvPath).stream()
                .filter(this::isValidMatchRecord)
                .map(this::toMatchRow)
                .toList();
    }

    public List<PlayerCsvRow> loadPlayers() {
        return read(playersCsvPath).stream()
                .map(this::toPlayerRow)
                .toList();
    }

    public List<PlayerCsvRow> loadPlayers(Path csvPlayersPath) {
        return read(csvPlayersPath).stream()
                .map(this::toPlayerRow)
                .toList();
    }

    private List<CSVRecord> read(Path path) {
        try (Reader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8);
             CSVParser parser = CSVFormat.DEFAULT.builder()
                     .setHeader()
                     .setSkipHeaderRecord(true)
                     .setIgnoreEmptyLines(true)
                     .get()
                     .parse(reader)) {
            return parser.stream()
                    .filter(csvRecord -> csvRecord.size() > 0)
                    .filter(csvRecord -> !CsvSupport.cleanValue(csvRecord.get(0)).isBlank())
                    .toList();
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to read CSV at " + path, exception);
        }
    }

    private WorldCupCsvRow toWorldCupRow(CSVRecord csvRecord) {
        return new WorldCupCsvRow(
                Integer.parseInt(CsvSupport.cleanValue(csvRecord.get(0))),
                CsvSupport.cleanValue(csvRecord.get(1)),
                CsvSupport.cleanValue(csvRecord.get(2)),
                CsvSupport.cleanValue(csvRecord.get(3)),
                CsvSupport.cleanValue(csvRecord.get(4)),
                CsvSupport.cleanValue(csvRecord.get(5)),
                Integer.parseInt(CsvSupport.cleanValue(csvRecord.get(6))),
                Integer.parseInt(CsvSupport.cleanValue(csvRecord.get(7))),
                Integer.parseInt(CsvSupport.cleanValue(csvRecord.get(8))),
                CsvSupport.cleanValue(csvRecord.get(9))
        );
    }

    private MatchCsvRow toMatchRow(CSVRecord csvRecord) {
        return new MatchCsvRow(
                Integer.parseInt(CsvSupport.cleanValue(csvRecord.get(0))),
                CsvSupport.cleanValue(csvRecord.get(1)),
                CsvSupport.cleanValue(csvRecord.get(2)),
                CsvSupport.cleanValue(csvRecord.get(3)),
                CsvSupport.cleanValue(csvRecord.get(4)),
                CsvSupport.cleanValue(csvRecord.get(5)),
                Integer.parseInt(CsvSupport.cleanValue(csvRecord.get(6))),
                Integer.parseInt(CsvSupport.cleanValue(csvRecord.get(7))),
                CsvSupport.cleanValue(csvRecord.get(8)),
                CsvSupport.cleanValue(csvRecord.get(9)),
                CsvSupport.parseInteger(CsvSupport.cleanValue(csvRecord.get(10))),
                CsvSupport.parseInteger(CsvSupport.cleanValue(csvRecord.get(11))),
                CsvSupport.parseInteger(CsvSupport.cleanValue(csvRecord.get(12))),
                CsvSupport.cleanValue(csvRecord.get(13)),
                CsvSupport.cleanValue(csvRecord.get(14)),
                CsvSupport.cleanValue(csvRecord.get(15)),
                CsvSupport.cleanValue(csvRecord.get(16)),
                CsvSupport.cleanValue(csvRecord.get(17)),
                CsvSupport.cleanValue(csvRecord.get(18)),
                CsvSupport.cleanValue(csvRecord.get(19))
        );
    }

    private boolean isValidMatchRecord(CSVRecord csvRecord) {
        return csvRecord.size() >= 20
                && isInteger(csvRecord.get(0))
                && isInteger(csvRecord.get(6))
                && isInteger(csvRecord.get(7));
    }

    private boolean isInteger(String value) {
        String cleaned = CsvSupport.cleanValue(value);
        if (cleaned.isBlank()) {
            return false;
        }

        try {
            Integer.parseInt(cleaned);
            return true;
        } catch (NumberFormatException exception) {
            return false;
        }
    }

    private PlayerCsvRow toPlayerRow(CSVRecord csvRecord) {
        return new PlayerCsvRow(
                CsvSupport.cleanValue(csvRecord.get(0)),
                CsvSupport.cleanValue(csvRecord.get(1)),
                CsvSupport.cleanValue(csvRecord.get(2)),
                CsvSupport.cleanValue(csvRecord.get(3)),
                CsvSupport.cleanValue(csvRecord.get(4)),
                CsvSupport.cleanValue(csvRecord.get(5)),
                CsvSupport.cleanValue(csvRecord.get(6)),
                CsvSupport.cleanValue(csvRecord.get(7)),
                csvRecord.size() > 8 ? CsvSupport.cleanValue(csvRecord.get(8)) : ""
        );
    }
}
