package com.syncfitwishlistservice.domain;


import com.syncfitcommon.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Wishlist extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @ElementCollection
    @CollectionTable(name = "wishlist_tracks", joinColumns = @JoinColumn(name = "wishlist_id"))
    @Column(name = "track_id")
    private List<Long> trackIds = new ArrayList<>();


    @Builder(access = AccessLevel.PRIVATE)
    private Wishlist(Long memberId, String title, String imageUrl) {
        this.memberId = memberId;
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public static Wishlist createWishlist(Long memberId, String title, String imageUrl) {
        return Wishlist.builder().memberId(memberId).title(title).imageUrl(imageUrl).build();
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}