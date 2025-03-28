package com.syncfitoutterapiservice.domain.chat.api;

import com.syncfitoutterapiservice.domain.chat.application.ChatService;
import com.syncfitoutterapiservice.domain.chat.dto.response.GenreRecommendationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/music")
public class ChatController {

    private final ChatService chatService;

    @PostMapping("/recommendation")
    public GenreRecommendationResponse getRecommendation(@RequestBody Map<String, String> request) {
        String input = request.get("input");
        String genre = chatService.getRecommendGenre(input);

        return new GenreRecommendationResponse(genre);
    }


}
