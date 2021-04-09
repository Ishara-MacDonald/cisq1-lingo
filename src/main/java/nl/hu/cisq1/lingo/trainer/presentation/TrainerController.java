package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.Feedback;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.trainer.presentation.dto.FeedbackResponseDTO;
import nl.hu.cisq1.lingo.trainer.presentation.dto.ProgressResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/trainer")
public class TrainerController {
    private final TrainerService service;

    public TrainerController(TrainerService trainerService){
        service = trainerService;
    }

    @PostMapping("/games")
    public ProgressResponseDTO startNewGame(){
        return convertProgressToDTO(this.service.startNewGame());
    }

    @PostMapping("/games/{id}/newRound")
    public ProgressResponseDTO startNewRound(@PathVariable Long id){
        try{
            return convertProgressToDTO(this.service.startNewRound(id));
        }catch(IllegalArgumentException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping("/games/{id}/{guess}")
    public ProgressResponseDTO guess(@PathVariable Long id, @PathVariable String guess){
        try{
            GameProgress progress = this.service.guess(id,guess);
            ProgressResponseDTO progressDTO = convertProgressToDTO(progress);
            if(progress.getAttemptsLeft() == 0){
                this.service.showWord(id, progressDTO);
            }
            return progressDTO;
        }catch(IllegalArgumentException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping("/games/{id}/progress}")
    public ProgressResponseDTO getProgress(@PathVariable Long id){
        try{
            return convertProgressToDTO(this.service.getProgress(id));
        }catch(IllegalArgumentException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    private ProgressResponseDTO convertProgressToDTO(GameProgress progress){
        List<FeedbackResponseDTO> feedbackResponseDTOs = new ArrayList<>();

        if(!progress.getRoundFeedback().isEmpty()){
            for(Feedback feedback: progress.getRoundFeedback()){
                feedbackResponseDTOs.add(new FeedbackResponseDTO(feedback.getAttempt(), feedback.getMarks()));
            }
        }

        return new ProgressResponseDTO(progress.getGameID(), progress.getGameStatus(), feedbackResponseDTOs, progress.getHint(), progress.getAttemptsLeft());
    }
}
