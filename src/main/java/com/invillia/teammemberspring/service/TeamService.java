package com.invillia.teammemberspring.service;

import com.invillia.teammemberspring.domain.Team;
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
        } finally {
            team.setCreatedAt(teamTemp.getCreatedAt());
            teamRepository.save(team);
        }
    }

    public void delete(Long id) {
        teamRepository.deleteById(id);
    }

    public List<Team> findAll() {
        return teamRepository.findAll();
    }

    public Team findById(Long id) {
        return teamRepository.findById(id).isEmpty() ? null : teamRepository.findById(id).get();
    }
}
