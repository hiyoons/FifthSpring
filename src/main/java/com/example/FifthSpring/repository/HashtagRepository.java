package com.example.FifthSpring.repository;

import com.example.FifthSpring.model.HashTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashtagRepository extends JpaRepository<HashTag,Long> {
    Optional<HashTag> findByTagName(String tagName);



}