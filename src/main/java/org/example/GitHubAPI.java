package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GitHubAPI {
    private final HttpClient client;
    private final Gson gson;

    public GitHubAPI() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.gson = new Gson();
    }

    public List<Map<String, String>> getRepositories(String username) throws IOException, InterruptedException {
        String url = Utils.GITHUB_API_BASE_URL + "/users/" + username + "/repos";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", Utils.USER_AGENT)
                .header("Accept", "application/vnd.github+json")
                .timeout(Duration.ofSeconds(30))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 404) {
            throw new RuntimeException("Utilisateur '" + username + "' introuvable sur GitHub");
        } else if (response.statusCode() == 403) {
            throw new RuntimeException("Limite de taux d'API GitHub atteinte. Réessayez plus tard.");
        } else if (response.statusCode() != 200) {
            throw new RuntimeException("Erreur API GitHub: " + response.statusCode() + " - " + response.body());
        }

        return parseRepositories(response.body());
    }

    private List<Map<String, String>> parseRepositories(String jsonResponse) {
        List<Map<String, String>> repositories = new ArrayList<>();

        JsonArray reposArray = gson.fromJson(jsonResponse, JsonArray.class);

        for (JsonElement repoElement : reposArray) {
            JsonObject repoObject = repoElement.getAsJsonObject();

            Map<String, String> repoData = new HashMap<>();
            repoData.put("name", getStringValue(repoObject, "name"));
            repoData.put("html_url", getStringValue(repoObject, "html_url"));
            repoData.put("languages_url", getStringValue(repoObject, "languages_url"));
            repoData.put("description", getStringValue(repoObject, "description"));
            repoData.put("language", getStringValue(repoObject, "language"));

            repositories.add(repoData);
        }

        return repositories;
    }

    private String getStringValue(JsonObject object, String key) {
        JsonElement element = object.get(key);
        return (element != null && !element.isJsonNull()) ? element.getAsString() : "N/A";
    }
}