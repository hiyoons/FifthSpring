package com.example.FifthSpring.service;


import com.example.FifthSpring.model.Song;
import com.example.FifthSpring.repository.SongRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Service
public class SongService {
    private final SongRepository songRepository;

    public Optional<Song> findByHref(String href){
        return songRepository.findByHref(href);
    }

    public Song save(String href,String text){
        return songRepository.save(Song.builder().href(href).text(text).build());
    }
}
