package nl.hu.cisq1.lingo.lingo_game.domain.lingoRonde;

import nl.hu.cisq1.lingo.lingo_game.domain.Mark;
import nl.hu.cisq1.lingo.lingo_game.domain.raadBeurt.Raadbeurt;
import nl.hu.cisq1.lingo.words.domain.Word;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "lingoronde")
public class LingoRonde {
    @ManyToOne
    private Word woord;
    @OneToMany
    private List<Raadbeurt> raadbeurts;
    @Id
    private Long id;


    public LingoRonde(Word woord){
        this.woord=woord;
        raadbeurts=new ArrayList<>();
    }

    public LingoRonde() {
    }

    private int countTries(){return  raadbeurts.size();}

    public int berekenPunten(){
        int total=5*(5- raadbeurts.size()+5);
        return  total;
    }
    public boolean checkoltooid(){
        StringBuilder sb = new StringBuilder();
        for (Character ch : calcWord()) {
            sb.append(ch);
        }
        String string = sb.toString();



        return countTries() >= 5 || string.equals(woord.getValue());
    }

    public void addRaadBeurt(Word woord1){
        if (countTries()!=5||!checkoltooid()){
            Raadbeurt raadbeurt=new Raadbeurt(woord1.getValue());
            raadbeurts.add(raadbeurt);
            //raadbeurts.add(raadbeurt.compare(woord));
        }

    }
    public ArrayList<Character>calcWord(){
        ArrayList<Character> woord1=new ArrayList<>();
        for (int i = 0; i < woord.getLength() ; i++) {
            ArrayList<Mark>resultaten=new ArrayList<>();
            for (Raadbeurt raadbeurt:raadbeurts) {
                Mark mark1 = raadbeurt.compare(woord).get(i);
                resultaten.add(mark1);
            }
            if (resultaten.contains(Mark.CORRECT)){
                woord1.add(woord.getValue().charAt(i));
            }
            else {
                woord1.add(' ');}
        }

        woord1.set(0,woord.getValue().charAt(0));
        return woord1;
    }

    public void setWoord(Word woord) {
        this.woord = woord;
    }

    public void setRaadbeurts(ArrayList<Raadbeurt> raadbeurts) {
        this.raadbeurts = raadbeurts;
    }

    public Word getWoord() {
        return woord;
    }

    public List<Raadbeurt> getRaadbeurts() {
        return raadbeurts;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
