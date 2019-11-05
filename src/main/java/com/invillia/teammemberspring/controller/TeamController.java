package com.invillia.teammemberspring.controller;

import com.invillia.teammemberspring.domain.Team;
import com.invillia.teammemberspring.exception.ActionNotPermitedException;
import com.invillia.teammemberspring.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequestMapping("/team")
public class TeamController {

    private TeamService teamService;

    private final int teamPerPage = 5;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/")
    public String findAll(Model model,@RequestParam(defaultValue = "0") int page) {
        model.addAttribute("teams", teamService.findAll(page,teamPerPage));
        model.addAttribute("currentPage", page);
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
        return "redirect:/team/";
    }

    @GetMapping("/edit/{id}")
    public String showEditTeam(@PathVariable("id") long id, Model model) {
        model.addAttribute("team", teamService.findById(id));
        return "team/update-team";
    }

    @PostMapping("/update/{id}")
    public String updateTeam(@PathVariable("id") long id, @Valid Team team, BindingResult result, Model model) {
        if (result.hasErrors()) {
            team.setId(id);
            return "team/update-team";
        }

        teamService.update(team);
        return "redirect:/team/";
    }

    @GetMapping("delete/{id}")
    public String deleteTeam(@PathVariable("id") long id, Model model) {
        teamService.delete(id);
        return "redirect:/team/";
    }

    @GetMapping("/search")
    public String searchMember(@RequestParam("searchTerm") String searchTerm,
                               @RequestParam("searchType") String searchType,
                               @RequestParam(defaultValue = "0") int page,
                               Model model) {

        switch (searchType) {
            case "Id":
                try {
                    model.addAttribute("teams", teamService.findAllById(Long.valueOf(searchTerm),page,teamPerPage));
                } catch (Exception e) {
                    model.addAttribute("teams", new ArrayList<Team>());
                }
                break;
            case "Nome":
                model.addAttribute("teams", teamService.findByNameContainingIgnoreCase(searchTerm,page,teamPerPage));
                break;
        }
        model.addAttribute("currentPage", page);
        return "team/teams";
    }

    @ExceptionHandler(ActionNotPermitedException.class)
    public void exceptionHandler(HttpServletResponse response, Exception e) throws IOException {
        response.sendError(HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage());
    }
}
