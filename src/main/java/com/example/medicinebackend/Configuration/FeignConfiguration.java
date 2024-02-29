package com.example.medicinebackend.Configuration;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import java.io.StringReader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import feign.Logger;
import feign.Response;
import feign.Util;
import feign.codec.Decoder;
import feign.jackson.JacksonDecoder;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

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
            try {
                handleXmlFieldsAsString(jsonNode);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // 수정된 JsonNode를 원래의 타입으로 변환하여 반환합니다.
            return jsonMapper.treeToValue(jsonNode, jsonMapper.constructType(type));
        }

        private void handleXmlFieldsAsString(JsonNode node) throws Exception {
            if (node.isObject()) {
                Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
                while (fields.hasNext()) {
                    Map.Entry<String, JsonNode> field = fields.next();
                    String key = field.getKey();
                    JsonNode value = field.getValue();

                    // _DOC_DATA로 끝나는 필드가 XML 형태의 문자열을 포함하고 있는 경우
                    if (key.endsWith("_DOC_DATA") && value.isTextual()) {
                        String xml = value.textValue();
                        String simpleText = convertXmlToSimpleText(xml);
                        // 변환된 단순 텍스트를 JSON 노드에 저장
                        ((ObjectNode) node).put(key, simpleText);
                    } else {
                        // 재귀적으로 모든 자식 노드를 처리
                        handleXmlFieldsAsString(value);
                    }
                }
            } else if (node.isArray()) {
                for (JsonNode subNode : node) {
                    handleXmlFieldsAsString(subNode);
                }
            }
        }

        public String convertXmlToSimpleText(String xml) throws Exception {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource(new StringReader(xml));
            Document doc = builder.parse(is);

            StringBuilder textBuilder = new StringBuilder();
            extractTextRecursively(doc.getDocumentElement(), textBuilder);

            // 최종 문자열에서 ||와 \n을 제거하거나 대체
            String finalText = textBuilder.toString();
            finalText = finalText
//                    .replaceAll(System.getProperty("line.separator"), " ")
                    .replaceAll("\\\"","");


            return finalText;
        }

        private void extractTextRecursively(Node node, StringBuilder textBuilder) {
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                switch (element.getTagName()) {
                    case "title":
                        // 제목 태그의 경우, 제목을 텍스트에 추가
                        textBuilder.append(element.getTextContent()).append(": ");
                        break;
                    case "PARAGRAPH":
                        // 단락 태그의 경우, 단락 내용을 텍스트에 추가하고 줄바꿈
                        textBuilder.append(element.getTextContent()).append("\n");
                        break;
                    // 추가적인 태그 처리가 필요한 경우, 여기에 로직 추가
                }
            }

            NodeList children = node.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                extractTextRecursively(children.item(i), textBuilder);
            }
        }
    }}

