package com.example.medicinebackend.FeignClient;

import com.example.medicinebackend.Params.OAuthLoginParams;
import com.example.medicinebackend.Response.OAuthInfoResponse;

public interface OAuthApiClient {
    //인증 api 요청 -> Access token 획득
    String requestAccessToken(OAuthLoginParams params);
    //access token 기반으로 nickname, profile image url 프로필 정보 획득
    OAuthInfoResponse requestOauthInfo(String accessToken);
}
