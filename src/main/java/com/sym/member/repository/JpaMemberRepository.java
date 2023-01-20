package com.sym.member.repository;


import com.sym.member.Member;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
public class JpaMemberRepository implements MemberRepository{

    @PersistenceContext
    private EntityManager em;
    @Override
    public void save(Member member) {
        em.persist(member);
    }

    @Override
    public Optional<Member> findMemberByEmail(String email) {
        Member member = em.createQuery("select m from Member m where m.email = :email",
                Member.class)
            .setParameter("email", email)
            .getSingleResult();
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findMemberById(Long id) {
        return Optional.ofNullable(em.find(Member.class, id));
    }

    @Override
    public List<Member> findMembers() {
        return em.createQuery("select m from Member m", Member.class)
            .getResultList();
    }
}
