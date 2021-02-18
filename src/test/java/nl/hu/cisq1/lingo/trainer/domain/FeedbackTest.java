package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.*;

import java.beans.FeatureDescriptor;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {
    List<Mark> listOfCorrectMarks = List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT );
    List<Mark> listOfIncorrectMarks = List.of(Mark.ABSENT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT );

    @Test
    @DisplayName("Word is guess if all letters are correct")
    void wordIsGuessed(){
        Feedback feedback = new Feedback("", 5, listOfCorrectMarks);
        assertTrue(feedback.isWordGuessed());
    }
    
    @Test
    @DisplayName("Word is not guessed if not all letters are correct")
    void wordIsNotGuessed(){
        Feedback feedback = new Feedback("", 5, listOfIncorrectMarks);
        assertFalse(feedback.isWordGuessed());
    }
    
    @Test
    @DisplayName("Word is has the correct amount of letters")
    void guessIsValid(){
        Feedback feedback = new Feedback("Hello", 5, listOfCorrectMarks);
        assertTrue(feedback.isWordValid());
    }

    @Test
    @DisplayName("Word too long and therefore Invalid")
    void guessIsTooLong(){
        Feedback feedback = new Feedback("Hello", 6, listOfCorrectMarks);
        assertFalse(feedback.isWordValid());
    }

    @Test
    @DisplayName("Word too short and therefore Invalid")
    void guessIsTooShort(){
        Feedback feedback = new Feedback("Hello", 4, listOfCorrectMarks);
        assertFalse(feedback.isWordValid());
    }
}