package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.Hint;
import nl.hu.cisq1.lingo.trainer.domain.Mark;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class FeedbackResponseDTO extends RepresentationModel<FeedbackResponseDTO> {
    public String attempt;
    public List<Mark> marks;

    public FeedbackResponseDTO(String attempt, List<Mark> marks){
        this.attempt = attempt;
        this.marks = marks;
    }
}
