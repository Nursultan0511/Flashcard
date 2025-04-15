package org.example.flashcard.sorter;

import java.util.List;

import org.example.flashcard.Card;

public interface CardSorter {
    List<Card> sort(List<Card> cards);
}
