package com.invillia.teammemberspring.repository;

import com.invillia.teammemberspring.domain.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Page<Team> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Team> findAllById(Long id, Pageable pageable);
}
