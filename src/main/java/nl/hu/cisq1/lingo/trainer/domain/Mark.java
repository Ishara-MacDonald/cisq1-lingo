package nl.hu.cisq1.lingo.trainer.domain;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public enum Mark implements Serializable {
    CORRECT("CORRECT"),
    PRESENT("PRESENT"),
    ABSENT("ABSENT"),
    INVALID("INVALID");

    private final String markName;

    Mark(String markName){
        this.markName = markName;
    }
}
