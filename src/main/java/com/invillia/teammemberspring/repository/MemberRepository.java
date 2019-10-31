package com.invillia.teammemberspring.repository;

import com.invillia.teammemberspring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    List<Member> findByNameContainingIgnoreCase(String name);

    List<Member> findByTeamNameContainingIgnoreCase(String teamName);

    List<Member> findAllById(Long id);
}
