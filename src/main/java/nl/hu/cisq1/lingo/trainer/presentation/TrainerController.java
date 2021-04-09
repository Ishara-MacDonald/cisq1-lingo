package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.TrainerService;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/trainer")
public class TrainerController {
    private final TrainerService service;

    public TrainerController(TrainerService trainerService){
        service = trainerService;
    }

    @PostMapping("/games")
    public GameProgress startNewGame(){
        return this.service.startNewGame();
    }

    @PostMapping("/games/{id}/newRound")
    public GameProgress startNewRound(@PathVariable Long id){
        try{
            return this.service.startNewRound(id);
        }catch(IllegalArgumentException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping("/games/{id}/{guess}}")
    public GameProgress guess(@PathVariable Long id, @PathVariable String guess){
        try{
            return this.service.guess(id, guess);
        }catch(IllegalArgumentException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping("/games/{id}/progress}")
    public GameProgress getProgress(@PathVariable Long id){
        try{
            return this.service.getProgress(id);
        }catch(IllegalArgumentException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }




}
