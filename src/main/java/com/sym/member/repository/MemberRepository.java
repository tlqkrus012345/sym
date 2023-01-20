package com.sym.member.repository;

import com.sym.member.Member;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    void save(Member member);
    Optional<Member> findMemberByEmail(String email);
    Optional<Member> findMemberById(Long id);
    List<Member> findMembers();
}
