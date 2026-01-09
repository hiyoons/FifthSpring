package com.example.FifthSpring.model;

import lombok.Data;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Data
public class MemberDetails implements UserDetails {
    private String username;
    private String password;
    private List<SimpleGrantedAuthority> authorities;
    private String displayName;
    private Long memberId;


    public MemberDetails(Member member, List<Authority> authorities){
        this.username=member.getEmail();//이메일로 로그인
        this.displayName=member.getName();//이름표시
        this.password=member.getPassword();
        this.memberId=member.getId();
        this.authorities=authorities.stream().map(authority -> new SimpleGrantedAuthority(authority.getAuthority())).toList();
    }
}
