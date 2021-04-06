package nl.hu.cisq1.lingo.trainer.domain;

public enum Mark {
    CORRECT("CORRECT"),
    PRESENT("PRESENT"),
    ABSENT("ABSENT"),
    INVALID("INVALID");

    private final String markName;

    Mark(String markName){
        this.markName = markName;
    }

    public String toString(){
        return this.markName;
    }
}
