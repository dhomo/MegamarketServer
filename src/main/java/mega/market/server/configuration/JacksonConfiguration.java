package mega.market.server.configuration;

import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;


@Configuration
public class JacksonConfiguration {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        return builder -> builder.serializers(new CustomInstantSerializer());
    }

    private static class CustomInstantSerializer extends InstantSerializer {

        private static final DateTimeFormatter UTC_DATE_FORMAT = DateTimeFormatter
                .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withZone(ZoneOffset.UTC);

        private CustomInstantSerializer() {
            super(InstantSerializer.INSTANCE, null, UTC_DATE_FORMAT);
        }

    }
}
