package com.fog.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.fog.member.entity.Member;


public interface MemberRepository extends JpaRepository<Member,Long> {
    //회원 가입시 중복되지 않기 위해 이메일 검색하는 쿼리 메소드 추가
    Member findByEmail(String email);
    Member findByProviderId(String ProviderId);
    
    // optional 타입으로 하고 findById(Long id)하는게 맞는데 빠르게 작업하기 위해 일단 이렇게 추후 변경
    Member findMemberById(Long id);
    
    // 조회수 증가
    @Modifying
    @Query("update Member m set m.view = m.view + 1 where m.id = :id")
    int updateView(Long id);
}
