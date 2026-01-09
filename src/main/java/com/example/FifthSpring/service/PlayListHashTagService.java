package com.example.FifthSpring.service;

import com.example.FifthSpring.model.*;
import com.example.FifthSpring.repository.HashtagRepository;
import com.example.FifthSpring.repository.PlaylistHashTagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class PlayListHashTagService {
    private final HashTagService hashtagService;
    private final PlaylistHashTagRepository playlistHashTagRepository;
    private final HashtagRepository hashtagRepository;
    public void saveHashtag(PlayListArticle playListArticle, List<HashTag> hashTagList) {

        if(hashTagList.size() == 0) return;

        hashTagList.stream()
                .map(hashtag ->
                        hashtagService.findByTagName(hashtag.getTagName())
                                .orElseGet(() -> hashtagService.save(hashtag.getTagName())))
                .forEach(hashtag -> mapTagAndPlaylist(playListArticle, hashtag));
    }




    private void mapTagAndPlaylist(PlayListArticle playListArticle, HashTag hashtag) {
        List<TagPlaylistMap> tagPlaylistMapList = playlistHashTagRepository.findAllByPlayListArticle(playListArticle);

        PlayListArticle tagetPlaylist = tagPlaylistMapList.getLast().getPlayListArticle();
        TagPlaylistMap updateTagPlaylistMap = TagPlaylistMap.builder().playListArticle(tagetPlaylist).hashTag(hashtag).build();

        playlistHashTagRepository.save(updateTagPlaylistMap);
    }

    public List<TagPlaylistMap> findHashtagListByPlaylistArticle(PlayListArticle playListArticle) {

        return playlistHashTagRepository.findAllByPlayListArticle(playListArticle);
    }
}
