package nl.hu.cisq1.lingo.trainer.domain;

import java.util.List;
import java.util.Objects;

public class Feedback {
    private String attempt;
    private int maxLengthWord;
    private List<Mark> marks;

    public Feedback(String attempt, int maxLengthWord, List<Mark> marks) {
        this.attempt = attempt;
        this.maxLengthWord = maxLengthWord;
        this.marks = marks;
    }

    public boolean isWordGuessed() {
        for(Mark mark : marks){
            if(! mark.equals(Mark.CORRECT))
                return false;
        }
        return true;
    }

    public boolean isWordValid(){
        return attempt.length() == maxLengthWord;
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
