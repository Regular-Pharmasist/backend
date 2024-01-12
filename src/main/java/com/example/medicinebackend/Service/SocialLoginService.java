package com.example.medicinebackend.Service;

import com.example.medicinebackend.JWT.AuthTokens;
import com.example.medicinebackend.JWT.AuthTokensGenerator;
import com.example.medicinebackend.Params.OAuthLoginParams;
import com.example.medicinebackend.Repository.MemberRepository;
import com.example.medicinebackend.Response.OAuthInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.medicinebackend.Entitiy.Member;


@Service
@RequiredArgsConstructor
@Slf4j
public class SocialLoginService {
    private final MemberRepository memberRepository;
    private final AuthTokensGenerator authTokensGenerator;
    private final RequestOAuthInfoService requestOAuthInfoService;

    public AuthTokens login(OAuthLoginParams params){
        OAuthInfoResponse oAuthInfoResponse = requestOAuthInfoService.request(params);
        log.info("LOG : Received response from Kakao API: {}", oAuthInfoResponse.getNickname());

        Long memberId= findOrCreateMember(oAuthInfoResponse);
        return authTokensGenerator.generate(memberId);
    }

    private Long findOrCreateMember(OAuthInfoResponse oAuthInfoResponse) {
        return memberRepository.findByNickname(oAuthInfoResponse.getNickname())
                .map(Member::getMemberId)
                .orElseGet(()-> newMember(oAuthInfoResponse));
    }

    private Long newMember(OAuthInfoResponse oAuthInfoResponse) {
        Member member = Member.builder()
                .nickname(oAuthInfoResponse.getNickname())
                .profileImageUrl(oAuthInfoResponse.getProfileImage())
                .build();
        return memberRepository.save(member).getMemberId();
    }

}
