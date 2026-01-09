package com.example.FifthSpring.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="hashtag")
public class HashTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="tag_name",nullable = false)
    private String tagName;

//    @OneToMany(mappedBy = "hashTag",orphanRemoval = true,cascade = CascadeType.ALL)
//    private List<TagArticleMap> hashTagArticlemap;

    @OneToMany(mappedBy = "hashTag",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<TagPlaylistMap> hashTagPlayListmap;
    @Builder
    public HashTag(String tagName){
        this.tagName=tagName;
    }
}
