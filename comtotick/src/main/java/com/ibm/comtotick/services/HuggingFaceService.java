package com.ibm.comtotick.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

@Service
public class HuggingFaceService {

    @Value("${huggingface.api.token}")
    private String apiToken;

    @Value("${huggingface.api.url}")
    private String apiUrl;

    private final RestClient restClient = RestClient.create();

    private String query(String prompt) {
        try {
            var body = Map.of(
                "model", "meta-llama/Llama-3.1-8B-Instruct:novita",
                "messages", List.of(Map.of("role", "user", "content", prompt)),
                "max_tokens", 150
            );

            Map<String, Object> response = restClient.post()
                    .uri(apiUrl)
                    .header("Authorization", "Bearer " + apiToken)
                    .header("Content-Type", "application/json")
                    .body(body)
                    .retrieve()
                    .body(Map.class);

            if (response != null && response.containsKey("choices")) {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
                if (!choices.isEmpty()) {
                    Map<String, Object> choice = choices.get(0);
                    Map<String, Object> message = (Map<String, Object>) choice.get("message");
                    Object content = message.get("content");
                    return content != null ? content.toString().trim().toLowerCase() : "";
                }
            }
        } catch (HttpServerErrorException e) {
            System.out.println("HuggingFace model loading or unavailable: " + e.getMessage());
        } catch (HttpClientErrorException e) {
            System.out.println("HuggingFace API error: " + e.getMessage());
        }
        return "";
    }

    public TicketDraft analyze(String commentText) {

        String prompt = """
            Analyze this user comment and answer the following questions:
            1. Should this become a support ticket? (yes/no)
            2. Short ticket title (max 8 words, start with capital letter)
            3. Category (one word from following options): bug, feature, billing, account, or other
            4. Priority (one word from following options): low, medium, or high
            5. One sentence summary (starts with capital letter):

            Comment: %s

            Answer in this exact format:
            ticket: yes
            title: ...
            category: ...
            priority: ...
            summary: ...
            """.formatted(commentText);

        String result = query(prompt);

        if (result.isBlank() || !result.contains("ticket: yes"))
            return null;

        return new TicketDraft(
            extractField(result, "title",    "Untitled Ticket"),
            extractField(result, "category", "other"),
            extractField(result, "priority", "medium"),
            extractField(result, "summary",  commentText)
        );
    }

    private String extractField(String text, String field, String fallback) {
        for (String line : text.split("\n")) {
            if (line.startsWith(field + ":")) {
                String value = line.substring((field + ":").length()).trim();
                return value.isBlank() ? fallback : value;
            }
        }
        return fallback;
    }

    public record TicketDraft(String title, String category, String priority, String summary) {}
}