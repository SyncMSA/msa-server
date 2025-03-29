package com.syncfittrackservice.application;

import com.syncfitcommonjpa.error.exception.CustomException;
import com.syncfitcommonjpa.error.exception.ErrorCode;
import com.syncfittrackservice.client.WishlistServiceClient;
import com.syncfittrackservice.dao.TrackRepository;
import com.syncfittrackservice.domain.Track;
import com.syncfittrackservice.dto.request.TrackCreateRequest;
import com.syncfittrackservice.dto.response.TrackInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class TrackService {

    private final WishlistServiceClient wishlistServiceClient;

//    private final MemberUtil memberUtil;
//    private final WishlistRepository wishlistRepository;
    private final TrackRepository trackRepository;

    public void createTrack(TrackCreateRequest request) {
        // 멤버 아이디를 가져오게 변환하기
//        final Member currentMember = memberUtil.getCurrentMember();
        final Long wishlistId = request.wishlistId();


//        validateWishlistMemberMismatch(wishlistId, currentMemberId);

        trackRepository.save(
                Track.createTrack(
                        request.artistName(), request.title(), request.albumName(), request.imageUrl(), wishlistId));
    }

    public void deleteTrack(Long trackId) {
//        final Member currentMember = memberUtil.getCurrentMember();
        final Track track = findTrackById(trackId);

//        validateTrackMemberMismatch(track, currentMember);

        trackRepository.deleteById(trackId);
    }

    @Transactional(readOnly = true)
    public List<TrackInfoResponse> findAllTrack(Long wishlistId) {
//        final Member currentMember = memberUtil.getCurrentMember();

//        validateWishlistMemberMismatch(wishlistId, currentMember);

        return trackRepository.findAllByWishlistId(wishlistId)
                .stream()
                .map(TrackInfoResponse::from)
                .toList();
    }

    /* 이 메서드는 사용할 일 없을듯?
    private Wishlist findWishlistById(Long wishlistId) {
        return wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new CustomException(ErrorCode.WISHLIST_NOT_FOUND));
    }
    */

    private void validateWishlistMemberMismatch(Long wishlistId, Long memberId) {
//        if (!wishlist.getMember().getId().equals(member.getId())) {
//            throw new CustomException(ErrorCode.WISHLIST_MEMBER_MISMATCH);
//        }
          if(wishlistServiceClient.validateOwnership(wishlistId, memberId) == false){
              throw new CustomException(ErrorCode.WISHLIST_MEMBER_MISMATCH);
          }
    }

    private Track findTrackById(Long trackId) {
        return trackRepository.findById(trackId)
                .orElseThrow(() -> new CustomException(ErrorCode.TRACK_NOT_FOUND));
    }

    private void validateTrackMemberMismatch(Track track, Long memberId) {
//        if (!track.getWishlist().getMember().getId().equals(memberId)) {
//            throw new CustomException(ErrorCode.TRACK_MEMBER_MISMATCH);
//        }
        if(wishlistServiceClient.validateOwnership(track.getWishlistId(),memberId) == false){
            throw new CustomException(ErrorCode.TRACK_MEMBER_MISMATCH);
        }
    }
}
