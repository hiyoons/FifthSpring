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
public class SongPlaylistMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="playlist_song_id")
    @JsonIgnore
    private Long id;



    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="play_list_article_id")
    private PlayListArticle playListArticle;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="song_id")
    @JsonIgnore
    private Song song;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    public SongPlaylistMap(PlayListArticle playListArticle,Song song){

        this.playListArticle = playListArticle;
        this.song=song;
    }


}
