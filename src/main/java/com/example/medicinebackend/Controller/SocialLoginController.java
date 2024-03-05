package com.example.medicinebackend.Controller;

import com.example.medicinebackend.Entitiy.Member;
import com.example.medicinebackend.JWT.AuthTokens;
import com.example.medicinebackend.JWT.AuthTokensGenerator;
import com.example.medicinebackend.Params.KakaoLoginParams;
import com.example.medicinebackend.Response.MemberResponseDto;
import com.example.medicinebackend.Service.SocialLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class SocialLoginController {
    private final SocialLoginService socialLoginService;
    private final AuthTokensGenerator authTokensGenerator;

    @PostMapping("/login/kakao")
    public ResponseEntity<AuthTokens> loginKakao(@RequestBody KakaoLoginParams params){
        return ResponseEntity.ok(socialLoginService.login(params));
    }

    @GetMapping("/profile")
    public MemberResponseDto getMember(@RequestHeader("Authorization") String tokenHeader){
        String token = tokenHeader.substring(7);
        Long memberId = authTokensGenerator.extractMemberId(token);
        return socialLoginService.getMemberInfo(memberId);
    }
}
