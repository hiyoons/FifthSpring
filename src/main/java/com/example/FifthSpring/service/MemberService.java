package com.example.FifthSpring.service;

import com.example.FifthSpring.dto.MemberDto;
import com.example.FifthSpring.dto.MemberForm;
import com.example.FifthSpring.model.Member;
import com.example.FifthSpring.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor//final 이나 @NotNULL인 값만 PARAMETER로 받는 생성자를 추가
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberDto findById(Long id){
        return memberRepository.findById(id).map(this::mapToMemberDto).orElseThrow();//메서드를 통해 dto를 객체로 변환
    }

    private MemberDto mapToMemberDto(Member member){
        return MemberDto.builder().id(member.getId()).name(member.getName()).email(member.getEmail()).build();
    }

    public MemberDto create(MemberForm memberForm){
        Member member = Member.builder().name(memberForm.getName()).email(memberForm.getEmail()).password(passwordEncoder.encode(memberForm.getPassword())).build();
        memberRepository.save(member);
        return mapToMemberDto(member);
    }
    public Optional<MemberDto> findByEmail(String email){
        return memberRepository.findByEmail(email).map(this::mapToMemberDto);
    }

    public boolean checkPassword(Long id,String password){
        Member member= memberRepository.findById(id).orElseThrow();
        return passwordEncoder.matches(password,member.getPassword());
    }

    public void updatePassword(Long id,String password){
        Member member= memberRepository.findById(id).orElseThrow();
        member.setPassword(passwordEncoder.encode(password));
        memberRepository.save(member);
    }
}
