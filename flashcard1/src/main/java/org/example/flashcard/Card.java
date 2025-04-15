package org.example.flashcard;

public class Card {
    private final String question;
    private final String answer;
    private int correctCount = 0;
    private int incorrectCount = 0;
    private int totalAttempts = 0;
    private boolean lastAnswerCorrect = false;
    private long lastMistakeTimestamp = 0;

    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }

    public void markCorrect() {
        correctCount++;
        totalAttempts++;
        lastAnswerCorrect = true;
    }

    public void markIncorrect() {
        incorrectCount++;
        totalAttempts++;
        lastAnswerCorrect = false;
        lastMistakeTimestamp = System.currentTimeMillis(); // 🔥
    }

    public int getCorrectCount() { return correctCount; }
    public int getIncorrectCount() { return incorrectCount; }
    public int getTotalAttempts() { return totalAttempts; }
    public boolean wasLastAnswerCorrect() { return lastAnswerCorrect; }

    public long getLastMistakeTimestamp() { return lastMistakeTimestamp; }
    public void setLastMistakeTimestamp(long timestamp) {
        this.lastMistakeTimestamp = timestamp;
    }
    public void setCorrectCount(int count) { this.correctCount = count; }
    public void setIncorrectCount(int count) { this.incorrectCount = count; }
    public void setTotalAttempts(int attempts) { this.totalAttempts = attempts; }
    public void setLastAnswerCorrect(boolean val) { this.lastAnswerCorrect = val; }

}
