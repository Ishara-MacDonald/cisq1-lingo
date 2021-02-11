package nl.hu.cisq1.lingo.lingo_game.domain;

import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LingoRondeTest {
    Word woord=new Word("fiets");
    Word woordTest1=new Word("niets");
    Word woordTest2=new Word("meets");
    Word WoordTest3=new Word("klarinet");
    LingoRonde lingoRonde=new LingoRonde(woord);

    @Test
    void berekenPunten() {
        lingoRonde.addRaadBeurt(woordTest1);
        lingoRonde.addRaadBeurt(woordTest2);
        lingoRonde.addRaadBeurt(WoordTest3);
        for(Raadbeurt raadbeurt: lingoRonde.getRaadbeurts()){
            System.out.println(raadbeurt.getRespons());

        }
        System.out.println(lingoRonde.getPunten());
        System.out.println(lingoRonde.getRevealedWord());
    }

    @Test
    void addRaadBeurt() {
    }
}