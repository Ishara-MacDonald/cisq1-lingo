package nl.hu.cisq1.lingo.trainer.domain;

import java.util.ArrayList;
import java.util.List;

public class Game {
    // region parameters
    private static int startWordLength = 5;
    private int wordLength = 5;
    private static Long nextGameId = 0L;
    private Long gameId;

    private GameStatus gameStatus;
    private Long totalScore;
    private int maxAttempts = 5;

    private List<Round> rounds = new ArrayList<>();
    //endregion

    public Game(){
        this.gameStatus = GameStatus.WAITING_FOR_ROUND;
        totalScore = 0L;
        wordLength = startWordLength;

        this.gameId = nextGameId;
        nextGameId += 1;
    }

    //region setters and getters

    //region setters
    public void setMaxAttempts(int maxAttempts){
        this.maxAttempts = maxAttempts;
        getCurrentRound().setMaxAttempts(maxAttempts);
    }
    public static void setNextGameId(int nextGameId) { Game.nextGameId = (long) nextGameId; }
    public void setRounds(List<Round> rounds) { this.rounds = rounds; }

    //endregion

    //region getters
    public List<Round> getRounds() { return rounds; }
    public Long getGameId() { return gameId; }
    private Round getCurrentRound(){
        return rounds.get( rounds.size() -1 );
    }

    //endregion

    //endregion
    public void startNewRound(String wordToGuess){
        Round newRound = new Round(wordToGuess, maxAttempts);
        rounds.add(newRound);
    }

    public void guess(String attempt){
        getCurrentRound().addAttempt(attempt);
    }

    public GameProgress getProgress(){
         return new GameProgress(getCurrentRound());
    }

    private int nextWordLength(){
        wordLength += 1;
        return wordLength;
    }
}
