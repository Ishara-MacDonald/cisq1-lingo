package nl.hu.cisq1.lingo.trainer.domain;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum GameStatus {
    WAITING_FOR_ROUND("Waiting for a new Round..."),
    PLAYING("You're currently playing a Round. Use GetProgress to find your active Round ID."),
    ELIMINATED("You've been Eliminated. Start a new Game to Continue Training.");

    private final String announcement;

    GameStatus(String announcement){ this.announcement = announcement; }
}