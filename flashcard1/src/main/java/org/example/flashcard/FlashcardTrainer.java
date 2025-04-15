package org.example.flashcard;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class FlashcardTrainer {
    private final List<Card> cards;
    private final int repetitions;
    private final boolean invert;

    public FlashcardTrainer(List<Card> cards, int repetitions, boolean invert) {
        this.cards = cards;
        this.repetitions = repetitions;
        this.invert = invert;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        for (Card card : cards) {
            int correctCount = 0;

            while (correctCount < repetitions) {
                System.out.println();
                String prompt = invert ? card.getAnswer() : card.getQuestion();
                String expected = invert ? card.getQuestion() : card.getAnswer();

                System.out.print(" " + prompt + " = ");
                String input = scanner.nextLine().trim();

                if (input.equalsIgnoreCase("skip")) {
                    card.markIncorrect();
                    System.out.println(" Kart alga bolloo (skip).");
                    break;
                } else if (input.equalsIgnoreCase(expected)) {
                    card.markCorrect();
                    correctCount++;
                    System.out.println(" Zuv!");
                } else {
                    card.markIncorrect();
                    System.out.println(" buruu. zuv hariult: " + expected);
                }
            }
        }

        showAchievements();
        saveResultsToFile("results.txt");
    }

    private void showAchievements() {
        boolean allCorrect = cards.stream().allMatch(Card::wasLastAnswerCorrect);
        boolean repeat = cards.stream().anyMatch(c -> c.getTotalAttempts() > 5);
        boolean confident = cards.stream().anyMatch(c -> c.getCorrectCount() >= 3);

        System.out.println("\n Amjilttai:");
        if (allCorrect) System.out.println("CORRECT: Buh cartiig zuv hariulsan bn.");
        if (repeat) System.out.println("REPEAT: Neg kart 5-aas ih orson.");
        if (confident) System.out.println("CONFIDENT: Neg kart 3-aas deesh zuv hariulsan.");
        if (!allCorrect && !repeat && !confident) {
            System.out.println(" Amjilt. Dahin oroldooroi!");
        }
    }

    private void saveResultsToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Flashcard Quiz Results");
            writer.println("=======================");

            for (Card card : cards) {
                writer.println("Q: " + card.getQuestion());
                writer.println("Your answer(s):");
                writer.println("  Correct: " + card.getCorrectCount() + " | Incorrect: " + card.getIncorrectCount());
                writer.println("  Total Attempts: " + card.getTotalAttempts());
                writer.println("  LastAnswerCorrect: " + card.wasLastAnswerCorrect());
                writer.println("  LastMistakeTimestamp: " + card.getLastMistakeTimestamp()); // 🟢 зөв байрлал
                writer.println();
            }

            showAchievements();
            System.out.println(" Ur dun hadgalagdaaa: " + filename);

        } catch (IOException e) {
            System.out.println(" ur dun bichh uyd aldaa garlaa: " + e.getMessage());
        }
    }
}
