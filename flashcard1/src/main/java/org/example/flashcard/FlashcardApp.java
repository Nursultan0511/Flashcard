package org.example.flashcard;

import java.util.Arrays;
import java.util.List;

import org.example.flashcard.sorter.CardSorter;
import org.example.flashcard.sorter.RandomSorter;
import org.example.flashcard.sorter.RecentMistakesSorter;
import org.example.flashcard.sorter.WorstFirstSorter;

public class FlashcardApp {
    private final String[] args;

    public FlashcardApp(String[] args) {
        this.args = args;
    }

    public void run() {
        if (hasOption("--help") || args.length == 0) {
            printHelp();
            return;
        }

        String filePath = args[0];

        boolean invert = hasOption("--invertCards");
        int repetitions = getIntOption("--repetitions", 1);
        String order = getOptionValue("--order", "random");

        CardSorter sorter = switch (order) {
            case "worst-first" -> new WorstFirstSorter();
            case "recent-mistakes-first" -> new RecentMistakesSorter();
            default -> new RandomSorter();
        };
        List<Card> cards = CardLoader.loadFromFileWithStats(filePath, "results.txt");
        List<Card> sortedCards = sorter.sort(cards);
        FlashcardTrainer trainer = new FlashcardTrainer(sortedCards, repetitions, invert);
        trainer.start();
    }

    private boolean hasOption(String flag) {
        return Arrays.asList(args).contains(flag);
    }

    private String getOptionValue(String flag, String defaultVal) {
        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].equals(flag)) return args[i + 1];
        }
        return defaultVal;
    }

    private int getIntOption(String flag, int defaultVal) {
        try {
            return Integer.parseInt(getOptionValue(flag, String.valueOf(defaultVal)));
        } catch (NumberFormatException e) {
            return defaultVal;
        }
    }

    private void printHelp() {
        System.out.println("""
        Ashiglah zaavar:
        flashcard <cards-file> [options]
        
        Options:
          --help                   Tuslamjiin medeelel
          --order <order>          Zohion bayguulaltiin turul(default: random)
                                   Songolt: random, worst-first, recent-mistakes-first
          --repetitions <num>      Neg cartad shaardlagatay zuv hariultiin too(default: 1)
          --invertCards            Kartiin asuult bolon haruiltiig solih(default: false)
        """);
    }
}
 