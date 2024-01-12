package com.example.medicinebackend.Service;

import com.example.medicinebackend.Client.OAuthApiClient;
import com.example.medicinebackend.Params.OAuthLoginParams;
import com.example.medicinebackend.Response.OAuthInfoResponse;
import org.springframework.stereotype.Component;

@Component
public class RequestOAuthInfoService {
    private final OAuthApiClient client;
    public RequestOAuthInfoService(OAuthApiClient client){
        this.client = client;
    }

    public OAuthInfoResponse request(OAuthLoginParams params){
        String accessToken = client.requestAccessToken(params);
        return client.requestOauthInfo(accessToken);
    }

}
