package com.syncfitouterapiservice.api;

import com.syncfitouterapiservice.application.ChatService;
import com.syncfitouterapiservice.application.SpotifyService;
import com.syncfitouterapiservice.application.YouTubeService;
import com.syncfitouterapiservice.dto.response.GenreRecommendationResponse;
import com.syncfitouterapiservice.dto.response.SpotifySearchResponse;
import com.syncfitouterapiservice.dto.response.YouTubeVideoIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OuterApiController {

    private final ChatService chatService;
    private final SpotifyService spotifyService;
    private final YouTubeService youTubeService;

    @PostMapping("/recommendation")
    public GenreRecommendationResponse getRecommendation(@RequestBody Map<String, String> request) {
        String input = request.get("input");
        String genre = chatService.getRecommendGenre(input);

        return new GenreRecommendationResponse(genre);
    }

    @GetMapping("/search")
    public List<SpotifySearchResponse> searchSpotify(@RequestParam List<String> genres) {
        return spotifyService.searchByGenre(genres);
    }

    @GetMapping("/youtube")
    public YouTubeVideoIdResponse getYouTubeLink(@RequestParam String query) throws IOException {
        return youTubeService.getYouTubeLink(query);
    }
}
