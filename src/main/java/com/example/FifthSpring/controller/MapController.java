package com.example.FifthSpring.controller;


import com.example.FifthSpring.dto.PlayListForm;

import com.example.FifthSpring.service.PlayListArticleService;
import com.example.FifthSpring.service.YoutubeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/map")
@RequiredArgsConstructor
public class MapController {
    private final YoutubeService youtubeService;

    @GetMapping("/kakaomap")
    public String getKakaoMap(Model model){

        return "kakaomap";
    }


    @PostMapping("/location/music/add")
    @ResponseBody
    public String addLocationWithMusic(@RequestBody PlayListForm playListForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "kakaomap";
        }
        log.info("playListForm 정보: "+playListForm);

        youtubeService.save(0L,playListForm);

        return "redirect:/playlist/all";
    }


}
