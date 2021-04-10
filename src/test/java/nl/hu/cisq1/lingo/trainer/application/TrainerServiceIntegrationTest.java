package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.CiTestConfiguration;
import nl.hu.cisq1.lingo.trainer.domain.GameProgress;
import nl.hu.cisq1.lingo.trainer.domain.GameStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Import(CiTestConfiguration.class)
public class TrainerServiceIntegrationTest {

    @Autowired
    private TrainerService trainerService;

    @Test
    @DisplayName("starting a new game starts a new round")
    void startNewGame(){
        GameProgress progress = trainerService.startNewGame();

        assertEquals(GameStatus.PLAYING, progress.getGameStatus());
        assertEquals(5, progress.getHint().getHints().size());
        assertEquals(5, progress.getAttemptsLeft());
        assertEquals(0, progress.getRoundFeedback().size());
    }

    @Test
    @DisplayName("cannot start a new round when still playing")
    void cannotStartNewRound(){
        GameProgress progress = trainerService.startNewGame();
        Long id = progress.getGameID();

        assertThrows(
                Exception.class,
                () -> trainerService.startNewRound(id)
        );
    }

}
