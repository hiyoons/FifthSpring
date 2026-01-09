package com.example.FifthSpring.repository;

import com.example.FifthSpring.model.PlayListArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaylistArticleRepository extends JpaRepository<PlayListArticle,Long> {
    Optional<PlayListArticle> findById(Long id);
}
