package io.github.tallessantos.world_cup_api.business.infra.repository.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.StringReader;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Locale;

public final class CsvSupport {

    private static final DateTimeFormatter MATCH_DATE_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("d MMM yyyy - HH:mm")
            .toFormatter(Locale.ENGLISH);

    private CsvSupport() {
    }

    public static List<String> parseCsvLine(String line) {
        try (CSVParser parser = CSVFormat.DEFAULT.parse(new StringReader(line))) {
            CSVRecord csvRecord = parser.iterator().next();
            return csvRecord.stream()
                    .map(CsvSupport::cleanValue)
                    .toList();
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to parse CSV line", exception);
        }
    }

    public static String cleanValue(String value) {
        if (value == null) {
            return null;
        }

        String normalized = value
                .replace("rn\">", "")
                .replace("rn>", "")
                .replace('\uFEFF', ' ')
                .trim();

        return normalized.isBlank() ? "" : normalized;
    }

    public static String slugify(String rawValue) {
        String normalized = Normalizer.normalize(cleanValue(rawValue), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .replaceAll("[^a-z0-9]+", "-")
                .replaceAll("(^-|-$)", "");

        return normalized.isBlank() ? "unknown" : normalized;
    }

    public static String toDisplayName(String rawValue) {
        String cleaned = cleanValue(rawValue).toLowerCase(Locale.ROOT);
        String[] words = cleaned.split("\\s+");
        StringBuilder displayName = new StringBuilder();

        for (String word : words) {
            if (word.isBlank()) {
                continue;
            }

            if (!displayName.isEmpty()) {
                displayName.append(' ');
            }

            displayName.append(Character.toUpperCase(word.charAt(0)));
            if (word.length() > 1) {
                displayName.append(word.substring(1));
            }
        }

        return displayName.toString();
    }

    public static LocalDateTime parseMatchDateTime(String value) {
        try {
            return LocalDateTime.parse(cleanValue(value), MATCH_DATE_FORMATTER);
        } catch (DateTimeParseException exception) {
            return null;
        }
    }

    public static Integer parseInteger(String value) {
        String cleaned = cleanValue(value).replace(".", "");
        return cleaned.isBlank() ? null : Integer.parseInt(cleaned);
    }

    public static String playerId(String teamInitials, String playerName) {
        return slugify(teamInitials + "-" + playerName);
    }
}
