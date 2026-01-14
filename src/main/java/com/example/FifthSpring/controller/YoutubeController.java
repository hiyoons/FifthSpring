package com.example.FifthSpring.controller;

import com.example.FifthSpring.dto.YoutubeResult;
import com.example.FifthSpring.service.YoutubeService;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.SearchResultSnippet;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class YoutubeController {

    private final YoutubeService youtubeService;



    @GetMapping("/youtube/search")
    public String searchYoutube(){
        return "youtube-search";
    }



    @GetMapping("/api/youtube/search")
    public String search(@RequestParam(value="keyword") String keyword, Model model) throws GeneralSecurityException, IOException {
        List<SearchResult> results= youtubeService.searchTopVideos(keyword);
        List <ResourceId> resourceList= results.stream().map(SearchResult::getId).collect(Collectors.toList());
        List<SearchResultSnippet> resultSnippets = results.stream().map(SearchResult::getSnippet).collect(Collectors.toList());
        List<YoutubeResult> searchResult = results.stream().map(
                res-> {
                    ResourceId id = res.getId();
                    String videoId = res.getId().getVideoId();
                    System.out.println(videoId);
                    String title = res.getSnippet().getTitle();
                    String channelTitle =res.getSnippet().getChannelTitle();

                    YoutubeResult youtubeResult = YoutubeResult.builder().id(id).channelTitle(channelTitle).videoId(videoId).title(title).build();
                    return  youtubeResult;
                }
                ).collect(Collectors.toList());



        model.addAttribute("results",searchResult);

        return "youtube-search-list";


    }
}