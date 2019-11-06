package com.invillia.teammemberspring.controller;

import com.invillia.teammemberspring.domain.Member;
import com.invillia.teammemberspring.exception.ActionNotPermitedException;
import com.invillia.teammemberspring.service.MemberService;
import com.invillia.teammemberspring.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;

    private TeamService teamService;

    private final int teamPerPage = 5;

    @Autowired
    public MemberController(MemberService memberService, TeamService teamService) {
        this.memberService = memberService;
        this.teamService = teamService;
    }

    @GetMapping("/")
    public String findAll(Model model, @PageableDefault(size = 5) Pageable pageable) {
        model.addAttribute("members", memberService.findAll(pageable));
        return "member/members";
    }

    @GetMapping("/addNew")
    public String showNewMember(Member member, Model model) {
        model.addAttribute("teams", teamService.findAll());
        return "member/add-edit-member";
    }

    @PostMapping("/save")
    public String saveMember(@Valid Member member, BindingResult result, Model model, RedirectAttributes attr) {
        if (result.hasErrors()) {
            model.addAttribute("teams", teamService.findAll());
            return "member/add-edit-member";
        }

        memberService.save(member);
        attr.addFlashAttribute("success", "Novo membro cadastrado com sucesso!");
        return "redirect:/member/";
    }

    @GetMapping("/showEdit/{id}")
    public String showEditMember(@PathVariable("id") long id, Model model) {
        model.addAttribute("teams", teamService.findAll());
        model.addAttribute("member", memberService.findById(id));
        return "member/add-edit-member";
    }

    @PostMapping("/update/{id}")
    public String updateMember(@PathVariable("id") long id,
                               @Valid Member member, BindingResult result, Model model, RedirectAttributes attr) {
        if (result.hasErrors()) {
            member.setId(id);
            model.addAttribute("teams", teamService.findAll());
            return "member/add-edit-member";
        }

        memberService.update(member);
        attr.addFlashAttribute("success", "Membro alterado com sucesso!");
        return "redirect:/member/";
    }

    @GetMapping("delete/{id}")
    public String deleteMember(@PathVariable("id") long id, Model model, RedirectAttributes attr) {
        memberService.delete(id);
        attr.addFlashAttribute("success", "Membro excluído com sucesso!");
        return "redirect:/member/";
    }

    @GetMapping("/search")
    public String searchMember(@RequestParam("searchTerm") String searchTerm,
                               @RequestParam("searchType") String searchType,
                               @PageableDefault(size = 5) Pageable pageable,
                               Model model) {

        switch (searchType) {
            case "Id":
                try {
                    model.addAttribute(
                            "members", memberService.findAllById(Long.valueOf(searchTerm), pageable));
                } catch (Exception e) {
                    model.addAttribute("members", memberService.findAll(pageable));
                }
                break;
            case "Nome":
                model.addAttribute(
                        "members", memberService.findByNameContainingIgnoreCase(searchTerm, pageable));
                break;
            case "Time":
                model.addAttribute("members", memberService.findByTeamNameContainingIgnoreCase(
                        searchTerm, pageable));
                break;
        }
        return "member/members";
    }

    @ExceptionHandler(ActionNotPermitedException.class)
    public void exceptionHandler(HttpServletResponse response, Exception e) throws IOException {
        response.sendError(HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage());
    }
}
