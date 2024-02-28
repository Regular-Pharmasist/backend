package com.example.medicinebackend.Repository;

import com.example.medicinebackend.Entitiy.Member;
import com.example.medicinebackend.Response.OAuthInfoResponse;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByNickname(String nickname);
}
