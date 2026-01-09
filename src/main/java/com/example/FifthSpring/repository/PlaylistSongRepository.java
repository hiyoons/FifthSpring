package com.example.FifthSpring.repository;

import com.example.FifthSpring.model.PlayListArticle;
import com.example.FifthSpring.model.SongPlaylistMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaylistSongRepository extends JpaRepository<SongPlaylistMap,Long> {

    List<SongPlaylistMap> findAllByPlayListArticle(PlayListArticle playListArticle);

}
