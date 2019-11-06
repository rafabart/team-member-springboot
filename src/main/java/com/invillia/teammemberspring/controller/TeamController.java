package com.invillia.teammemberspring.controller;

import com.invillia.teammemberspring.domain.Team;
import com.invillia.teammemberspring.exception.ActionNotPermitedException;
import com.invillia.teammemberspring.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("/team")
public class TeamController {

    private TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/")
    public String findAll(Model model, @PageableDefault(size = 5) Pageable pageable) {
        model.addAttribute("teams", teamService.findAll(pageable));
        return "team/teams";
    }

    @GetMapping("/addNew")
    public String showNewTeam(Team team) {
        return "team/add-edit-team";
    }

    @PostMapping("/save")
    public String saveTeam(@Valid Team team, BindingResult result, Model model, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "team/add-edit-team";
        }
        teamService.save(team);
        attr.addFlashAttribute("success", "Novo time cadastrado com sucesso!");
        return "redirect:/team/";
    }

    @GetMapping("/showEdit/{id}")
    public String showEditTeam(@PathVariable("id") long id, Model model) {
        model.addAttribute("team", teamService.findById(id));
        return "team/add-edit-team";
    }

    @PostMapping("/update/{id}")
    public String updateTeam(@PathVariable("id") long id,
                             @Valid Team team, BindingResult result, Model model, RedirectAttributes attr) {
        if (result.hasErrors()) {
            team.setId(id);
            return "team/add-edit-team";
        }

        teamService.update(team);
        attr.addFlashAttribute("success", "Time alterado com sucesso!");
        return "redirect:/team/";
    }

    @GetMapping("delete/{id}")
    public String deleteTeam(@PathVariable("id") long id, Model model, RedirectAttributes attr) {
        teamService.delete(id);
        attr.addFlashAttribute("success", "Time excluido com sucesso!");
        return "redirect:/team/";
    }

    @GetMapping("/search")
    public String searchMember(@RequestParam("searchTerm") String searchTerm,
                               @RequestParam("searchType") String searchType,
                               @PageableDefault(size = 5) Pageable pageable,
                               RedirectAttributes attr,
                               Model model) {

        switch (searchType) {
            case "Id":
                try {
                    model.addAttribute(
                            "teams", teamService.findAllById(Long.valueOf(searchTerm), pageable));

                } catch (Exception e) {
                    model.addAttribute("teams", teamService.findAll(pageable));
                }
                break;
            case "Nome":
                model.addAttribute(
                        "teams", teamService.findByNameContainingIgnoreCase(searchTerm, pageable));
                break;
        }
        return "team/teams";
    }

    @ExceptionHandler(ActionNotPermitedException.class)
    public void exceptionHandler(HttpServletResponse response, Exception e) throws IOException {
        response.sendError(HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage());
    }
}
