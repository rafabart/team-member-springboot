package com.invillia.teammemberspring.service;

import com.invillia.teammemberspring.domain.Team;
import com.invillia.teammemberspring.exception.ActionNotPermitedException;
import com.invillia.teammemberspring.exception.TeamNotFoundException;
import com.invillia.teammemberspring.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void save(Team team) {
        teamRepository.save(team);
    }

    public void update(Team team) {
        Team teamTemp = teamRepository.findById(team.getId()).get();
        try {
            team.setMembers(teamTemp.getMembers());
            team.setCreatedAt(teamTemp.getCreatedAt());
        } finally {
            teamRepository.save(team);
        }
    }

    public void delete(Long id) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new TeamNotFoundException(String.valueOf(id)));
        teamRepository.delete(team);
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Team findById(Long id) {
        return teamRepository.findById(id).orElseThrow(() -> new ActionNotPermitedException(String.valueOf(id)));
    }

    public List<Team> findByNameContainingIgnoreCase(String name) {
        return teamRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Team> findAllById(Long id) {
        return teamRepository.findAllById(id);
    }
}
