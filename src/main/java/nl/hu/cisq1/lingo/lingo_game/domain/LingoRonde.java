package nl.hu.cisq1.lingo.lingo_game.domain;

import nl.hu.cisq1.lingo.words.domain.Word;

import java.util.ArrayList;


public class LingoRonde {
    private Word woord;
    private ArrayList<Raadbeurt>raadbeurts;



    public LingoRonde(Word woord){
        this.woord=woord;
        raadbeurts=new ArrayList<>();
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
            raadbeurt.compare(woord);
            raadbeurts.add(raadbeurt);
        }

    }
    public ArrayList<Character>calcWord(){
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

        woord1.set(0,woord.getValue().charAt(0));
        return woord1;
    }

    public Word getWoord() {
        return woord;
    }

    public ArrayList<Raadbeurt> getRaadbeurts() {
        return raadbeurts;
    }

}
