package mega.market.server.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;


@Configuration
public class JacksonConfiguration {

//  @Bean
//  @Primary
//  public ObjectMapper objectMapper() {
//    ObjectMapper objectMapper = JsonMapper.builder()
//            .addModule(new JavaTimeModule())
//            .build();
//
////    ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
//
//    return objectMapper;
//  }
}
