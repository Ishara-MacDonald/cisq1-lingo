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
        game = new Game();
    }

    @Test
    @DisplayName("Creating a Game automatically sets the gameId")
    void gameIdIsMade(){
        assertEquals(0, game.getGameId());
    }

    @Test
    @DisplayName("A new Game gets a new gameId")
    void newGameIdGetsGenerated(){
        Game secondGame = new Game();
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
        game.setRounds(List.of(new Round(), new Round(), new Round()));

        assertEquals(2, game.getProgress().getRoundId());
    }

//    @Test
//    @DisplayName("showFeedbackPerRound should give the feedback per round")
//    void getRoundWithFeedback(){
//        game.startNewRound("Shirt");
//        game.setMaxAttempts(3);
//        Round currentRound = game.getProgress().getRound();
//
//        mockRound(currentRound);
//
//        assertEquals(1, game.getProgress().getRoundFeedback().size());
//    }

    @ParameterizedTest
    @MethodSource("provideExamplesForGameProgress")
    @DisplayName("")
    void getFeedbackWithGameProgress(int maxAttempts, List<String> attempts, int expected){
        game.startNewRound("Shirt");
        game.setMaxAttempts(maxAttempts);
        GameProgress gameProgress = game.getProgress();
        Round currentRound = gameProgress.getRound();

        for(String attempt : attempts){
            currentRound.addAttempt(attempt);
        }

        assertEquals(expected, gameProgress.getRoundFeedback().size());
    }

    static Stream<Arguments> provideExamplesForGameProgress(){
        return Stream.of(
                Arguments.of(3, List.of("Short", "Hazel"), 2),
                Arguments.of(3, List.of("Shirt", "Doodle", "Hazel"), 3),
                Arguments.of(3, List.of("Shirt", "Doodle", "Hazel", "House"), 3)
        );
    }


}