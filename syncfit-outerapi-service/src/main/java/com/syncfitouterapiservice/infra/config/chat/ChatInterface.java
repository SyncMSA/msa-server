package com.syncfitouterapiservice.infra.config.chat;

import com.syncfitouterapiservice.dto.request.ChatRequest;
import com.syncfitouterapiservice.dto.response.ChatResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/v1beta/models/")
public interface ChatInterface {

    @PostExchange("{model}:generateContent")
    ChatResponse getCompletion(
            @PathVariable String model,
            @RequestBody ChatRequest request
    );
}
