package com.syncfitwishlistservice.dao;

import com.syncfitwishlistservice.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByMemberId(Long memberId);
}