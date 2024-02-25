package com.example.medicinebackend.Service;

import com.example.medicinebackend.Entitiy.Member;
import com.example.medicinebackend.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    public Member getCurrentMemberId(){

        throw new RuntimeException("No authenticated user found");
    }
}
