package com.example.medicinebackend.Service;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesRequest;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.ImageSource;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class GoogleCloudVisionApiService {
    static{
        System.setProperty("GOOGLE_APPLICATION_CREDENTIALS", "/Users/choyongeun/Documents/GitHub/RegularMedicine/backend/src/main/resources/medicine-411304-505fe523d410.json");
    }

    public List<String> medicineNameExtractor(MultipartFile imageFile){
        List<String> drugNames = new ArrayList<>();

        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
            // MultipartFile에서 이미지 데이터 읽기
            byte[] data = imageFile.getBytes();
            Image image = Image.newBuilder().setContent(com.google.protobuf.ByteString.copyFrom(data)).build();

            // 텍스트 감지를 위한 특징 설정
            Feature feature = Feature.newBuilder().setType(Type.TEXT_DETECTION).build();
            AnnotateImageRequest request =
                    AnnotateImageRequest.newBuilder().addFeatures(feature).setImage(image).build();

            List<AnnotateImageRequest> requests = new ArrayList<>();
            requests.add(request);

            // 이미지 처리 요청 및 응답 받기
            BatchAnnotateImagesResponse responses = vision.batchAnnotateImages(requests);

            // 약물 이름 처리 및 결과 리스트 반환
            for (AnnotateImageResponse response : responses.getResponsesList()) {
                String[] words = response.getTextAnnotationsList().get(0).getDescription().split("\\s+");
                for (String word : words) {
                    if (isDesiredWord(word)) {
                        drugNames.add(word);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("drugNames : {}", drugNames);
        return drugNames;
    }
    private static boolean isDesiredWord(String word) {
        // 파싱 단어 조건
        // 차후 상세 변동 요망
        return (word.contains("정") || word.contains("캡슐") || word.contains("필름") || word.contains("캅셀") || word.contains("시럽"))
                && !word.contains("정보") && !word.contains("정제") && !word.contains("캅셀제")&& !word.contains("시럽제");
    }

    private static MultipartFile getYourMultipartFile() {
        // MultipartFile 구체적인 로직
        return null;
    }
}
