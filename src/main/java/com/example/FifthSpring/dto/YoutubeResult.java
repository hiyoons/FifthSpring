package com.example.FifthSpring.dto;

import com.google.api.services.youtube.model.ResourceId;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class YoutubeResult {
    private String channelTitle;
    private String videoId;
    private String title;
    private ResourceId id;
}
