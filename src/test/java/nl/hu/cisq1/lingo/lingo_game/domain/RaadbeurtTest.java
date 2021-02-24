package nl.hu.cisq1.lingo.lingo_game.domain;

import nl.hu.cisq1.lingo.lingo_game.domain.raadBeurt.Raadbeurt;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RaadbeurtTest {


    @Test
    @DisplayName("wrong length")
    void compareInvalid() {
        Raadbeurt raadbeurt=new Raadbeurt("test");
        Word word=new Word("where");
        System.out.println(raadbeurt.compare(word));
        assertSame(Mark.INVALID,raadbeurt.compare(word).get(0));
    }
    @Test
    @DisplayName("same word")
    void compareGood(){
        Raadbeurt raadbeurt=new Raadbeurt("test");
        Word word=new Word("test");
        raadbeurt.compare(word);
        System.out.println(raadbeurt.compare(word));
    }

}