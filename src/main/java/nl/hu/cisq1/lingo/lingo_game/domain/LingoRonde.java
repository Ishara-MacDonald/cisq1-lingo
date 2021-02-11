package nl.hu.cisq1.lingo.lingo_game.domain;

import nl.hu.cisq1.lingo.words.domain.Word;

import java.util.ArrayList;


public class LingoRonde {
    private boolean voltooid;
    private ArrayList<Character> revealedWord;
    private Word woord;
    private int punten;
    private ArrayList<Raadbeurt>raadbeurts;
    private int countTries(){return  raadbeurts.size();}
    public LingoRonde(Word woord){
        this.woord=woord;
        punten=0;
        raadbeurts=new ArrayList<>();
        voltooid=false;
        revealedWord=new ArrayList<>();
    }

    public int berekenPunten(){
        int total=5*(5- raadbeurts.size()+5);
        return  total;
    }

    public void addRaadBeurt(Word woord1){
        if (countTries()!=5||!voltooid){
            Raadbeurt raadbeurt=new Raadbeurt(woord1.getValue());
            raadbeurt.compare(woord);
            raadbeurts.add(raadbeurt);
        }


        if (countTries()!=5||revealedWord.toString()==woord.getValue()){voltooid=true;


        revealedWord=calcWord();


        }

    }
    private ArrayList<Character>calcWord(){
        ArrayList<Character> woord1=new ArrayList<>();
        for (int i = 0; i < woord.getLength() ; i++) {
            ArrayList<Resultaat>resultaten=new ArrayList<>();
            for (Raadbeurt raadbeurt:raadbeurts) {
                Resultaat resultaat1= raadbeurt.getRespons().get(i);
                resultaten.add(resultaat1);

            }
            if (resultaten.contains(Resultaat.CORRECT)){
                woord1.add(woord.getValue().charAt(i));
            }
            else {
                woord1.add(' ');}
        }


        return woord1;
    }

    public Word getWoord() {
        return woord;
    }

    public int getPunten() {
        return punten;
    }

    public ArrayList<Raadbeurt> getRaadbeurts() {
        return raadbeurts;
    }

    public ArrayList<Character> getRevealedWord() {
        return revealedWord;
    }
}
