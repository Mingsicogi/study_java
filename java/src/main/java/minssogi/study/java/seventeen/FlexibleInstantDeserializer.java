package minssogi.study.java.seventeen;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FlexibleInstantDeserializer extends JsonDeserializer<Instant> {

    public static final FlexibleInstantDeserializer INSTANT = new FlexibleInstantDeserializer();
    private static final DateTimeFormatter DEFAULT_ISO_FORMATTER = DateTimeFormatter.ISO_INSTANT;
    private static final List<DateTimeFormatter> FORMATTER_LIST = new ArrayList<>();
    private static final List<Function<String, Instant>> PARSER_LIST = new ArrayList<>();

    static {
        FORMATTER_LIST.add(DEFAULT_ISO_FORMATTER);
    }

    @Override
    public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        final String text = jsonParser.getText();
        if (text == null || text.trim().isEmpty()) {
            return null;
        }

        for (DateTimeFormatter formatter : FORMATTER_LIST) {
            try {
                return ZonedDateTime.parse(text, formatter).toInstant();
            } catch (DateTimeException ignored) { }
        }

        for (Function<String, Instant> parser : PARSER_LIST) {
            try {
                return parser.apply(text);
            } catch (Exception ignored) { }
        }

        throw new RuntimeException("Failed to parse Instant value : " + text);
    }

    public void addFormatter(DateTimeFormatter formatter) {
        FORMATTER_LIST.add(formatter);
    }

    public void addParser(Function<String, Instant> formatter) {
        PARSER_LIST.add(formatter);
    }
}
