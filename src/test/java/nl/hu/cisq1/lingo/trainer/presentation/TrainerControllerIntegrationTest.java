package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.data.SpringGameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.words.data.SpringWordRepository;
import nl.hu.cisq1.lingo.words.domain.Word;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.function.Predicate;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TrainerControllerIntegrationTest {
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
                .andExpect(jsonPath("$.gameStatus", is("PLAYING")))
                .andExpect(jsonPath("$.hint", hasSize(5)))
                .andExpect(jsonPath("$.hint", containsInRelativeOrder(expectedHint)))
                .andExpect(jsonPath("$.attemptsLeft", is(5)))
                .andExpect(jsonPath("$.roundFeedback", hasSize(0)));
    }

    @Test
    @DisplayName("start a new round")
    void startNewRound() throws Exception{
        Game game = new Game();
        game.startNewRound("baard");
        game.guess("baard");

        when(gameRepository.findById(anyLong()))
                .thenReturn(Optional.of(game));
        when(wordRepository.findRandomWordByLength(6))
                .thenReturn(Optional.of(new Word("hoeden")));

        RequestBuilder request = MockMvcRequestBuilders.post("/trainer/games/0/newRound");

        String[] expectedHint = {"H", ".", ".", ".", ".", "."};

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameStatus", is("PLAYING")))
                .andExpect(jsonPath("$.hint", hasSize(6)))
                .andExpect(jsonPath("$.hint", containsInRelativeOrder(expectedHint)))
                .andExpect(jsonPath("$.attemptsLeft", is(5)))
                .andExpect(jsonPath("$.roundFeedback", hasSize(0)));
    }

    @Test
    @DisplayName("cannot start new round if game not found")
    void cannotStartRound() throws Exception{
        RequestBuilder request = MockMvcRequestBuilders
                .post("/trainer/games/0/round");

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("cannot start new round if still playing")
    void cannotStartRoundWhenPlaying() throws Exception{
        Game game = new Game();
        game.startNewRound("baard");

        when(gameRepository.findById(anyLong()))
                .thenReturn(Optional.of(game));
        when(wordRepository.findRandomWordByLength(6))
                .thenReturn(Optional.of(new Word("hoeden")));

        RequestBuilder request = MockMvcRequestBuilders.post("/trainer/games/0/newRound");

        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("taking a guess")
    void guess() throws Exception{
        Game game = new Game();
        game.startNewRound("baard");

        when(gameRepository.findById(anyLong()))
                .thenReturn(Optional.of(game));
        when(wordRepository.findRandomWordByLength(5))
                .thenReturn(Optional.of(new Word("baard")));

        RequestBuilder requestGuess = MockMvcRequestBuilders.post("/trainer/games/0/boord");

        String[] expectedHints = {"B",".",".","R","D"};

        mockMvc.perform(requestGuess)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gameStatus", is("PLAYING")))
                .andExpect(jsonPath("$.hint", hasSize(5)))
                .andExpect(jsonPath("$.hint", containsInRelativeOrder(expectedHints)))
                .andExpect(jsonPath("$.attemptsLeft", is(4)))
                .andExpect(jsonPath("$.roundFeedback", hasSize(1)));
    }

    @Test
    @DisplayName("cannot take a guess when player is eliminated")
    void cannotGuessWhenEliminated() throws Exception{
        Game game = new Game();
        game.setMaxAttempts(2);
        game.startNewRound("baard");
        game.guess("boord");
        game.guess("boord");

        when(gameRepository.findById(anyLong()))
                .thenReturn(Optional.of(game));

        RequestBuilder requestGuess = MockMvcRequestBuilders.post("/trainer/games/0/boord");

        mockMvc.perform(requestGuess)
                .andExpect(status().isBadRequest());
    }
}