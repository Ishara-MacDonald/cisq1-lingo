package nl.hu.cisq1.lingo.lingo_game.domain;

import nl.hu.cisq1.lingo.words.domain.Word;

import java.util.ArrayList;

public class LingoRonde {
    private boolean voltooid;
    private Word revealedWord;
    private Word woord;
    private int punten;
    private ArrayList<Raadbeurt>raadbeurts;
    private int countTries(){return  raadbeurts.size();}

    public int berekenPunten(){
        int total=5*(5-berekenPunten()+5);
        return  total;

    }

    public void addRaadBeurt(Word woord1){
        if (countTries()!=5||!voltooid){raadbeurts.add(new Raadbeurt(woord1.getValue()));
        }
        if (countTries()!=5){voltooid=true; }

    }
    public ArrayList<Character>calcWord(){
        ArrayList<Character> woord1=null;
        for (int i = 0; i < woord.getLength()-1 ; i++) {
            ArrayList<Resultaat>resultaten=new ArrayList<>();
            for (Raadbeurt raadbeurt:raadbeurts) {
                Resultaat resultaat1= raadbeurt.getRespons().get(i);
                resultaten.add(resultaat1);

            }
            if (resultaten.contains(Resultaat.CORRECT)){
                woord1.set(i,woord.getValue().charAt(i));
            }
            else {woord1.set(i,' ');}

        }


        return woord1;
    }






}
