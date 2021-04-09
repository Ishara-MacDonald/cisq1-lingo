package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.Hint;
import nl.hu.cisq1.lingo.trainer.domain.Mark;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.List;

public class ProgressResponseDTO extends RepresentationModel<ProgressResponseDTO> {
    public Long gameID;
    public GameStatus gameStatus;
    public String word = "Unknown: Guess To Find Out.";
    public Hint hint;
    public int attemptsLeft;
    public List<FeedbackResponseDTO> roundFeedback;

    public ProgressResponseDTO(Long gameID, GameStatus gameStatus, List<FeedbackResponseDTO> feedback, Hint hint, int attemptsLeft){
        this.gameID = gameID;
        this.gameStatus = gameStatus;
        this.roundFeedback = feedback;
        this.hint = hint;
        this.attemptsLeft = attemptsLeft;
    }

    public void setWord(String word){
        this.word = word;
    }
}
