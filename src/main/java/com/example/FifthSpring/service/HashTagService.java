package com.example.FifthSpring.service;

import com.example.FifthSpring.model.HashTag;
import com.example.FifthSpring.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class HashTagService {

    private final HashtagRepository hashtagRepository;

    public Optional<HashTag> findByTagName(String tagName) {

        return hashtagRepository.findByTagName(tagName);
    }

    public HashTag save(String tagName) {
        return hashtagRepository.save(HashTag.builder().tagName(tagName).build());
    }
}
