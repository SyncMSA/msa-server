package com.syncfitexternalapiservice.application;

import com.syncfitexternalapiservice.dto.request.ChatRequest;
import com.syncfitexternalapiservice.dto.response.ChatResponse;
import com.syncfitexternalapiservice.infra.config.chat.ChatInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    //private final MemberUtil memberUtil;
    private final ChatInterface chatInterface;
    private final PromptGenerator promptGenerator;

    private static final String MODEL = "gemini-2.0-flash-lite";

    private ChatResponse getCompletion(ChatRequest request) {
        return chatInterface.getCompletion(MODEL, request);
    }

    private String getCompletion(String text) {
        ChatResponse response = getCompletion(new ChatRequest(text));

        return response.getCandidates()
                .stream()
                .findFirst().flatMap(candidate -> candidate.getContent().getParts()
                        .stream()
                        .findFirst()
                        .map(ChatResponse.TextPart::getText))
                .orElse(null);
    }

    public String getRecommendGenre(String input) {
        //memberUtil.getCurrentMember();
        String message = promptGenerator.generatePrompt(input);
        return getCompletion(message);
    }
}
