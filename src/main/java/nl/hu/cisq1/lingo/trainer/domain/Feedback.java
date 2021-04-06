package nl.hu.cisq1.lingo.trainer.domain;

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
        if(isWordValid()){
            String[] splitGuess = attempt.split("");
            int counter = 0;

            for(String letter: splitGuess){
                if(checkPresent(letter)){
                    if(checkCorrect(letter, counter))
                        marks.add(Mark.CORRECT);
                    else
                        marks.add(Mark.PRESENT);
                }else{
                    marks.add(Mark.ABSENT);
                }
                counter++;
            }
        }else{
            for(int i = 0; i < word.length(); i++){
                marks.add(Mark.INVALID);
            }
        }
    }

    public boolean checkCorrect(String letter, int counter){
        List<String> splitWord = new ArrayList<>(Arrays.asList(word.split("")));
        return letter.equals(splitWord.get(counter));
    }

    public boolean checkPresent(String letter){
        return word.contains(letter);
    }

    public boolean isWordGuessed() {
        for(Mark mark : marks){
            if(! mark.equals(Mark.CORRECT))
                return false;
        }
        return true;
    }

    public boolean isWordValid(){
        return attempt.length() == word.length();
    }

    @Override
    public String toString() {
        return String.format("Feedback { marks: %s\nhint: %s}", marks, lastHint);
    }
}