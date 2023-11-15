package minssogi.study.java.seventeen;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoUnit;

public class ObjectMapperUtils {

    public static ObjectMapper createGeneralObjectMapper() {
        final ObjectMapper objectMapper = new ObjectMapper(JsonFactory.builder()
                .configure(JsonFactory.Feature.CANONICALIZE_FIELD_NAMES, false)
                .configure(JsonFactory.Feature.INTERN_FIELD_NAMES, false)
                .build());

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        final JavaTimeModule javaTimeModule = new JavaTimeModule();

        FlexibleInstantDeserializer flexibleInstantDeserializer = FlexibleInstantDeserializer.INSTANT;

        // flexible datetime format
        flexibleInstantDeserializer.addFormatter(new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd'T'HH:mm:ss[.SSS][.SS][.S]X").toFormatter().withZone(ZoneId.of("UTC")));
        flexibleInstantDeserializer.addFormatter(new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm").toFormatter().withZone(ZoneId.of("UTC")));
        flexibleInstantDeserializer.addFormatter(new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd HH:mm:ss[.S]").toFormatter().withZone(ZoneId.of("UTC")));

        // other
        flexibleInstantDeserializer.addParser((text) -> {
            final String[] parts = text.split("\\.");

            final long seconds = Long.parseLong(parts[0]);
            final int nanoseconds = parts.length == 2 ? Integer.parseInt(parts[1]) : 0;

            return Instant.ofEpochSecond(seconds, nanoseconds).truncatedTo(ChronoUnit.MILLIS);
        });

        javaTimeModule.addDeserializer(Instant.class, flexibleInstantDeserializer);

        objectMapper.registerModule(javaTimeModule);

        return objectMapper;
    }

}
