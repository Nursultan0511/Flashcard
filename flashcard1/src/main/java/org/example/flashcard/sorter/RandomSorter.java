package org.example.flashcard.sorter;

import java.util.Collections;
import java.util.List;

import org.example.flashcard.Card;



public class RandomSorter implements CardSorter {
    @Override
    public List<Card> sort(List<Card> cards) {
        Collections.shuffle(cards);
        return cards;
    }
}
