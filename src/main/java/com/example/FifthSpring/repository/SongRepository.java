package com.example.FifthSpring.repository;

import com.example.FifthSpring.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SongRepository extends JpaRepository<Song,Long> {
    Optional<Song> findByHref(String href);
}
