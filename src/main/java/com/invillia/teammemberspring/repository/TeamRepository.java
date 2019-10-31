package com.invillia.teammemberspring.repository;

import com.invillia.teammemberspring.domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    List<Team> findByNameContainingIgnoreCase(String name);

    List<Team> findAllById(Long id);
}
