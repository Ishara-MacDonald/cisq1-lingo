package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {
    Round round;
    @BeforeEach
    void setUp(){
        Round.setNextId(0);
        round = new Round("Hey", 2);
    }

    //region Tests for RoundId
    @Test
    @DisplayName("Creating a Round automatically sets the roundId")
    void roundIdIsMade(){
        assertEquals(0, round.getRoundId());
    }

    @Test
    @DisplayName("A new round gets a new roundId")
    void roundIdGetsGenerated(){
        Round secondRound = new Round("", 2);
        assertEquals(1, secondRound.getRoundId());
    }
    //endregion

    //region Tests for List<Feedback> allFeedback
    @Test
    @DisplayName("When adding an attempt, a feedback gets added to the allFeedback list.")
    void feedbackAddedToList(){
        round.processAttempts("Hey");
        assertFalse(round.getAllFeedback().isEmpty());
    }

    @Test
    @DisplayName("When adding new attempts, feedbacks get added to the allFeedback list.")
    void processingMultipleAttempts(){
        round.processAttempts("Hel");
        round.processAttempts("Hai");
        round.processAttempts("Hey");
        assertEquals(3, round.getAllFeedback().size());
    }

        // region Feedback and Marks
        // Go to #FeedbackTest.java for more Feedback tests
    @Test
    @DisplayName("When adding a correct attempt, feedback gives back a correct mark")
    void correctAttemptGivesBackFeedback(){
        round.processAttempts("Hey");
        assertEquals(List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT), round.getAllFeedback().get(0).getMarks());
    }

    @Test
    @DisplayName("When adding a incorrect attempt, feedback gives back a incorrect mark")
    void incorrectAttemptGivesBackFeedback() {
        round.processAttempts("Hei");
        assertEquals(List.of(Mark.CORRECT, Mark.CORRECT, Mark.ABSENT), round.getAllFeedback().get(0).getMarks());
    }

    @Test
    @DisplayName("When adding a invalid attempt, feedback gives back invalid marks")
    void invalidAttemptGivesBackFeedback(){
        round.processAttempts("He");
        assertEquals(List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID), round.getAllFeedback().get(0).getMarks());
    }
        //endregion

        // region maxAttempts
    @Test
    @DisplayName("There cannot be more attempts than the set maxAttempts")
    void maxAttemptsTest(){
        round.setMaxAttempts(3);

        round.addAttempt("Hei");
        round.addAttempt("Hei");
        round.addAttempt("Hei");
        round.addAttempt("Hei");
        assertEquals(3, round.getAllFeedback().size());
    }
        //endregion maxAttempts
    //endregion allFeedback

    //region calculateScore()

    @Test
    @DisplayName("maxScore increases the more letter the player has to guess")
    void maxScoreIncrease(){
        round.setWordToGuess("hi");
        int lowerScore = round.getMaxScore();
        round.setWordToGuess("hello");
        int higherScore = round.getMaxScore();
        assertTrue(lowerScore < higherScore);
    }

    @Test
    @DisplayName("score gives back maxScore when first attempt is correct")
    void scoreOnFirstCorrectAttempt(){
        round.processAttempts("Hey");
        assertEquals(round.getMaxScore(), round.getCurrentScore());
    }

    @Test
    @DisplayName("Score gives back lower score than maxScore when second attempt is correct")
    void scoreOnSecondCorrectAttempt(){
        round.setMaxAttempts(5);
        round.processAttempts("oio");
        round.processAttempts("Hey");
        assertTrue(round.getCurrentScore() < round.getMaxScore());
    }
    //endregion

    //region isRoundActive()
    @Test
    void roundisStillActive(){
        round.processAttempts("hoi");
        assertTrue(round.isRoundActive());
    }

    @Test
    void roundIsSetInactive(){
        round.processAttempts("Hey");
        assertFalse(round.isRoundActive());
    }
    //endregion

    //region Tests for roundattempts
    @ParameterizedTest
    @MethodSource("provideExamplesForGameProgress")
    @DisplayName("")
    void getFeedbackWithGameProgress(int maxAttempts, List<String> attempts, int expected){
        round.setMaxAttempts(maxAttempts);
        for(String attempt : attempts){
            round.addAttempt(attempt);
        }

        assertEquals(expected, round.getAllFeedback().size());
    }

    static Stream<Arguments> provideExamplesForGameProgress(){
        return Stream.of(
                Arguments.of(3, List.of("Short", "Hazel"), 2),
                Arguments.of(3, List.of("Shirt", "Doodle", "Hazel"), 3),
                Arguments.of(3, List.of("Shirt", "Doodle", "Hazel", "House"), 3)
        );
    }
    //endregion
}