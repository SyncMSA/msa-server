package com.syncfittrackservice.application;

import com.syncfitcommonjpa.error.exception.CustomException;
import com.syncfitcommonjpa.error.exception.ErrorCode;
import com.syncfitcommonjpa.util.MemberUtil;
import com.syncfittrackservice.client.WishlistServiceClient;
import com.syncfittrackservice.dao.TrackRepository;
import com.syncfittrackservice.domain.Track;
import com.syncfittrackservice.dto.request.TrackCreateRequest;
import com.syncfittrackservice.dto.response.TrackInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class TrackService {

    private final WishlistServiceClient wishlistServiceClient;

    private final MemberUtil memberUtil;
    private final TrackRepository trackRepository;

    public void createTrack(TrackCreateRequest request) {
        // 멤버 아이디를 반환
        final Long currentMemberId = memberUtil.getMemberId();
        final Long wishlistId = request.wishlistId();

        validateWishlistMemberMismatch(wishlistId, currentMemberId);

        trackRepository.save(
                Track.createTrack(
                        request.artistName(), request.title(), request.albumName(), request.imageUrl(), wishlistId));
    }

    public void deleteTrack(Long trackId) {
        final Long currentMemberId = memberUtil.getMemberId();
        final Track track = findTrackById(trackId);

        validateTrackMemberMismatch(track, currentMemberId);

        trackRepository.deleteById(trackId);
    }

    @Transactional(readOnly = true)
    public List<TrackInfoResponse> findAllTrack(Long wishlistId) {
        final Long currentMemberId = memberUtil.getMemberId();

        validateWishlistMemberMismatch(wishlistId, currentMemberId);

        return trackRepository.findAllByWishlistId(wishlistId)
                .stream()
                .map(TrackInfoResponse::from)
                .toList();
    }


    private void validateWishlistMemberMismatch(Long wishlistId, Long memberId) {
        if(!wishlistServiceClient.validateOwnership(wishlistId, memberId)){
            throw new CustomException(ErrorCode.WISHLIST_MEMBER_MISMATCH);
        }
    }

    private Track findTrackById(Long trackId) {
        return trackRepository.findById(trackId)
                .orElseThrow(() -> new CustomException(ErrorCode.TRACK_NOT_FOUND));
    }

    private void validateTrackMemberMismatch(Track track, Long memberId) {
        if(!wishlistServiceClient.validateOwnership(track.getWishlistId(), memberId)){
            throw new CustomException(ErrorCode.TRACK_MEMBER_MISMATCH);
        }
    }
}
