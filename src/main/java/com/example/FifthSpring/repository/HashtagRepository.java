package com.example.FifthSpring.repository;

import com.example.FifthSpring.model.HashTag;
import com.example.FifthSpring.model.TagArticleMap;
import com.example.FifthSpring.model.TagPlaylistMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HashtagRepository extends JpaRepository<HashTag,Long> {
    Optional<HashTag> findByTagName(String tagName);



}