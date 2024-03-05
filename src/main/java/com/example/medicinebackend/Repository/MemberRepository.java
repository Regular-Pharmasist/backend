package com.example.medicinebackend.Repository;

import com.example.medicinebackend.Entitiy.Member;
import com.example.medicinebackend.Response.OAuthInfoResponse;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findByNickname(String nickname);
    Member findByMemberId(Long memberId);
    @Query("SELECT nickname FROM Member WHERE memberId = :memberId")
    String findNicknameByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT profileImageUrl FROM Member WHERE memberId = :memberId")
    String findProfileImageUrlByMemberId(@Param("memberId")Long memberId);

    @Query("SELECT memberId FROM Member WHERE nickname = :nickname")
    Optional<Member> findMemberIdByNickname(@Param("nickname") String nickname);
}
