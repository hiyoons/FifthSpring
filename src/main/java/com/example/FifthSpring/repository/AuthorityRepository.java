package com.example.FifthSpring.repository;

import com.example.FifthSpring.model.Authority;
import com.example.FifthSpring.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthorityRepository extends JpaRepository<Authority,Long> {

    List<Authority> findByMember(Member member);
}
