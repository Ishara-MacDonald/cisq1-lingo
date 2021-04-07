package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Data;

import java.util.*;

@Data
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

    // region attempts, word to Guess
    private List<String> attempts = new ArrayList<>();
    private int maxAttempts;

    private String wordToGuess;

    private boolean isPlayerEliminated = false;
    //endregion

    // region feedback and hints
    private List<Feedback> allFeedback = new ArrayList<>();
    private List<Hint> hints = new ArrayList<>();
    private Hint lastHint = new Hint();
    //endregion

    //endregion

    public Round(String wordToGuess, int maxAttempts){
        this.wordToGuess = wordToGuess;
        this.maxAttempts = maxAttempts;
        roundSetUp();
    }

    private void roundSetUp(){
        setRoundId();
        calculateMaxScore();
        calculatePentalty();
        setHints();
    }

    //region setters
    public static void setNextId(int id){ nextId = (long) id; }


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

    public void setHints(){
        lastHint = lastHint.firstHintOfRound(wordToGuess);
        hints.add(lastHint);
    }
    //endregion

    public void addAttempt(String attempt){
        if(roundActive){
            if(allFeedback.size() < maxAttempts){
                attempts.add(attempt);
                processAttempts(attempt);
            }else{
                roundActive = false;
                isPlayerEliminated = true;
            }
        }
    }

    public void processAttempts(String attempt){
        if(attempt.equals(wordToGuess))
            roundActive = false;
        else
            calculateScore();

        Feedback feedback = new Feedback(wordToGuess, attempt, lastHint);

        lastHint = feedback.addAttempt( attempt );
        hints.add(lastHint);
        allFeedback.add( feedback );
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
    }
}
