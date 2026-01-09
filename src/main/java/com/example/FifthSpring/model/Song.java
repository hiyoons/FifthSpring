package com.example.FifthSpring.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="song")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="text",nullable = false)
    private String text;

    @Column(name="href",nullable = false)
    private String href;


    @OneToMany(mappedBy = "song",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<SongPlaylistMap> songPlaylistMap;


    @Builder
    public Song(String text,String href){
        this.text=text;
        this.href=href;
    }
}
