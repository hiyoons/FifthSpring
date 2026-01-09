package com.example.FifthSpring.repository;

import com.example.FifthSpring.model.PlayListArticle;
import com.example.FifthSpring.model.TagPlaylistMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistHashTagRepository extends JpaRepository<TagPlaylistMap,Long> {
    List<TagPlaylistMap> findAllByPlayListArticle(PlayListArticle playListArticle);

    void deleteByPlayListArticle(PlayListArticle playListArticle);
}
