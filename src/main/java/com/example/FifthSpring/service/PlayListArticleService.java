package com.example.FifthSpring.service;


import com.example.FifthSpring.dto.PlayListDto;
import com.example.FifthSpring.model.*;
import com.example.FifthSpring.repository.PlaylistArticleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PlayListArticleService {

    private final PlaylistArticleRepository playlistArticleRepository;
    private final PlayListHashTagService playListHashTagService;
    private final PlayListSongService playListSongService;


    private PlayListDto mapToPlayListDto(PlayListArticle playListArticle){

        PlayListArticle targetArticle = playlistArticleRepository.findById(playListArticle.getId()).orElseThrow();
        List<TagPlaylistMap> tagPlaylistMaps =
                playListHashTagService.findHashtagListByPlaylistArticle(targetArticle);


        List<SongPlaylistMap> songPlaylistMaps=playListSongService.findSongListByPlayList(targetArticle);


        List<HashTag> tagList = tagPlaylistMaps.stream().map(TagPlaylistMap::getHashTag).toList();

        List<Song> songList = songPlaylistMaps.stream().map(SongPlaylistMap::getSong).toList();

        return PlayListDto.builder().id(targetArticle.getId()).latitude(targetArticle.getLatitude()).longitude(targetArticle.getLongitude()).songList(songList).tagList(tagList).created(targetArticle.getCreated()).updated(targetArticle.getUpdated()).address(targetArticle.getAddress()).build();
    }

    public Page<PlayListDto> findAll(Pageable pageable){
        return playlistArticleRepository.findAll(pageable).map(this::mapToPlayListDto);
    }
    public PagedModel<PlayListDto> mapFindAll(Pageable pageable){
        Page<PlayListDto> findResponse = playlistArticleRepository.findAll(pageable).map(this::mapToPlayListDto);
        return new PagedModel<>(findResponse);
    }

    public PlayListDto findById(Long id){
        return playlistArticleRepository.findById(id).map(this::mapToPlayListDto).orElseThrow();
    }

}
