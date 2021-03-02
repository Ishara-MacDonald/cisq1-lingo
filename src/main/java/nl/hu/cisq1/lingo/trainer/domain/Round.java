package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;

public class Round {
    private static Long nextId = Long.valueOf(0);
    private Long roundId;
    private String wordToGuess;
    private int currentscore;
    private List<Feedback> allFeedback;
    private int maxAttempts;

    public Round(){
        setRoundId();
    }

    public Round(String wordToGuess, int currentscore, int maxAttempts){
        this.wordToGuess = wordToGuess;
        this.currentscore = currentscore;
        this.maxAttempts = maxAttempts;

        setRoundId();
    }

    //region setters and getters
    //region getters
    public List<Feedback> getAllFeedback() { return allFeedback; }
    public String getWordToGuess() { return wordToGuess; }
    public int getCurrentscore() { return currentscore; }
    public int getMaxAttempts() { return maxAttempts; }
    public static Long getNextId() { return nextId; }
    public Long getRoundId() { return roundId; }
    //endregion

    //region setters
    public void setAllFeedback(List<Feedback> allFeedback) { this.allFeedback = allFeedback;}
    public void setCurrentscore(int currentscore) { this.currentscore = currentscore; }
    public void setWordToGuess(String wordToGuess) { this.wordToGuess = wordToGuess; }
    public void setMaxAttempts(int maxAttempts) { this.maxAttempts = maxAttempts; }
    public void setRoundId() {
        this.roundId = nextId;
        nextId += nextId;
    }
    //endregion
    //endregion



}
