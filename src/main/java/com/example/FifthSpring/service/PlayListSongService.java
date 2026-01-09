package com.example.FifthSpring.service;

import com.example.FifthSpring.model.PlayListArticle;
import com.example.FifthSpring.model.Song;
import com.example.FifthSpring.model.SongPlaylistMap;
import com.example.FifthSpring.repository.PlaylistSongRepository;
import com.example.FifthSpring.repository.SongRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class PlayListSongService {
    private final SongService songService;
    private final PlaylistSongRepository playlistSongRepository;
    private final SongRepository songRepository;
    public void saveSongList(PlayListArticle playListArticle, List<Song> songList){
        if(songList.size()==0) return;

        songList.stream()
                .map(
                    song->songService.findByHref(song.getHref())
                            .orElseGet(()->songService.save(song.getHref(),song.getText())))
                .forEach(song->mapSongAndArticle(playListArticle,song));
    }

    public void updateSongList(PlayListArticle playListArticle,List<SongPlaylistMap> songList){
        List<SongPlaylistMap> tar1 = playlistSongRepository.findAllByPlayListArticle(playListArticle);


        List<String> titleList= new ArrayList<>();
        for(int i=0;i< tar1.size();i++){
            String title=tar1.get(i).getSong().getText();
            titleList.add(title);
        }



        List<String> tar2 = new ArrayList<>();
        for(int i=0;i<songList.size();i++){
            String title = songList.get(i).getSong().getText();
            tar2.add(title);
        }

    }
    private void mapSongAndArticle(PlayListArticle playListArticle, Song song){
        List<SongPlaylistMap> songPlaylistMap = playlistSongRepository.findAllByPlayListArticle(playListArticle);
        PlayListArticle targetPlaylist = songPlaylistMap.getLast().getPlayListArticle();
        SongPlaylistMap updateSongPtMap = SongPlaylistMap.builder().playListArticle(targetPlaylist).song(song).build();
        playlistSongRepository.save(updateSongPtMap);
    }

    public List<SongPlaylistMap> findSongListByPlayList(PlayListArticle playListArticle){
        return playlistSongRepository.findAllByPlayListArticle(playListArticle);
    }
}

