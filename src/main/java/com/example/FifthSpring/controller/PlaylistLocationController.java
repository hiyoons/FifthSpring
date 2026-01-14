package com.example.FifthSpring.controller;

import com.example.FifthSpring.dto.PlayListDto;
import com.example.FifthSpring.dto.PlayListForm;
import com.example.FifthSpring.service.PlayListArticleService;
import com.example.FifthSpring.service.YoutubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/playlist")
@RequiredArgsConstructor
public class PlaylistLocationController {

        private final PlayListArticleService playListArticleService;
        private final YoutubeService youtubeService;

        @Value("${kakao.api.key}")
         private String apiKey;

        @GetMapping("/all")
        public String getPlaylistMapList(@PageableDefault(size=10,sort="id",direction = Sort.Direction.DESC) Pageable pageable, Model model){

            Page<PlayListDto> page = playListArticleService.findAll(pageable);
            System.out.println("page");
            System.out.println(page);
            model.addAttribute("page",page);
            model.addAttribute("kakaoApiKey",apiKey);
            return "playlistarticle-all";

        }

        @GetMapping("/content")
        public String getPlayListContent(@RequestParam(name="id") long id,Model model){
            PlayListDto playListDto = playListArticleService.findById(id);
            System.out.println(playListDto);
            System.out.println(playListDto.getLatitude());
            System.out.println(playListDto.getLongitude());
            System.out.println(playListDto.getSongList());

            model.addAttribute("playList",playListDto);
            model.addAttribute("kakaoApiKey",apiKey);

            return "playlistarticle-content";
        }

        @GetMapping("/content/edit/{id}")
        public String editPage(@PathVariable(name="id") long id,@ModelAttribute("playlist") PlayListForm playListForm,Model model){
            PlayListDto playListDto = playListArticleService.findById(playListForm.getId());

            playListForm.setId(playListDto.getId());
            playListForm.setAddress(playListDto.getAddress());
            playListForm.setTagList(playListDto.getTagList());
            playListForm.setSongList(playListDto.getSongList());
            playListForm.setLongitude(playListDto.getLongitude());
            playListForm.setLatitude(playListDto.getLatitude());


            model.addAttribute("kakaoApiKey",apiKey);

            return "playlistarticle-edit";

        }



        @PostMapping("/add")
        public String addLocationWithMusic(@RequestBody PlayListForm playListForm, BindingResult bindingResult,Model model){
            if(bindingResult.hasErrors()){
                model.addAttribute("kakaoApiKey",apiKey);

                return "kakaomap";
            }


            youtubeService.save(0L,playListForm);
            model.addAttribute("kakaoApiKey",apiKey);

            return "playlistarticle-all";
        }

        @PutMapping("/edit")
        @ResponseBody
        public String editPlaylist(@RequestBody PlayListForm playListForm, BindingResult bindingResult,Model model){
            if(bindingResult.hasErrors()){
                model.addAttribute("kakaoApiKey",apiKey);

                return "/playlist/content/edit/"+playListForm.getId();
            }
            System.out.println("controller 에서 form 결과");
            System.out.println(playListForm);
            youtubeService.update(playListForm);
            model.addAttribute("kakaoApiKey",apiKey);

        return "/playlist/content?id="+playListForm.getId();

        }

        @DeleteMapping("/delete")
        @ResponseBody
        public String deletePlaylist(@RequestParam(name="id") long id,Model model){
            youtubeService.delete(id);
            model.addAttribute("kakaoApiKey",apiKey);

            return "/playlist/all";
//            return "redirect:/playlist/all";
            //@ResponseBody
        }


}


