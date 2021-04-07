package nl.hu.cisq1.lingo.trainer.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hint {
    private List<String> hints = new ArrayList<>();

    public Hint firstHintOfRound(String word){
        String upperCaseWord = word.toUpperCase();
        List<String> hints = new ArrayList<>();
        for(int x = 0; x < upperCaseWord.length(); x++){
            if(x == 0){
                hints.add(String.valueOf(upperCaseWord.charAt(x)));
            }else{
                hints.add(".");
            }
        }
        return new Hint(hints);
    }

    public void processFeedbackIntoHints(List<Mark> marks, Hint hint, String wordToGuess){
        List<String> hintPerLetter = new ArrayList<>();
        boolean isFirstHint = hint.getHints().isEmpty();

        for(int counter = 0; counter < marks.size(); counter ++){
            if(marks.get(counter) == Mark.CORRECT){
                hintPerLetter.add(String.valueOf(wordToGuess.charAt(counter)));
            }
            else if(!isFirstHint) {
                hintPerLetter.add(getHints().get(counter));
            }
            else {
                hintPerLetter.add(".");
            }
        }
        hints = hintPerLetter;
    }
}
