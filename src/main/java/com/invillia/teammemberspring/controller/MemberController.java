package com.invillia.teammemberspring.controller;

import com.invillia.teammemberspring.domain.Member;
import com.invillia.teammemberspring.domain.Team;
import com.invillia.teammemberspring.exception.ActionNotPermitedException;
import com.invillia.teammemberspring.service.MemberService;
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
import java.util.List;

@Controller
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;

    private TeamService teamService;

    @Autowired
    public MemberController(MemberService memberService, TeamService teamService) {
        this.memberService = memberService;
        this.teamService = teamService;
    }

    @GetMapping("/")
    public String findAll(Model model) {
        model.addAttribute("members", memberService.findAll());
        model.addAttribute("search", "");
        return "member/members";
    }

    @GetMapping("/add")
    public String showNewMember(Member member, Model model) {
        List<Team> teams = teamService.findAll();
        if(!teams.isEmpty()){
            model.addAttribute("teams", teams);
            return "member/add-member";
        }
        return "member/teamEmpty-member";
    }

    @PostMapping("/save")
    public String saveMember(@Valid Member member, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("teams", teamService.findAll());
            return "member/add-member";
        }
        memberService.save(member);
        return "redirect:/member/";
    }

    @GetMapping("/edit/{id}")
    public String showEditMember(@PathVariable("id") long id, Model model) {
        model.addAttribute("teams", teamService.findAll());
        model.addAttribute("member", memberService.findById(id));
        return "member/update-member";
    }

    @PostMapping("/update/{id}")
    public String updateMember(@PathVariable("id") long id, @Valid Member member, BindingResult result, Model model) {
        if (result.hasErrors()) {
            member.setId(id);
            model.addAttribute("teams", teamService.findAll());
            return "member/update-member";
        }
        memberService.update(member);
        return "redirect:/member/";
    }

    @GetMapping("delete/{id}")
    public String deleteMember(@PathVariable("id") long id, Model model) {
        memberService.delete(id);
        return "redirect:/member";
    }

    @GetMapping("/search")
    public String searchMember(@RequestParam("searchTerm") String searchTerm,
                               @RequestParam("searchType") String searchType,
                               Model model) {

        switch (searchType) {
            case "Id":
                try {
                    model.addAttribute("members", memberService.findAllById(Long.valueOf(searchTerm)));
                } catch (Exception e) {
                    model.addAttribute("members", new ArrayList<Member>());
                }
                break;
            case "Nome":
                model.addAttribute("members", memberService.findByNameContainingIgnoreCase(searchTerm));
                break;
            case "Time":
                model.addAttribute("members", memberService.findByTeamNameContainingIgnoreCase(searchTerm));
                break;
        }
        return "member/members";
    }

    @ExceptionHandler(ActionNotPermitedException.class)
    public void exceptionHandler(HttpServletResponse response, Exception e) throws IOException {
        response.sendError(HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage());
    }
}
