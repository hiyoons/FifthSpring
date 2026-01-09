package com.example.FifthSpring.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ArticleForm
{
    private Long id;
    private String title;
    private String content;
    private List<String> tagList;


}
