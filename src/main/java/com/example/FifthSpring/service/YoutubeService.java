package com.example.FifthSpring.service;


import com.example.FifthSpring.dto.PlayListDto;
import com.example.FifthSpring.dto.PlayListForm;
import com.example.FifthSpring.model.*;
import com.example.FifthSpring.repository.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import  com.google.api.client.json.gson.GsonFactory;
//import com.google.api.client.json.jackson2.JacksonFactory;

import com.google.api.services.youtube.YouTube;

import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class YoutubeService {

    @Value("${youtube.api.key}")
    private String API_KEY;


    private static final long MAX_RESULTS = 10;
    private final PlaylistArticleRepository playlistArticleRepository;


    private final PlayListHashTagService playListHashTagService;
    private final PlayListSongService playListSongService;


    public List<SearchResult> searchTopVideos(String query) throws GeneralSecurityException, IOException {
        YouTube youtubeService = new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                null
        ).setApplicationName("youtube-api-demo").build();

        YouTube.Search.List request = youtubeService.search()
                .list(Collections.singletonList("snippet"));

        SearchListResponse response = request.setKey(API_KEY)
                .setQ(query)
                .setType(Collections.singletonList("video"))
                .setOrder("viewCount") // 조회수 기준 정렬
                .setMaxResults(MAX_RESULTS)
                .execute();

        return response.getItems();
    }

    private PlayListDto mapToPlaysListDto(PlayListArticle playListArticle){

        List<TagPlaylistMap> tagPlaylistMaps =
                playListHashTagService.findHashtagListByPlaylistArticle(playListArticle);
        List<SongPlaylistMap> songPlaylistMaps =
                playListSongService.findSongListByPlayList(playListArticle);

        //변환하기
        List<HashTag> tagList = tagPlaylistMaps.stream().map(TagPlaylistMap::getHashTag).toList();

        List<Song> songLists = songPlaylistMaps.stream().map(SongPlaylistMap::getSong).toList();


        return PlayListDto.builder().id(playListArticle.getId()).latitude(playListArticle.getLatitude()).longitude(playListArticle.getLongitude()).address(playListArticle.getAddress()).songList(songLists).created(playListArticle.getCreated())
                .updated(playListArticle.getUpdated()).tagList(tagList).build();
    }

    public void save(Long id, PlayListForm playListForm){


        PlayListArticle playListArticle= PlayListArticle.builder().id(playListForm.getId()).latitude(playListForm.getLatitude()).longitude(playListForm.getLongitude()).address(playListForm.getAddress()).build();

        List<HashTag> tagList = playListForm.getTagList();
        List<TagPlaylistMap> tagPlaylistMapList = new ArrayList<TagPlaylistMap>();

        for(int i=0;i<tagList.size();i++){
            HashTag hashTag = HashTag.builder().tagName(tagList.get(i).getTagName()).build();
            TagPlaylistMap newtagMap = TagPlaylistMap.builder().playListArticle(playListArticle).hashTag(hashTag).build();

            tagPlaylistMapList.add(newtagMap);
        }

        playListArticle.setTagList(tagPlaylistMapList);

        List<Song> songList = playListForm.getSongList();
        List<SongPlaylistMap> songPlaylistMapList = new ArrayList<SongPlaylistMap>();
        for(int i=0;i<songList.size();i++){
            Song item = songList.get(i);
            Song newSong = Song.builder().text(item.getText()).href(item.getHref()).build();
            SongPlaylistMap songPlaylistMap = SongPlaylistMap.builder().playListArticle(playListArticle).song(newSong).build();
            System.out.println(songPlaylistMap);
            songPlaylistMapList.add(songPlaylistMap);
        }



        playListArticle.setSongList(songPlaylistMapList);

        playlistArticleRepository.save(playListArticle);



    }


    public void update(PlayListForm playListForm) {

        PlayListArticle playListArticle = playlistArticleRepository.findById(playListForm.getId()).orElseThrow();
        System.out.println("playlistarticle 기존 태그");




        List<HashTag> tagList = playListForm.getTagList();
        List<TagPlaylistMap> tagPlaylistMapList = new ArrayList<TagPlaylistMap>();

        for(int i=0;i<tagList.size();i++){
            HashTag hashTag = HashTag.builder().tagName(tagList.get(i).getTagName()).build();
            TagPlaylistMap newtagMap = TagPlaylistMap.builder().playListArticle(playListArticle).hashTag(hashTag).build();

            tagPlaylistMapList.add(newtagMap);
        }



        //영속성 문제
        playListArticle.getTagList().clear();

        playListArticle.getTagList().addAll(tagPlaylistMapList);

        List<Song> songList = playListForm.getSongList();
        List<SongPlaylistMap> songPlaylistMapList = new ArrayList<SongPlaylistMap>();
        for(int i=0;i<songList.size();i++){
            Song item = songList.get(i);
            Song newSong = Song.builder().text(item.getText()).href(item.getHref()).build();
            SongPlaylistMap songPlaylistMap = SongPlaylistMap.builder().playListArticle(playListArticle).song(newSong).build();

            songPlaylistMapList.add(songPlaylistMap);
        }

        playListArticle.getSongList().clear();

        playListArticle.getSongList().addAll(songPlaylistMapList);
        playListArticle.setAddress(playListForm.getAddress());
        playListArticle.setLatitude(playListForm.getLatitude());
        playListArticle.setLongitude(playListForm.getLongitude());



        playlistArticleRepository.save(playListArticle);


    }

    public void delete(Long id){
        playlistArticleRepository.deleteById(id);
    }
}
