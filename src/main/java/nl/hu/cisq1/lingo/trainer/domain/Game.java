package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Data;
import java.util.*;

@Data
public class Game {
    // region parameters
    private static int startWordLength = 5;
    private int wordLength = 5;
    private static Long nextGameId = 0L;
    private Long gameId;

    private GameStatus gameStatus = GameStatus.WAITING_FOR_ROUND;
    private Long totalScore;
    private int maxAttempts;

    private List<Round> rounds = new ArrayList<>();
    //endregion

    public Game(int maxAttempts){
        this.maxAttempts = maxAttempts;
        gameSetup();
    }

    public void gameSetup(){
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
        if(!rounds.isEmpty()){
            getCurrentRound().setMaxAttempts(maxAttempts);
        }
    }
    public static void setNextGameId(int nextGameId) { Game.nextGameId = (long) nextGameId; }
    //endregion

    //region getters
    public GameProgress getProgress(){
        return new GameProgress(getCurrentRound());
    }
    private Round getCurrentRound(){
        return rounds.get( rounds.size() -1 );
    }
    //endregion

    //endregion
    public GameStatus startNewRound(String wordToGuess){
        if(gameStatus == GameStatus.WAITING_FOR_ROUND){
            gameStatus = GameStatus.PLAYING;
            Round newRound = new Round(wordToGuess, maxAttempts);
            rounds.add(newRound);
        }
        return gameStatus;
    }

    public void guess(String attempt){
        getCurrentRound().addAttempt(attempt);
        updateGameStatus();
    }

    public void updateGameStatus(){
        gameStatus = GameStatus.WAITING_FOR_ROUND;
        for(Round round : rounds){
            if(round.isRoundActive()){
                gameStatus = GameStatus.PLAYING;
                break;
            }
        }
        if(getCurrentRound().isPlayerEliminated()){
            gameStatus = GameStatus.ELIMINATED;
        }

    }

    private int nextWordLength(){
        wordLength += 1;
        return wordLength;
    }
}
