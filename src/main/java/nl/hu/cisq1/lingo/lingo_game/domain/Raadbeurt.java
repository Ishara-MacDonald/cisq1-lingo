package nl.hu.cisq1.lingo.lingo_game.domain;

import nl.hu.cisq1.lingo.words.domain.Word;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity(name = "raadbeurt")
public class Raadbeurt {

    @Column(name = "woord")
    private String ingev_woord ;
    private ArrayList<Mark> respons;
    @Id
    private Long id;

    public Raadbeurt(String ingev_woord){
        this.ingev_woord=ingev_woord;
        respons=new ArrayList<>();
        Word woord=new Word(ingev_woord);
        //compare(woord);
    }

    public Raadbeurt() {

    }


    public ArrayList<Mark> compare(Word woord){
            if (woord.getLength()!=ingev_woord.length()){

                for (int i = 0; i <ingev_woord.length() ; i++) {
                    respons.add(Mark.INVALID);

                }
            }
            else{
                //System.out.println(ingev_woord);
                //System.out.println(woord.getValue());

                for (int i = 0; i <ingev_woord.length() ; i++) {
                    //System.out.println(i);
                    char character=ingev_woord.charAt(i);

                    if(woord.getValue().contains(String.valueOf(character))){

                        if(character==woord.getValue().charAt(i)){
                            respons.add(Mark.CORRECT);
                        }

                        else  {respons.add(Mark.PRESENT);}
                    }

                    else {respons.add(Mark.ABSENT);}
                }
            } return  respons;
    }

    public ArrayList<Mark> getRespons() {
        return respons;
    }

    public String getIngev_woord() {
        return ingev_woord;
    }

    public void setIngev_woord(String ingev_woord) {
        this.ingev_woord = ingev_woord;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
