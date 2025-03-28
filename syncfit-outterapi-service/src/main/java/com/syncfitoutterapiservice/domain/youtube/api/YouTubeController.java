package com.syncfitoutterapiservice.domain.youtube.api;

import com.syncfitoutterapiservice.domain.youtube.application.YouTubeService;
import com.syncfitoutterapiservice.domain.youtube.dto.response.YouTubeVideoIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
public class YouTubeController {

    private final YouTubeService youTubeService;

    @GetMapping("/youtube")
    public YouTubeVideoIdResponse getYouTubeLink(@RequestParam String query) throws IOException {
        return youTubeService.getYouTubeLink(query);
    }
}