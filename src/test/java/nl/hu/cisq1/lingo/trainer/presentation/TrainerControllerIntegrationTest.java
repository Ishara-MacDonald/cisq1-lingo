package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.function.Predicate;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TrainerControllerIntegrationTest {
    private static final Predicate<String> WORD_EXISTS = x -> true;

    @MockBean
    private SpringWordRepository wordRepository;
    @MockBean
    private SpringGameRepository gameRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("start a new game")
    void startNewGame() throws Exception{
        when(wordRepository.findRandomWordByLength(5))
                .thenReturn(Optional.of(new Word("baard")));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/trainer/games");

        String[] expectedHint = {"B", ".", ".", ".", "."};

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect( jsonPath("$.gameStatus", is("PLAYING")))
                .andExpect(jsonPath("$.hint", hasSize(5)))
                .andExpect(jsonPath("$.attemptsLeft", is(5)))
                .andExpect(jsonPath("$.feedbackHistory", hasSize(0)));
    }

}