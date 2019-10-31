package com.invillia.teammemberspring.controller;

import com.invillia.teammemberspring.domain.Team;
import com.invillia.teammemberspring.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/add")
    public String showNewTeam(Team team) {
        return "team/add-team";
    }

    @PostMapping("/save")
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

    @PostMapping("/update/{id}")
    public String updateTeam(@PathVariable("id") long id, @Valid Team team, BindingResult result, Model model) {
        if (result.hasErrors()) {
            team.setId(id);
            return "team/update-team";
        }

        teamService.update(team);
        model.addAttribute("teams", teamService.findAll());
        return "team/teams";
    }

    @GetMapping("delete/{id}")
    public String deleteTeam(@PathVariable("id") long id, Model model) {
        teamService.delete(id);
        model.addAttribute("teams", teamService.findAll());
        return "team/teams";
    }

    @GetMapping("/search")
    public String searchMember(@RequestParam("searchTerm") String searchTerm,
                               @RequestParam("searchType") String searchType,
                               Model model) {

        switch (searchType) {
            case "Id":
                try {
                    model.addAttribute("teams", teamService.findAllById(Long.valueOf(searchTerm)));
                } catch (Exception e) {
                    model.addAttribute("teams", new ArrayList<Team>());
                }
                break;
            case "Nome":
                model.addAttribute("teams", teamService.findByNameContainingIgnoreCase(searchTerm));
                break;
        }

        return "team/teams";
    }
}
