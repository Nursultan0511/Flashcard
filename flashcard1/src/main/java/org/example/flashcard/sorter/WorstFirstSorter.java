
package org.example.flashcard.sorter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.example.flashcard.Card;

public class WorstFirstSorter implements CardSorter {
    @Override
    public List<Card> sort(List<Card> cards) {
        List<Card> sorted = new ArrayList<>(cards);
        sorted.sort(Comparator
                .comparingInt(Card::getIncorrectCount).reversed()
                .thenComparing(card -> !card.wasLastAnswerCorrect()));
        return sorted;
    }
}
