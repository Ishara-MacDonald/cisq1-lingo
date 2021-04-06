package nl.hu.cisq1.lingo.trainer.domain;

import javax.persistence.GeneratedValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Feedback {
    private String attempt;
    private final String word;
    private List<Mark> marks = new ArrayList<>();
    private Hint lastHint = new Hint();

    public Feedback(String word, String attempt) {
        this.word = word.toUpperCase();
        this.attempt = attempt.toUpperCase();
        if(lastHint.getHints().isEmpty()){
            initiateHint();
        }
    }

    private void initiateHint(){
        List<String> hints = new ArrayList<>();
        for(int x = 0; x < word.length(); x++){
            if(x == 0){
                hints.add(String.valueOf(word.charAt(x)));
            }else{
                hints.add(".");
            }
        }

        lastHint = new Hint(hints);
    }

    public Hint getLastHint(){
        return lastHint;
    }
    public List<Mark> getMarks(){
        return marks;
    }

    public void setMarks(List<Mark> marks){ this.marks = marks; }
    public void setLastHint(Hint lastHint) { this.lastHint = lastHint; }

    public void processAttempt(){
        generateMarks();
        giveHint();
    }

    public void addAttempt(String attempt){
        this.attempt = attempt.toUpperCase();
        processAttempt();
    }

    public void giveHint(){
        lastHint.processFeedbackIntoHints(marks, lastHint, word);
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

    @Override
    @GeneratedValue
    public String toString() {
        return String.format("Feedback { marks: %s\nhint: %s}", marks, lastHint);
    }
}