package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import nl.hu.cisq1.lingo.trainer.domain.Hint;
import nl.hu.cisq1.lingo.words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.swing.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TrainerServiceTest {

    @Test
    @DisplayName("starting a new game starts a new round")
    void startNewGame(){
        WordService wordService = mock(WordService.class);

        when(wordService.provideRandomWord(5)).thenReturn("baard");
        SpringGameRepository repository = mock(SpringGameRepository.class);

        TrainerService trainerService = new TrainerService(wordService, repository);
        GameProgress progress = trainerService.startNewGame();

        assertEquals(new Hint(List.of("B", ".", ".",".", ".")), progress.getHint());
        assertEquals(GameStatus.PLAYING, progress.getGameStatus());
    }

    @Test
    @DisplayName("Starting a new Round")
    void startNewRound(){
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(6))
                .thenReturn("hoeden");

        Game game = new Game();
        game.startNewRound("baard");
        game.guess("baard");

        SpringGameRepository repository = mock(SpringGameRepository.class);
        when(repository.findById(anyLong())).
                thenReturn(Optional.of(game));

        TrainerService trainerService = new TrainerService(wordService, repository);

        GameProgress progress = trainerService.startNewRound(0L);

        assertEquals(new Hint(List.of("H", ".", "." , "." , "." , ".")), progress.getHint());
        assertEquals(GameStatus.PLAYING, progress.getGameStatus());
    }

    @ParameterizedTest
    @MethodSource("providingGuesses")
    @DisplayName("testing taking guesses and its outcome")
    void guess(List<String> guesses, List<String> expectedHints, GameStatus expectedGameStatus){
        WordService wordService = mock(WordService.class);

        Game game = new Game();
        game.startNewRound("baard");
        game.setMaxAttempts(2);

        SpringGameRepository repository = mock(SpringGameRepository.class);
        when(repository.findById(anyLong())).
                thenReturn(Optional.of(game));

        TrainerService trainerService = new TrainerService(wordService, repository);

        GameProgress progress = trainerService.getProgress(0L);
        for(String guess : guesses){
            progress = trainerService.guess(0L, guess);
        }

        assertEquals(new Hint(expectedHints), progress.getHint());
        assertEquals(expectedGameStatus, game.getGameStatus());
    }

    static Stream<Arguments> providingGuesses(){
        return Stream.of(
                Arguments.of(List.of("baard"), List.of("B", "A", "A", "R", "D"), GameStatus.WAITING_FOR_ROUND),
                Arguments.of(List.of("beren"), List.of("B", ".", ".", ".", "."), GameStatus.PLAYING),
                Arguments.of(List.of("braad"), List.of("B", ".", "A", ".", "D"), GameStatus.PLAYING),
                Arguments.of(List.of("beren", "braad"), List.of("B", ".", "A", ".", "D"), GameStatus.ELIMINATED)
        );
    }
}