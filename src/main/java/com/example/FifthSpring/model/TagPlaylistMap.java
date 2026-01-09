package com.example.FifthSpring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
public class TagPlaylistMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="playlist_tag_id")
    @JsonIgnore
    private Long id;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="play_list_article_id")
    private PlayListArticle playListArticle;



    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="hashtag_id")
    @JsonIgnore
    private HashTag hashTag;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public TagPlaylistMap(PlayListArticle playListArticle,HashTag hashTag){
        this.playListArticle=playListArticle;
        this.hashTag=hashTag;
    }

}
