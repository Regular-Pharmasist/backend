package com.example.medicinebackend.Params;

import org.springframework.util.MultiValueMap;

public interface OAuthLoginParams {
    MultiValueMap<String, String> makeBody();
}
