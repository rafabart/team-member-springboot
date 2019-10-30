package com.invillia.teammemberspring.controller;

import com.invillia.teammemberspring.domain.Member;
import com.invillia.teammemberspring.domain.Team;
import com.invillia.teammemberspring.service.MemberService;
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
        return "member/members";
    }

    @GetMapping("/add")
    public String showNewMember(Member member, Model model) {
        model.addAttribute("teams", teamService.findAll());
        return "member/add-member";
    }

    @PostMapping("/save")
    public String saveMember(@Valid Member member, BindingResult result, Model model) {
        System.out.println(member.getName());
        System.out.println(member.getTeam());

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
}
