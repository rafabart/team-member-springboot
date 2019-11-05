package com.invillia.teammemberspring.repository;

import com.invillia.teammemberspring.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    Page<Member> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Member> findByTeamNameContainingIgnoreCase(String teamName, Pageable pageable);

    Page<Member> findAllById(Long id, Pageable pageable);
}
