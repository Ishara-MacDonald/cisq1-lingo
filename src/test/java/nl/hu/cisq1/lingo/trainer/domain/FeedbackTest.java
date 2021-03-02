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
        feedback = new Feedback("Hallo", "");
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

    @Test
    @DisplayName("Feedback Generates the Correct Marks")
    void feedbackGeneratesCorrectMarks(){
        feedback.addAttempt("hallo");
        assertEquals(listOfCorrectMarks, feedback.getMarks());
    }

    @Test
    @DisplayName("Feedback Generates the One Absent Mark")
    void feedbackGeneratesAbsentMark(){
        feedback.addAttempt("hello");
        assertEquals(List.of(Mark.CORRECT, Mark.ABSENT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT), feedback.getMarks());
    }

    @Test
    @DisplayName("Feedback Generates the One Present Mark")
    void feedbackGeneratesPresentMark(){
        feedback.addAttempt("hlllo");
        assertEquals(List.of(Mark.CORRECT, Mark.PRESENT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT), feedback.getMarks());
    }

    @Test
    @DisplayName("Feedback after two attempts")
    void feedbackGeneratesTwoMarks(){
        feedback.addAttempt("hello");
        System.out.println(feedback.getMarks());
        feedback.addAttempt("shirt");
        System.out.println(feedback.getMarks());
        assertEquals(List.of(Mark.ABSENT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT), feedback.getMarks());
    }
    //endregion

    //region Tests for Feedback.giveHint(): Generating Hints
    // word to guess is 'Hallo' ( see method setUp() )
    @ParameterizedTest
    @MethodSource({"provideHintExamples"})
    @DisplayName("Hint gives Letter at Correct Marks")
    void hintWithDifferentFeedback(List<String> attempts, List<String> expectedHint){
        for(String attempt: attempts){
            feedback.addAttempt(attempt);
        }
        assertEquals(new Hint(expectedHint), feedback.getLastHint());
    }

    static Stream<Arguments> provideHintExamples(){
        return Stream.of(
                Arguments.of(List.of("hallo"), List.of("H","A","L","L","O")),
                Arguments.of(List.of("hellp"), List.of("H",".","L","L",".")),
                Arguments.of(List.of("halll"), List.of("H","A","L","L",".")),
                Arguments.of(List.of("hal"), List.of(".",".",".",".",".")),
                Arguments.of(List.of("heyoo","shirt"), List.of("H",".",".",".","O")),
                Arguments.of(List.of("heyoo","shirt", "Hello"), List.of("H",".","L","L","O"))
        );
    }

    //endregion

}