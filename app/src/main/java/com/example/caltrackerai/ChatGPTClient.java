package com.example.caltrackerai;

import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class ChatGPTClient {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient httpClient;
    private final String apiKey;

    public ChatGPTClient(String apiKey) {
        this.apiKey = apiKey;
        this.httpClient = new OkHttpClient();
    }

    public String sendPromptAndGetResponse(String prompt) throws IOException {
        String url = "https://api.openai.com/v1/chat/completions";
        String json = "{\"prompt\": \"" + prompt + "\", \"temperature\": 0.8, \"max_tokens\": 4000}";
        String apiKeyHeader = "Bearer " + apiKey;

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", apiKeyHeader)
                .post(body)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Request failed with code: " + response.code());
            }

            Gson gson = new Gson();
            ChatGPTResponse chatGPTResponse = gson.fromJson(response.body().string(), ChatGPTResponse.class);
            return chatGPTResponse.choices.get(0).text;
        }
    }

    private static class ChatGPTResponse {
        List<Choice> choices;

        private static class Choice {
            String text;
        }
    }
}

