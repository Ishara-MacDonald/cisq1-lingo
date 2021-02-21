package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
    List<Mark> listOfCorrectMarks = List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT );
    List<Mark> listOfMarksWithOneAbsent = List.of(Mark.ABSENT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT );
    List<Mark> listOfMarksWithOneCorrect = List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT );

    //region Tests for isWordGuessed()

    @Test
    @DisplayName("Word is guess if all letters are correct")
    void wordIsGuessed(){
        Feedback feedback = new Feedback("", "");
        feedback.setMarks(listOfCorrectMarks);
        assertTrue(feedback.isWordGuessed());
    }
    
    @Test
    @DisplayName("Word is not guessed if not all letters are correct")
    void wordIsNotGuessed(){
        Feedback feedback = new Feedback("", "");
        feedback.setMarks(listOfMarksWithOneAbsent);
        assertFalse(feedback.isWordGuessed());
    }

    //endregion

    //region Tests for isWordValid()

    @Test
    @DisplayName("Word is has the correct amount of letters")
    void guessIsValid(){
        Feedback feedback = new Feedback("Hello", "shirt");
        assertTrue(feedback.isWordValid());
    }

    @Test
    @DisplayName("Word too long and therefore Invalid")
    void guessIsTooLong(){
        Feedback feedback = new Feedback("Hello", "Tshirt");
        assertFalse(feedback.isWordValid());
    }

    @Test
    @DisplayName("Word too short and therefore Invalid")
    void guessIsTooShort(){
        Feedback feedback = new Feedback("Hello", "cat");
        assertFalse(feedback.isWordValid());
    }

    //endregion

    //region Tests for generateMarks(): Generating Marks

    @Test
    @DisplayName("Feedback Generates the Correct Marks")
    void feedbackGeneratesCorrectMarks(){
        Feedback feedback = new Feedback("Hello", "Hello");
        feedback.generateMarks();
        assertEquals(listOfCorrectMarks, feedback.getMarks());
    }

    @Test
    @DisplayName("Feedback Generates the One Absent Mark")
    void feedbackGeneratesAbsentMark(){
        Feedback feedback = new Feedback("Hello", "Hallo");
        feedback.generateMarks();
        assertEquals(List.of(Mark.CORRECT, Mark.ABSENT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT), feedback.getMarks());
    }

    @Test
    @DisplayName("Feedback Generates the One Present Mark")
    void feedbackGeneratesPresentMark(){
        Feedback feedback = new Feedback("Hlllo", "Hallo");
        feedback.generateMarks();
        assertEquals(List.of(Mark.CORRECT, Mark.PRESENT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT), feedback.getMarks());
    }
    //endregion

    //region Tests for giveHint(): Generating Hints

    @Test
    @DisplayName("Hint gives letter at All Correct Marks")
    void hintWithAllCorrectMarks(){
        Feedback feedback = new Feedback("Hallo", "Hallo");
        feedback.generateMarks();
        Hint dummyHint = new Hint(List.of("H","A","L","L","O"));
        assertEquals(feedback.giveHint(), dummyHint);
    }

    @Test
    @DisplayName("Hint doesn't Give Letter at Absent Marks")
    void hintWithAbsentMark(){
        Feedback feedback = new Feedback("Hellp", "Hallo");
        feedback.generateMarks();
        Hint dummyHint = new Hint(List.of("H",".","L","L","."));
        assertEquals(feedback.giveHint(), dummyHint);
    }

    @Test
    @DisplayName("Hint doesn't Give Letter at Present Marks")
    void hintWithPresentMark(){
        Feedback feedback = new Feedback("Halll", "Hallo");
        feedback.generateMarks();
        Hint dummyHint = new Hint(List.of("H","A","L","L","."));
        assertEquals(feedback.giveHint(), dummyHint);
    }

    //endregion

}