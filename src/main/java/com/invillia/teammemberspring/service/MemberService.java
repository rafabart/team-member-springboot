package com.invillia.teammemberspring.service;


import com.invillia.teammemberspring.domain.Member;
import com.invillia.teammemberspring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private MemberRepository memberRepository;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public void save(Member member) {
        memberRepository.save(member);
    }

    public void update(Member member) {
        Member memberTemp = memberRepository.findById(member.getId()).get();
        try {
//            member.setTeam(memberTemp.getTeam());
            member.setCreatedAt(memberTemp.getCreatedAt());
        } finally {
            memberRepository.save(member);
        }
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).isEmpty() ? null : memberRepository.findById(id).get();
    }

    public List<Member> findByNameContainingIgnoreCase(String name) {
        return memberRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Member> findByTeamNameContainingIgnoreCase(String teamName) {
        return memberRepository.findByTeamNameContainingIgnoreCase(teamName);
    }

    public List<Member> findAllById(Long id) {
        return memberRepository.findAllById(id);
    }
}
