package com.practice.springbasic.repository;

import com.practice.springbasic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<Member, Long>, MemberRepository {

    @Override
    Optional<Member> findByNicknameAndPassword(String nickname, String password);

    @Override
    void delete(Member member);
}
