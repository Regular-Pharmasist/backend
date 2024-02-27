package com.example.medicinebackend.Configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import feign.Logger;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfiguration {
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public Decoder feignDecoder() {
        return new CustomDecoder();
    }


    public static class CustomDecoder implements Decoder {
        private final ObjectMapper jsonMapper;
        private final XmlMapper xmlMapper;

        public CustomDecoder() {
            this.jsonMapper = new ObjectMapper();
            this.xmlMapper = new XmlMapper();
        }


        @Override
        public Object decode(Response response, Type type) throws IOException {
            if (response.body() == null) return null;

            String bodyStr = feign.Util.toString(response.body().asReader(feign.Util.UTF_8));

            // JSON으로 일단 파싱을 시도합니다.
            JsonNode jsonNode = jsonMapper.readTree(bodyStr);

            // _DOC_DATA를 포함하는 모든 필드를 찾아서 추가 처리합니다.
            handleXmlFieldsAsString(jsonNode);

            // 수정된 JsonNode를 원래의 타입으로 변환하여 반환합니다.
            return jsonMapper.treeToValue(jsonNode, jsonMapper.constructType(type));
        }

        private void handleXmlFieldsAsString(JsonNode node) {

        }
    }
}

