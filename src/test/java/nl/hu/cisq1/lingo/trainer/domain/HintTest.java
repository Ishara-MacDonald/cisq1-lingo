package nl.hu.cisq1.lingo.trainer.domain;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class HintTest {
    Hint hint;
    String wordToGuess;
    List<Mark> absentMarks;
    List<Mark> correctMarks;

    @BeforeEach
    void setUp(){
        hint = new Hint();
        absentMarks = List.of(Mark.ABSENT, Mark.ABSENT, Mark.ABSENT);
        correctMarks = List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT);
        wordToGuess = "HEY";
    }

    @ParameterizedTest
    @MethodSource({"provideIsFirstHintExamples"})
    @DisplayName("Testing isFirstHint results with absent and correct marks.")
    void testingIsFirstHint(Hint hint, List<Mark> marks, List<String> expectedResult){
        hint.processMarksIntoHints(marks, hint, wordToGuess);
        assertEquals(expectedResult, hint.getHints());
    }

    static Stream<Arguments> provideIsFirstHintExamples(){
        return Stream.of(
                Arguments.of(new Hint(), List.of(Mark.ABSENT, Mark.ABSENT, Mark.ABSENT), List.of(".", ".", ".")),
                Arguments.of(new Hint(), List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT), List.of("H", "E", "Y")),
                Arguments.of(new Hint(List.of("H", ".", "Y")), List.of(Mark.ABSENT, Mark.ABSENT, Mark.ABSENT), List.of("H", ".", "Y")),
                Arguments.of(new Hint(List.of("H", ".", "Y")), List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT), List.of("H", "E", "Y"))
        );
    }
}
