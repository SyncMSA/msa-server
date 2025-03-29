package com.syncfittrackservice.domain;

import com.syncfitcommonjpa.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Track extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id")
    private Long id;

    private String artistName;

    private String title;

    private String albumName;

    private String imageUrl;

    private Long wishlistId;

    @Builder(access = AccessLevel.PRIVATE)
    public Track(String artistName, String title, String albumName, String imageUrl, Long wishlistId) {
        this.artistName = artistName;
        this.title = title;
        this.albumName = albumName;
        this.imageUrl = imageUrl;
        this.wishlistId = wishlistId;
    }

    public static Track createTrack(
            String artistName, String title, String albumName, String imageUrl, Long wishlistId) {
        return Track.builder()
                .artistName(artistName)
                .title(title)
                .albumName(albumName)
                .imageUrl(imageUrl)
                .wishlistId(wishlistId)
                .build();
    }
}
