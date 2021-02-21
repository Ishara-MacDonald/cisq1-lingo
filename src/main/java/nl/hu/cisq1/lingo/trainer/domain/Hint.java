package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Hint {
    private List<String> hints;
    private String hintedLetter;
    private String wordToGuess;

    public Hint(){}

    public Hint(List<String> hints){
        this.hints = hints;
    }

    public void setHints(List<String> hints){
        this.hints = hints;
    }

    public List<String> processFeedbackIntoHints(List<Mark> feedback, String wordToGuess){
        List<String> listOfLettersOfWord = new ArrayList<String>(Arrays.asList(wordToGuess.split("")));
        List<String> hints = new ArrayList<>();
        int counter = 0;

        for(Mark mark : feedback){
            if(mark == Mark.CORRECT){
                hints.add(listOfLettersOfWord.get(counter));
            }else
                hints.add(".");
            counter++;
        }
        return hints;
    }

    @Override
    public String toString() {
        return "Hint{" +
                "hints=" + hints +
                ", hintedLetter='" + hintedLetter + '\'' +
                ", wordToGuess='" + wordToGuess + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hint hint = (Hint) o;
        return Objects.equals(hints, hint.hints) &&
                Objects.equals(hintedLetter, hint.hintedLetter) &&
                Objects.equals(wordToGuess, hint.wordToGuess);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hintedLetter);
    }
}
