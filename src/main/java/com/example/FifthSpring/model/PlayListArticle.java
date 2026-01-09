package com.example.FifthSpring.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Builder
public class PlayListArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    private String address; //지역

    private double latitude;
    private double longitude;


    @CreatedDate
    private Date created;
    @LastModifiedDate
    private Date updated;

    @OneToMany(mappedBy = "playListArticle",orphanRemoval = true,cascade = CascadeType.ALL)
    @Setter

    private List<TagPlaylistMap> tagList;

    @OneToMany(mappedBy = "playListArticle",orphanRemoval = true,cascade = CascadeType.ALL)
    @Setter

    private List<SongPlaylistMap> songList;
}
