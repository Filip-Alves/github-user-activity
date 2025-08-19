package org.example;

import java.util.List;
import java.util.Map;

public class Utils {
    public static final String GITHUB_API_BASE_URL = "https://api.github.com";
    public static final String USER_AGENT = "GitHub-Repo-Viewer/1.0";

    public static void printUsage() {
        System.out.println("Usage: java org.example.Main [username]");
        System.out.println("Exemple: java org.example.Main octocat");
        System.out.println("Si aucun username n'est fourni, il sera demandé interactivement.");
    }

    public static boolean isValidUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return false;
        }

        // GitHub username rules: 1-39 caractères, alphanumériques et hyphens
        // Ne peut pas commencer ou finir par un hyphen
        return username.matches("^[a-zA-Z0-9]([a-zA-Z0-9-]*[a-zA-Z0-9])?$") &&
                username.length() <= 39;
    }

    public static void displayRepositories(String username, List<Map<String, String>> repositories) {
        System.out.println("==================================================");
        System.out.println("📚 Repositories de " + username + " (" + repositories.size() + " trouvés)");
        System.out.println("==================================================");

        for (int i = 0; i < repositories.size(); i++) {
            Map<String, String> repo = repositories.get(i);

            System.out.println("\n" + (i + 1) + ". " + repo.get("name"));
            System.out.println("   🔗 URL: " + repo.get("html_url"));
            System.out.println("   🔧 Languages API: " + repo.get("languages_url"));

            if (!"N/A".equals(repo.get("language"))) {
                System.out.println("   💻 Langage principal: " + repo.get("language"));
            }

            if (!"N/A".equals(repo.get("description"))) {
                System.out.println("   📝 Description: " + repo.get("description"));
            }
        }

        System.out.println("\n==================================================");
    }
}