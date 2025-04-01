package com.syncfitexternalapiservice.api;

import com.syncfitexternalapiservice.application.ChatService;
import com.syncfitexternalapiservice.application.SpotifyService;
import com.syncfitexternalapiservice.application.YouTubeService;
import com.syncfitexternalapiservice.dto.response.GenreRecommendationResponse;
import com.syncfitexternalapiservice.dto.response.SpotifySearchResponse;
import com.syncfitexternalapiservice.dto.response.YouTubeVideoIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ExternalApiController {

    private final ChatService chatService;
    private final SpotifyService spotifyService;
    private final YouTubeService youTubeService;

    @PostMapping("/recommendation")
    public GenreRecommendationResponse getRecommendation(@RequestBody Map<String, String> request) {
        System.out.println("request = " + request);
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
