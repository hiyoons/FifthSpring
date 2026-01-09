package com.example.FifthSpring.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class ArticleDto {
    private Long id;
    private Long memberId;
    private String name;
    private String email;
    private String title;
    private String description;
    private Date created;
    private Date updated;
    private List<String> tagList;

}
