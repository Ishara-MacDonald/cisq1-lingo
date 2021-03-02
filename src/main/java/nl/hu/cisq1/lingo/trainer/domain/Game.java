package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

public class Game {

    // region parameters
    private static Long nextGameId = 0L;
    private Long gameId;

    private GameStatus gameStatus;
    private Long totalScore;

    private List<Round> rounds = new ArrayList<>();
    //endregion

    public Game(){
        this.gameStatus = GameStatus.WAITING_FOR_ROUND;
        totalScore = 0L;

        this.gameId = nextGameId;
        nextGameId += 1;
    }

    //region setters and getters

    //region setters

    public static void setNextGameId(Long nextGameId) { Game.nextGameId = nextGameId; }
    public static void setNextGameId(int nextGameId) { Game.nextGameId = (long) nextGameId; }
    public void setGameStatus(GameStatus gameStatus) { this.gameStatus = gameStatus;}
    public void setTotalScore(Long totalScore) { this.totalScore = totalScore; }
    public void setRounds(List<Round> rounds) { this.rounds = rounds; }
    public void setGameId(Long gameId) { this.gameId = gameId; }

    //endregion

    //region getters

    public GameStatus getGameStatus() { return gameStatus; }
    public Long getTotalScore() { return totalScore; }
    public List<Round> getRounds() { return rounds; }
    public Long getGameId() { return gameId; }
    public Round getCurrentRound(){
        return rounds.get( rounds.size() -1 );
    }

    //endregion

    //endregion

    public void startNewRound(){
        Round newRound = new Round();
        rounds.add(newRound);
    }

    public void startNewRound(String word, int maxAttempts){
        Round newRound = new Round(word, maxAttempts);
        rounds.add(newRound);
    }

    public List<List<Feedback>> showFeedbackPerRound(){
        List<List<Feedback>> listRound = new ArrayList<>();
        for(Round round : rounds){
            List<Feedback> listFeedback = new ArrayList<>(round.getAllFeedback());
            listRound.add(listFeedback);
        }
        return listRound;
    }

    public void addScore(){

    }

    public void endGame(){

    }
}
