package com.example.FifthSpring.dto;


import com.example.FifthSpring.model.HashTag;
import com.example.FifthSpring.model.Song;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PlayListForm {
    private Long id;

    private String address;

    private double latitude;
    private double longitude;
    private List<Song> songList;
    private List<HashTag> tagList;


}
