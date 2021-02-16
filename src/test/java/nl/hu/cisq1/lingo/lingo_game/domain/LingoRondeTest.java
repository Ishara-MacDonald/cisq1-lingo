package nl.hu.cisq1.lingo.lingo_game.domain;

import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LingoRondeTest {
    Word woord=new Word("fiets");
    Word woordTest1=new Word("niets");
    Word woordTest2=new Word("meets");
    Word WoordTest3=new Word("klarinet");


    @Test
    void berekenPunten() {
        LingoRonde lingoRonde=new LingoRonde(woord);
        System.out.println(lingoRonde.calcWord());
        lingoRonde.addRaadBeurt(woordTest1);
        System.out.println(lingoRonde.calcWord());
        lingoRonde.addRaadBeurt(woordTest2);
        System.out.println(lingoRonde.calcWord());
        lingoRonde.addRaadBeurt(WoordTest3);
        System.out.println(lingoRonde.calcWord());
        lingoRonde.addRaadBeurt(woord);
        for(Raadbeurt raadbeurt: lingoRonde.getRaadbeurts()){
            System.out.println(raadbeurt.getRespons());

        }
        System.out.println(lingoRonde.berekenPunten());
        System.out.println(lingoRonde.calcWord());
    }

    @Test
    @DisplayName("round is done when the word is revealed")
    void checkvoltooid() {
        LingoRonde lingoRonde=new LingoRonde(woord);
        lingoRonde.addRaadBeurt(woordTest1);
        System.out.println(lingoRonde.calcWord());
        assertTrue(lingoRonde.checkoltooid());
    }
    @Test
    @DisplayName("round is not done if the word is  not revealed")
    void checkonvoltooid(){
        LingoRonde lingoRonde=new LingoRonde(woord);
        lingoRonde.addRaadBeurt(woordTest2);
        System.out.println(lingoRonde.calcWord());
        assertFalse(lingoRonde.checkoltooid());
    }
    @Test
    @DisplayName("round is done when the player tred 5 times")
    void check5tries(){
        LingoRonde lingoRonde=new LingoRonde(woord);
        lingoRonde.addRaadBeurt(woordTest2);
        lingoRonde.addRaadBeurt(woordTest2);
        lingoRonde.addRaadBeurt(woordTest2);
        lingoRonde.addRaadBeurt(woordTest2);
        lingoRonde.addRaadBeurt(woordTest2);
        assertTrue(lingoRonde.checkoltooid());
    }
    @Test
    @DisplayName("round is done when the player tred 5 times")
    void checklessthan5tries(){
        LingoRonde lingoRonde=new LingoRonde(woord);
        lingoRonde.addRaadBeurt(woordTest2);
      assertFalse(lingoRonde.checkoltooid());
    }

}