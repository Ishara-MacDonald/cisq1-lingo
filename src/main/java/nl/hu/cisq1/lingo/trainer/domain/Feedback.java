package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Data;

import java.util.*;

@Data
public class Feedback {
    private String attempt;
    private final String word;
    private final List<Mark> marks = new ArrayList<>();
    private Hint hint;

    public Feedback(String word, String attempt, Hint hint) {
        this.word = word.toUpperCase();
        this.attempt = attempt.toUpperCase();
        this.hint = hint;
        if(hint.getHints().isEmpty()){
            this.hint = hint.firstHintOfRound(word);
        }
    }

    public void processAttempt(){
        generateMarks();
        giveHint();
    }

    public Hint addAttempt(String attempt){
        this.attempt = attempt.toUpperCase();
        processAttempt();
        return hint;
    }

    public void giveHint(){
        hint.processFeedbackIntoHints(marks, hint, word);
    }

    public void generateMarks(){
        marks.clear();

        // attempt will only get marked if attempt is valid
        if(isWordValid()){

            // for every letter of attempt ...
            for(int counter = 0; counter < attempt.length(); counter ++){
                String letter = String.valueOf(attempt.charAt(counter));

                // check if letter is equal to string at same index of 'word'
                if(letter.equals(String.valueOf(word.charAt(counter)))){
                    marks.add(Mark.CORRECT);
                }
                // check if letter is present in 'word'
                else if(word.contains(letter)){
                    // check for doubles
                    if(counter == attempt.lastIndexOf(letter)){
                        marks.add(Mark.PRESENT);
                    }else{
                        marks.add(Mark.ABSENT);
                    }
                }
                // if letter not present in 'word', letter is absent marked absent
                else{
                    marks.add(Mark.ABSENT);
                }
            }
        // if attempt is not valid, attempt will be marked invalid.
        }else{
            for(int i = 0; i < word.length(); i++){
                marks.add(Mark.INVALID);
            }
        }
    }

    public boolean isWordValid(){
        return attempt.length() == word.length();
    }
}