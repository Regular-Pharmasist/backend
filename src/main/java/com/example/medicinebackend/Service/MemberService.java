package com.example.medicinebackend.Service;

import com.example.medicinebackend.Entitiy.Member;
import com.example.medicinebackend.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public Member getCurrentMemberId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String nickname = ((UserDetails)principal).getUsername();
                // username을 사용하여 사용자 ID를 찾는 로직 구현
                return memberRepository.findByNickname(nickname)
                        .orElseThrow(() -> new RuntimeException("Member not found"));
            }
        }
        throw new RuntimeException("No authenticated user found");
    }
}
