package com.invillia.teammemberspring.service;


import com.invillia.teammemberspring.domain.Member;
import com.invillia.teammemberspring.exception.ActionNotPermitedException;
import com.invillia.teammemberspring.exception.MemberNotFoundException;
import com.invillia.teammemberspring.exception.TeamNotFoundException;
import com.invillia.teammemberspring.repository.MemberRepository;
import com.invillia.teammemberspring.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    private TeamRepository teamRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository, TeamRepository teamRepository) {
        this.memberRepository = memberRepository;
        this.teamRepository = teamRepository;
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    public void update(Member member) {
        Member memberTemp = memberRepository.findById(member.getId()).get();
        try {
            member.setCreatedAt(memberTemp.getCreatedAt());
        } finally {
            memberRepository.save(member);
        }
    }

    public void delete(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new MemberNotFoundException(String.valueOf(id)));
        memberRepository.delete(member);
    }

    public Page<Member> findAll(Pageable pageable) {

        if (teamRepository.findAll().isEmpty()) {
            throw new TeamNotFoundException("cadastre um time antes de tentar cadastrar um membro!");
        }
        return memberRepository.findAll(pageable);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow(() -> new ActionNotPermitedException(String.valueOf(id)));
    }

    public Page<Member> findByNameContainingIgnoreCase(String name, Pageable pageable) {
        return memberRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    public Page<Member> findByTeamNameContainingIgnoreCase(String teamName, Pageable pageable) {
        return memberRepository.findByTeamNameContainingIgnoreCase(teamName, pageable);
    }

    public Page<Member> findAllById(Long id, Pageable pageable) {
        return memberRepository.findAllById(id, pageable);
    }
}
