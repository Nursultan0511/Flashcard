package org.example.flashcard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardLoader {

    public static List<Card> loadFromFile(String cardFilePath) {
        List<Card> cards = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(cardFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    cards.add(new Card(parts[0].trim(), parts[1].trim()));
                }
            }
        } catch (IOException e) {
            System.out.println("Карт ачаалж чадсангүй: " + e.getMessage());
        }
        return cards;
    }

    public static List<Card> loadFromFileWithStats(String cardFile, String resultsFile) {
        List<Card> cards = loadFromFile(cardFile);
        Map<String, Card> cardMap = new HashMap<>();
        for (Card c : cards) cardMap.put(c.getQuestion(), c);

        try (BufferedReader br = new BufferedReader(new FileReader(resultsFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Q: ")) {
                    String question = line.substring(3).trim();
                    Card card = cardMap.get(question);
                    if (card == null) continue;

                    br.readLine(); // 🟡 Skipping: "Your answer(s):"
                    String correctLine = br.readLine();
                    String totalLine = br.readLine();
                    String lastCorrectLine = br.readLine();
                    String lastMistakeLine = br.readLine(); // ✅ яг эндээс timestamp авна

                    int correct = extractNumber(correctLine, "Correct: ");
                    int incorrect = extractNumber(correctLine, "Incorrect: ");
                    int total = extractNumber(totalLine, "Total Attempts: ");
                    boolean lastCorrect = extractBoolean(lastCorrectLine, "LastAnswerCorrect: ");
                    long lastMistake = extractLong(lastMistakeLine, "LastMistakeTimestamp: ");

                    card.setCorrectCount(correct);
                    card.setIncorrectCount(incorrect);
                    card.setTotalAttempts(total);
                    card.setLastAnswerCorrect(lastCorrect);
                    card.setLastMistakeTimestamp(lastMistake);
                }
            }
        } catch (IOException e) {
            System.out.println("Stats ачаалж чадсангүй: " + e.getMessage());
        }

        return cards;
    }

    private static int extractNumber(String line, String key) {
        try {
            int idx = line.indexOf(key);
            if (idx == -1) return 0;
            String part = line.substring(idx + key.length()).trim().split("[ |]")[0];
            return Integer.parseInt(part);
        } catch (Exception e) {
            System.out.println("Parse алдаа (int): " + e.getMessage());
            return 0;
        }
    }

    private static boolean extractBoolean(String line, String key) {
        try {
            return Boolean.parseBoolean(line.substring(line.indexOf(key) + key.length()).trim());
        } catch (Exception e) {
            System.out.println("Parse алдаа (boolean): " + e.getMessage());
            return false;
        }
    }

    private static long extractLong(String line, String key) {
        try {
            int idx = line.indexOf(key);
            if (idx == -1) return 0;
            return Long.parseLong(line.substring(idx + key.length()).trim());
        } catch (Exception e) {
            System.out.println("Parse алдаа (long): " + e.getMessage());
            return 0;
        }
    }
}
