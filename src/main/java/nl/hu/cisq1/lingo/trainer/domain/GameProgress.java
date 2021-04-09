package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class GameProgress {
    //region parameters
    public Long gameID;
    public GameStatus gameStatus;
    public List<Feedback> roundFeedback;
    public Hint hint;
    public int attemptsLeft;

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
