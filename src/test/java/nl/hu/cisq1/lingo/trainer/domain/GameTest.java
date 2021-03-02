package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

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
        game.startNewRound();
        assertFalse(game.getRounds().isEmpty());
    }

    @Test
    @DisplayName("getCurrentRound() should give the latest round")
    void getLatestRound(){
        game.setRounds(List.of(new Round(), new Round(), new Round()));

        assertEquals(2, game.getCurrentRound().getRoundId());
    }

    @Test
    @DisplayName("showFeedbackPerRound should give the feedback per round")
    void getRoundWithFeedback(){
        game.startNewRound("Shirt", 3);
        Round currentRound = game.getCurrentRound();

        mockRound(currentRound);

        assertEquals(1, game.showFeedbackPerRound().size());
    }

    @Test
    @DisplayName("showFeedbackPerRound should give the feedback per round")
    void getFeedbackWithRound(){
        game.startNewRound("Shirt", 3);
        Round currentRound = game.getCurrentRound();

        mockRound(currentRound);

        assertEquals(3, game.showFeedbackPerRound().get(0).size());
    }

    void mockRound(Round round){
        round.addAttempt("sallo");
        round.addAttempt("Hallo");
        round.addAttempt("short");
    }


}