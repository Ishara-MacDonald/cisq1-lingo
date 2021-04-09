package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GameProgress {
    //region parameters
    private Long gameID;
    private GameStatus gameStatus;
    private List<Feedback> roundFeedback;
    private Hint hint;
    private List<Mark> marks;
    private int attemptsLeft;

    //endregion

    //region setUp
    public GameProgress(Game game, Round round){
        gameID = game.getGameId();
        gameStatus = game.getGameStatus();
        hint = round.getLastHint();

//        isActive = round.isRoundActive();
        roundFeedback = round.getAllFeedback();

        attemptsLeft = game.getMaxAttempts() - roundFeedback.size();
    }
    //endregion
}
