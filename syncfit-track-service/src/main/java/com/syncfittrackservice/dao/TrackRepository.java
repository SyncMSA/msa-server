package com.syncfittrackservice.dao;

import com.syncfittrackservice.domain.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Long> {
    List<Track> findAllByWishlistId(Long wishlistId);
}
