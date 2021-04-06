package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game;
    @BeforeEach
    void setUp(){
        Game.setNextGameId(0);
        Round.setNextId(0);
        game = new Game(5);
    }

    @Test
    @DisplayName("Creating a Game automatically sets the gameId")
    void gameIdIsMade(){
        assertEquals(0, game.getGameId());
    }

    @Test
    @DisplayName("A new Game gets a new gameId")
    void newGameIdGetsGenerated(){
        Game secondGame = new Game(5);
        assertEquals(1, secondGame.getGameId());
    }

    @Test
    @DisplayName("When a new round is started, this round is added to rounds")
    void startNewGame(){
        game.startNewRound("");
        assertFalse(game.getRounds().isEmpty());
    }

    @Test
    @DisplayName("getCurrentRound() should give the latest round")
    void getLatestRound(){
        game.setRounds(List.of(new Round("", 2), new Round("", 2), new Round("", 2)));

        assertEquals(2, game.getProgress().getRoundId());
    }

    @ParameterizedTest
    @MethodSource("provideExamplesForGameProgress")
    @DisplayName("Feedback should be added to list of Feedback with every Guess.")
    void getFeedbackWithGameProgress(int maxAttempts, List<String> attempts, int expected){
        game.startNewRound("Shirt");
        game.setMaxAttempts(maxAttempts);
        for(String attempt : attempts){
            game.guess(attempt);
        }

        assertEquals(expected, game.getProgress().getRoundFeedback().size());
    }

    static Stream<Arguments> provideExamplesForGameProgress(){
        return Stream.of(
                Arguments.of(3, List.of("Short", "Hazel"), 2),
                Arguments.of(3, List.of("Short", "Doodle", "Hazel"), 3),
                Arguments.of(3, List.of("Short", "Doodle", "Hazel", "House"), 3),
                Arguments.of(3, List.of("Shirt", "Doodle", "Hazel", "House"), 1)
        );
    }

    @Test
    @DisplayName("When Round is created, First letter of the wordToGuess should be showed.")
    void showHintWhenRoundCreated(){
        game.startNewRound("shirt");
        assertEquals(List.of("S", ".", ".", ".", "."), game.getProgress().getRound().getLastHint().getHints());
    }

    @ParameterizedTest
    @MethodSource("provideHintsForRoundActive")
    @DisplayName("New Round can only be made when there isn't a Round active already.")
    void canPlayerStartNewRound(List<String> wordsToGuess, List<String> attempts, int expectedAmountRounds){
        for(int counter = 0; counter < wordsToGuess.size(); counter++){
            game.startNewRound(wordsToGuess.get(counter));
            game.guess(attempts.get(counter));
        }
        assertEquals(expectedAmountRounds, game.getRounds().size());
    }

    static Stream<Arguments> provideHintsForRoundActive(){
        return Stream.of(
                Arguments.of(List.of("shirt", "button"), List.of("shirt", "button"), 2),
                Arguments.of(List.of("shirt", "button"), List.of("button", "button"), 1)
        );
    }

    @Test
    @DisplayName("Round can't be made when player is eliminated")
    void noNewRoundAfterElimination(){
        game.startNewRound("shirt");
        game.setMaxAttempts(1);
        game.guess("button");
        game.guess("shirt");

        game.startNewRound("button");

        assertEquals(1, game.getRounds().size());
    }


}