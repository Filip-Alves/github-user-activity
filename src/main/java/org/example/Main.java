package org.example;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String username;

        if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Quel est l'utilisateur ? ");
            username = scanner.nextLine().trim();
            scanner.close();
        } else if (args.length == 1) {
            username = args[0];
        } else {
            Utils.printUsage();
            System.exit(1);
            return;
        }

        if (!Utils.isValidUsername(username)) {
            System.err.println("Erreur: Le nom d'utilisateur '" + username + "' n'est pas valide");
            System.exit(1);
        }

        try {
            GitHubAPI api = new GitHubAPI();
            List<Map<String, String>> repositories = api.getRepositories(username);

            if (repositories.isEmpty()) {
                System.out.println("Aucun repository public trouvé pour l'utilisateur: " + username);
            } else {
                Utils.displayRepositories(username, repositories);
            }

        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des données: " + e.getMessage());
            System.exit(1);
        }
    }
}