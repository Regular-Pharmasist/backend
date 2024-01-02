package com.example.medicinebackend;


import com.example.medicinebackend.GoogleCloudVision.GoogleCloudVisionAPI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageProcessService {
    public List<String> extractTextFromImage(MultipartFile imageFile) {
        List<String> medicineNames = GoogleCloudVisionAPI.detectText(imageFile);
        return medicineNames;
    }
}
