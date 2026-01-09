package com.example.FifthSpring.dto;


import com.example.FifthSpring.model.HashTag;
import com.example.FifthSpring.model.Song;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class PlayListDto {
    private Long id;
//    private Long memberId;
//    private String name;
//    private String email;

    private String address; //지역
    private double latitude;
    private double longitude;

    private Date created;
    private Date updated;
    private List<HashTag> tagList;
    private List<Song> songList;
}
