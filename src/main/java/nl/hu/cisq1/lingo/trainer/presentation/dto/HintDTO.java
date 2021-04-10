package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.Hint;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

public class HintDTO extends RepresentationModel<HintDTO> {
    List<String> hints;

    public HintDTO(List<String> hints){
        this.hints = hints;
    }

    public HintDTO(Hint hint){;
        hints = hint.getHints();
    }

}
