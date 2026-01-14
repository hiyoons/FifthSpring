package com.example.FifthSpring.controller;

import com.example.FifthSpring.dto.PlayListDto;
import com.example.FifthSpring.dto.PlayListForm;
import com.example.FifthSpring.service.PlayListArticleService;
import com.example.FifthSpring.service.YoutubeService;
import io.swagger.v3.oas.annotations.Operation;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/playlist")
@RequiredArgsConstructor
public class PlaylistLocationApiController {
    private final PlayListArticleService playListArticleService;
    private final YoutubeService youtubeService;


    @GetMapping("/all")
    @Operation(summary="모든 게시글 조회",description = "모든 게시글을 10개씩 단위로 정보를 가져옵니다.")
    public ResponseEntity<PagedModel<PlayListDto>> getPlaylistMapList(@PageableDefault(size=10,sort="id",direction = Sort.Direction.DESC) Pageable pageable){

        return ResponseEntity.status(HttpStatus.OK).body(playListArticleService.mapFindAll(pageable));
    }

    @Operation(summary="게시글 id로 조회",description = "해당 id의 게시글 정보를 가져옵니다.")
    @GetMapping("/content")
    public ResponseEntity<PlayListDto> getPlayListContent(@RequestParam(name="id") long id){

        return ResponseEntity.status(HttpStatus.OK).body(playListArticleService.findById(id));
    }

    @Operation(summary="게시글 id로 수정페이지 조회",description = "해당 id의 게시글 정보를 가져옵니다.")
    @GetMapping("/edit")
    public ResponseEntity<PlayListDto> editPage(@RequestParam(name="id") long id){
       PlayListDto playListDto = playListArticleService.findById(id);

        return new ResponseEntity<>(playListDto, HttpStatus.OK);
    }



    @PostMapping("/add")
    public ResponseEntity<?> addLocationWithMusic(@RequestBody PlayListForm playListForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(playListForm);
        }


        youtubeService.save(0L,playListForm);

        return ResponseEntity.status(HttpStatus.OK).body(playListForm);
    }

    @PutMapping("/edit")
    public ResponseEntity<?> editPlaylist(@RequestBody PlayListForm playListForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(playListForm);
        }

        youtubeService.update(playListForm);

        return ResponseEntity.status(HttpStatus.OK).body(playListForm);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePlaylist(@RequestParam(name="id") long id){
        youtubeService.delete(id);

        return new ResponseEntity<>(null, HttpStatus.OK);
    }


}
