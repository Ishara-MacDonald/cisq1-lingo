package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.presentation.dto.ProgressResponseDTO;
import nl.hu.cisq1.lingo.words.application.WordService;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class TrainerService {
    private WordService wordService;
    private SpringGameRepository gameRepository;

    public TrainerService(WordService wordService, SpringGameRepository gameRepository) {
        this.wordService = wordService;
        this.gameRepository = gameRepository;
    }

    public GameProgress startNewGame(){
        Game game = new Game();
        String wordToGuess = wordService.provideRandomWord(5);
        game.startNewRound(wordToGuess);
        gameRepository.save(game);

        return game.getProgress();
    }

    public GameProgress startNewRound(Long id){
        Game game = getGameById(id);
        String wordToGuess = wordService.provideRandomWord(game.nextWordLength());

        game.startNewRound(wordToGuess);
        this.gameRepository.save(game);

        return game.getProgress();
    }

    public GameProgress guess(Long gameId, String guess){
        Game game = getGameById(gameId);
        game.guess(guess);
        this.gameRepository.save(game);

        return game.getProgress();
    }

    public void showWord(Long gameId, ProgressResponseDTO progressDTO){
        Game game = getGameById(gameId);
        progressDTO.setWord(game.getWord());
    }

    public void save(Game game){
        gameRepository.save(game);
    }

    public GameProgress getProgress(Long gameId){
        return getGameById(gameId).getProgress();
    }

    private Game getGameById(Long gameId){
        return this.gameRepository.findById(gameId).
                orElseThrow(IllegalArgumentException::new);
    }
}
