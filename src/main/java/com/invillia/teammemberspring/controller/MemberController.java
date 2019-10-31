package com.invillia.teammemberspring.controller;

import com.invillia.teammemberspring.domain.Member;
import com.invillia.teammemberspring.domain.Team;
import com.invillia.teammemberspring.service.MemberService;
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
        model.addAttribute("teams", teamService.findAll());
        return "member/add-member";
    }

    @PostMapping("/save")
    public String saveMember(@Valid Member member, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "member/add-member";
        }

        memberService.save(member);
        model.addAttribute("members", memberService.findAll());
        return "member/members";
    }

    @GetMapping("/edit/{id}")
    public String showEditMember(@PathVariable("id") long id, Model model) {
        Member member = memberService.findById(id);
        model.addAttribute("teams", teamService.findAll());
        model.addAttribute("member", member);
        return "member/update-member";
    }

    @PostMapping("/update/{id}")
    public String updateMember(@PathVariable("id") long id, @Valid Member member, BindingResult result, Model model) {
        if (result.hasErrors()) {
            member.setId(id);
            return "member/update-member";
        }

        memberService.update(member);
        model.addAttribute("members", memberService.findAll());
        return "member/members";
    }

    @GetMapping("delete/{id}")
    public String deleteMember(@PathVariable("id") long id, Model model) {
        memberService.delete(id);
        model.addAttribute("members", memberService.findAll());
        return "member/members";
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
}
