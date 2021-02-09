package nl.hu.cisq1.lingo.lingo_game.domain;

import nl.hu.cisq1.lingo.words.domain.Word;
import org.apache.coyote.Request;

import java.util.ArrayList;

public class Raadbeurt {
    public Raadbeurt(String ingev_woord){
        this.ingev_woord=ingev_woord;
        respons=null;
        Word woord=new Word(ingev_woord);
        compare(woord);
    }

    private String ingev_woord ;
    private ArrayList<Resultaat> respons;

    private void compare(Word woord){
            if (woord.getLength()!=ingev_woord.length()){

                for (int i = 0; i <ingev_woord.length()-1 ; i++) {
                    respons.add(Resultaat.INVALID);

                }
            }
            else{

                for (int i = 0; i <ingev_woord.length()-1 ; i++) {
                    char character=ingev_woord.charAt(i);

                    if(woord.getValue().contains(String.valueOf(character))){

                        if(character==woord.getValue().charAt(i)){
                            respons.set(i,Resultaat.CORRECT);
                        }

                        else  {respons.set(i,Resultaat.PRESENT);}
                    }

                    else {respons.set(i,Resultaat.ABSENT);}
                }
            }
    }

    public ArrayList<Resultaat> getRespons() {
        return respons;
    }

    public String getIngev_woord() {
        return ingev_woord;
    }
}
