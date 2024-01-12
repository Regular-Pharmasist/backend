package com.example.medicinebackend.Controller;

import com.example.medicinebackend.JWT.AuthTokens;
import com.example.medicinebackend.Params.KakaoLoginParams;
import com.example.medicinebackend.Response.KakaoTokens;
import com.example.medicinebackend.Service.SocialLoginService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SocialLoginController {
    private final SocialLoginService socialLoginService;

    @PostMapping("/login/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginParams params){
        return ResponseEntity.ok(socialLoginService.login(params));
    }
}
