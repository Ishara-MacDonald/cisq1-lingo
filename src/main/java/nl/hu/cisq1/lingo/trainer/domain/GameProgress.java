package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

public class GameProgress {
    //region parameters
    public Round round;
    public long longId;
    public boolean isActive;
    public List<Feedback> roundFeedback;
    public List<String> attempts = new ArrayList<>();

    //endregion

    //region setUp
    public GameProgress(Round round){
        this.round = round;
        setUp();
    }

    private void setUp(){
        longId = round.getRoundId();
        isActive = round.isRoundActive();
        roundFeedback = round.getAllFeedback();
        attempts = round.getAttempts();
    }
    //endregion

    //region getters
    public Round getRound() { return round; }
    public List<Feedback> getRoundFeedback() { return roundFeedback; }

    public long getRoundId() { return longId; }
    //endregion


}
