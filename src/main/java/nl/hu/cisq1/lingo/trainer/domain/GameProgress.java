package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
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
        isActive = round.isRoundActive();
        roundFeedback = round.getAllFeedback();
        for(Feedback feedback : round.getAllFeedback()){
            attempts.add(feedback.getAttempt());
        }
    }

    public Hint getHint(){
        return round.getLastHint();
    }
    //endregion
}
