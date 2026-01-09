package com.example.FifthSpring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Setter
public class TagArticleMap{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="article_tag_id")
    private Long id;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="article_id")
    private Article article;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="hashtag_id")
    private HashTag hashTag;

    @CreatedDate
    @Column(name="created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public TagArticleMap(Article article,HashTag hashTag){
        this.article=article;
        this.hashTag=hashTag;
    }


}
