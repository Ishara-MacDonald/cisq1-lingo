package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Feedback {
    private String attempt;
    private final String word;
    private List<Mark> marks = new ArrayList<>();
    private final Hint hint = new Hint();

    public Feedback(String word, String attempt) {
        this.word = word.toUpperCase();
        this.attempt = attempt.toUpperCase();
    }

    public Hint getHint(){
        return hint;
    }
    public List<Mark> getMarks(){
        return marks;
    }

    public void setMarks(List<Mark> marks){ this.marks = marks; }

    public void processAttempt(){
        generateMarks();
        giveHint();
    }

    public void addAttempt(String attempt){
        this.attempt = attempt.toUpperCase();
        processAttempt();
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

    public void giveHint(){
        hint.processFeedbackIntoHints(marks, hint, word);
    }
}