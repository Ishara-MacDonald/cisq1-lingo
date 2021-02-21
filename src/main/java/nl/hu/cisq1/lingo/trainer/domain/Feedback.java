package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Feedback {
    private String attempt;
    private String word;
    private List<Mark> marks;
    private List<Hint> hints;

    public Feedback(String attempt, String word) {
        this.attempt = attempt.toUpperCase();
        this.word = word.toUpperCase();
        hints = new ArrayList<>();
        marks = new ArrayList<>();
    }

    public List<Hint> getHints(){
        return hints;
    }

    public void setMarks(List<Mark> marks){
        this.marks = marks;
    }

    public List<Mark> getMarks(){
        return marks;
    }

    public void generateMarks(){
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

    public Hint giveHint(){
        Hint hint = new Hint();
        hint.setHints(hint.processFeedbackIntoHints(marks, word));
        hints.add(hint);
        return hint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(marks, feedback.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marks);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "marks=" + marks +
                '}';
    }
}
