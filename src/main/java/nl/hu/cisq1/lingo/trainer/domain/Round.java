package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

public class Round {

    // region parameters

    private boolean roundActive = true;
    private static Long nextId = 0L;
    private Long roundId;

    // region scores
    private int maxScore = 1000;
    private int scorePerLetter = 100;
    private int currentScore = 0;
    private int penalty = 10;
    //endregion

    // region attempts, word to Guess and Feedback
    private List<String> attempts = new ArrayList<>();
    private int maxAttempts = 1;

    private String wordToGuess = "";

    private List<Feedback> allFeedback = new ArrayList<>();
    //endregion

    //endregion

    public Round(){
        roundSetUp();
    }

    public Round(String wordToGuess, int maxAttempts){
        this.wordToGuess = wordToGuess;
        this.maxAttempts = maxAttempts;
        roundSetUp();
    }

    public Round(String wordToGuess, int scorePerLetter, int maxAttempts){
        this.wordToGuess = wordToGuess;
        this.scorePerLetter = scorePerLetter;
        this.maxAttempts = maxAttempts;
        roundSetUp();
    }

    private void roundSetUp(){
        setRoundId();
        calculateMaxScore();
        calculatePentalty();
    }

    //region setters and getters
    //region setters
    public static void setNextId(Long id){ nextId = id; }
    public static void setNextId(int id){ nextId = (long) id; }

    public void setAllFeedback(List<Feedback> allFeedback) { this.allFeedback = allFeedback;}
    public void setCurrentScore(int currentScore) { this.currentScore = currentScore; }

    public void setWordToGuess(String wordToGuess) {
        this.wordToGuess = wordToGuess;
        calculateMaxScore();
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
        calculatePentalty();
    }

    public void setRoundId() {
        this.roundId = nextId;
        nextId += 1L;
    }
    //endregion
    //region getters
    public List<Feedback> getAllFeedback() { return allFeedback; }
    public String getWordToGuess() { return wordToGuess; }
    public List<String> getAttempts() { return attempts; }
    public boolean isRoundActive() { return roundActive; }
    public int getCurrentScore() { return currentScore; }
    public int getMaxAttempts() { return maxAttempts; }
    public static Long getNextId() { return nextId; }
    public int getMaxScore() { return maxScore; }
    public Long getRoundId() { return roundId; }

    //endregion
    //endregion

    public void addAttempt(String attempt){
        if(allFeedback.size() < maxAttempts){
            attempts.add(attempt);
            processAttempts(attempt);
        }
    }

    public void processAttempts(String attempt){
        if(attempt.equals(wordToGuess)){
            roundActive = false;
        }else{
            calculateScore();
        }
        Feedback feedback = new Feedback(wordToGuess, attempt);
        feedback.processAttempt();
        allFeedback.add(feedback);
    }

    public void calculateScore(){
        currentScore -= penalty;
    }

    private void calculateMaxScore(){
        maxScore = 0;
        for(int index = 0; index < wordToGuess.length(); index++){
            maxScore += scorePerLetter;
        }
        currentScore = maxScore;
    }

    private void calculatePentalty(){
        penalty = maxScore / maxAttempts;
        System.out.println(penalty);
    }
}
