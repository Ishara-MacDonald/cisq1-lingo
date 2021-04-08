package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@NoArgsConstructor
public class Round {

    // region parameters

    private boolean roundActive = true;
    @Id
    @GeneratedValue
    private Long roundId;

    // region scores
    private int maxScore = 1000;
    private int scorePerLetter = 100;
    private int currentScore = 0;
    private int penalty = 10;
    //endregion

    // region attempts, word to Guess
    private int maxAttempts;
    private String wordToGuess;
    private boolean isPlayerEliminated = false;
    //endregion

    // region feedback and hints
    @OneToMany
    @JoinColumn
    @Cascade(CascadeType.ALL)
    private List<Feedback> allFeedback = new ArrayList<>();

    @OneToOne
    private Hint lastHint = new Hint();
    //endregion

    //endregion

    public Round(String wordToGuess, int maxAttempts){
        this.wordToGuess = wordToGuess;
        this.maxAttempts = maxAttempts;
        roundSetUp();
    }

    private void roundSetUp(){
        setHints();
    }

    //region setters
    public void setWordToGuess(String wordToGuess) {
        this.wordToGuess = wordToGuess;
    }

    public void setMaxAttempts(int maxAttempts) {
        this.maxAttempts = maxAttempts;
    }

    public void setHints(){
        lastHint = lastHint.firstHintOfRound(wordToGuess);
    }
    //endregion

    public void addAttempt(String attempt){
        if(roundActive){
            processAttempts(attempt);
        }
        if(allFeedback.size() >= maxAttempts){
            roundActive = false;
            isPlayerEliminated = true;
        }
    }

    public void processAttempts(String attempt){
        if(attempt.equals(wordToGuess))
            roundActive = false;

        Feedback feedback = new Feedback(wordToGuess, attempt, lastHint);

        lastHint = feedback.addAttempt( attempt );
        allFeedback.add( feedback );
    }
}
