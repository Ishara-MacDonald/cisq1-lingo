package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.words.application.WordService;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class GameService {
    private WordService wordService;
    private SpringGameRepository gameRepository;

    public GameService(WordService wordService, SpringGameRepository gameRepository) {
        this.wordService = wordService;
        this.gameRepository = gameRepository;
    }

    public GameProgress startNewGame(int maxAttempts){
        Game game = new Game(maxAttempts);
        String wordToGuess = wordService.provideRandomWord(game.nextWordLength());

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

    public void guess(String attempt){

    }

    public GameProgress getProgress(){
        return null;
    }

    private Game getGameById(Long gameId){
        return this.gameRepository.findById(gameId).
                orElseThrow(IllegalArgumentException::new);
    }
}
