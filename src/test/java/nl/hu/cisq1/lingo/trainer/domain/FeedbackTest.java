package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
    List<Mark> listOfCorrectMarks = List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT );
    List<Mark> listOfMarksWithOneAbsent = List.of(Mark.ABSENT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT );
    Feedback feedback;

    @BeforeEach
    void setUp(){
        feedback = new Feedback("BAARD", "");
    }

    //region Tests for isWordGuessed()

    @Test
    @DisplayName("Word is guess if all letters are correct")
    void wordIsGuessed(){
        feedback.setMarks(listOfCorrectMarks);
        assertTrue(feedback.isWordGuessed());
    }
    
    @Test
    @DisplayName("Word is not guessed if not all letters are correct")
    void wordIsNotGuessed(){
        feedback.setMarks(listOfMarksWithOneAbsent);
        assertFalse(feedback.isWordGuessed());
    }

    //endregion

    //region Tests for isWordValid()
    @Test
    @DisplayName("Word is has the correct amount of letters")
    void guessIsValid(){
        feedback.addAttempt("shirt");
        assertTrue(feedback.isWordValid());
    }

    @Test
    @DisplayName("Word too long and therefore Invalid")
    void guessIsTooLong(){
        feedback.addAttempt("Tshirt");
        assertFalse(feedback.isWordValid());
    }

    @Test
    @DisplayName("Word too short and therefore Invalid")
    void guessIsTooShort(){
        feedback.addAttempt("cat");
        assertFalse(feedback.isWordValid());
    }

    //endregion

    //region Tests for generateMarks(): Generating Marks
    @ParameterizedTest
    @MethodSource({"provideMarkExamples"})
    @DisplayName("Feedback generates correct marks")
    void feedbackGeneratesMarks(List<String> attempts, List<Mark> expectedMarks){
        for(String attempt : attempts){
            feedback.addAttempt(attempt);
        }
        assertEquals(expectedMarks, feedback.getMarks());
    }

    static Stream<Arguments> provideMarkExamples(){
        return Stream.of(
                Arguments.of(List.of("baard"), List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT )),
                Arguments.of(List.of("board"), List.of(Mark.CORRECT, Mark.ABSENT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT)),
                Arguments.of(List.of("brard"), List.of(Mark.CORRECT, Mark.PRESENT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT)),
                Arguments.of(List.of("board", "magen"), List.of(Mark.ABSENT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT))
        );
    }
    //endregion

    //region Tests for Feedback.giveHint(): Generating Hints
    // word to guess is 'BAARD' ( see method setUp() )

    @Test
    void initialHint(){
        System.out.println(feedback.getLastHint());
        assertEquals(List.of("B", ".", ".", ".", "."), feedback.getLastHint().getHints());
    }

    @ParameterizedTest
    @MethodSource({"provideHintExamples"})
    @DisplayName("Hint gives Letter at Correct Marks")
    void hintWithDifferentFeedback(List<String> attempts, List<String> expectedHint){
        for(String attempt: attempts){
            feedback.addAttempt(attempt);
        }
        System.out.println(new Hint(expectedHint) + " " + feedback.getLastHint());
        assertEquals(new Hint(expectedHint), feedback.getLastHint());
    }

    static Stream<Arguments> provideHintExamples(){
        return Stream.of(
                Arguments.of(List.of("BAARD"), List.of("B","A","A","R","D")),
                Arguments.of(List.of("BONJE"), List.of("B",".",".",".",".")),
                Arguments.of(List.of("DRAAD"), List.of("B",".","A",".","D")),
                Arguments.of(List.of("BERGEN"), List.of("B",".",".",".",".")),
                Arguments.of(List.of("BRAAD","SHIRT"), List.of("B",".","A","R","D")),
                Arguments.of(List.of("DRAAD","BERGT", "BOARD"), List.of("B",".","A","R","D")),
                Arguments.of(List.of("barst", "draad"), List.of("B", "A", "A", ".", "D"))
        );
    }

    //endregion

}