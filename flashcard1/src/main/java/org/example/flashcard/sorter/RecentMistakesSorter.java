package org.example.flashcard.sorter;

import java.util.Comparator;
import java.util.List;

import org.example.flashcard.Card;

public class RecentMistakesSorter implements CardSorter {
    @Override
    public List<Card> sort(List<Card> cards) {
        // lastMistakeTimestamp ихтэй буюу хамгийн сүүлд буруу хариулсан картуудыг эхэнд тавина
        cards.sort(Comparator.comparingLong(Card::getLastMistakeTimestamp).reversed());
        return cards;
    }
}
