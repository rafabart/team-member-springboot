package com.invillia.teammemberspring.controller;

import com.invillia.teammemberspring.domain.Team;
import com.invillia.teammemberspring.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/team")
public class TeamController {

    private TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/")
    public String findAll(Model model) {
        model.addAttribute("teams", teamService.findAll());
        return "team/teams";
    }

    @GetMapping("/add-team")
    public String showNewTeam(Team team) {
        return "team/add-team";
    }

    @PostMapping("/saveteam")
    public String saveTeam(@Valid Team team, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "team/add-team";
        }

        teamService.save(team);
        model.addAttribute("teams", teamService.findAll());
        return "team/teams";
    }

    @GetMapping("/edit/{id}")
    public String showEditTeam(@PathVariable("id") long id, Model model) {
        Team team = teamService.findById(id);
        model.addAttribute("team", team);
        return "team/update-team";
    }
}