package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.Hint;
import nl.hu.cisq1.lingo.trainer.domain.Mark;

import java.util.List;

public class FeedbackResponseDTO {
    public String attempt;
    public List<Mark> marks;

    public FeedbackResponseDTO(String attempt, List<Mark> marks){
        this.attempt = attempt;
        this.marks = marks;
    }
}
