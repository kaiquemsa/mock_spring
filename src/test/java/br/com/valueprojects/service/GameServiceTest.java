package br.com.valueprojects.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

import br.com.valueprojects.repository.GameRepository;

public class GameServiceTest {
    private GameService gameService;
    private SmsService smsServiceMock;
    private GameRepository gameRepositoryMock;

    @BeforeEach
    void setUp() {
        smsServiceMock = mock(SmsService.class);
        gameRepositoryMock = mock(GameRepository.class);
        gameService = new GameService(gameRepositoryMock, smsServiceMock);
    }

    @Test
    void shouldFinalizeAndSendSmsWhenGamesOfLastWeekAreProcessed() {
        // Arrange: Cria um jogo que foi realizado na semana passada
        List<Game> gamesFromLastWeek = Arrays.asList(
                new Game(1, "Team A", "Team B", "Team A", LocalDate.now().minusDays(7)));

        when(gameRepositoryMock.getGamesFromLastWeek()).thenReturn(gamesFromLastWeek);

        // Act: Finaliza os jogos e efetua o pagamento
        gameService.finalizeGamesOfLastWeek();

        // Assert: Verifica se o jogo foi salvo e o SMS foi enviado
        verify(gameRepositoryMock, times(1)).save(any(Game.class));
        verify(smsServiceMock, times(1)).send(anyString());

        // Verifica que o SMS foi enviado APENAS depois do salvamento
        InOrder inOrder = inOrder(gameRepositoryMock, smsServiceMock);
        inOrder.verify(gameRepositoryMock).save(any(Game.class));
        inOrder.verify(smsServiceMock).send(anyString());
    }

    @Test
    void shouldNotSendSmsIfSaveFails() {
        // Arrange: Simula um erro ao salvar o jogo
        List<Game> gamesFromLastWeek = Arrays.asList(
                new Game(1, "Team A", "Team B", "Team A", LocalDate.now().minusDays(7)));

        when(gameRepositoryMock.getGamesFromLastWeek()).thenReturn(gamesFromLastWeek);
        doThrow(new RuntimeException("Database Error")).when(gameRepositoryMock).save(any(Game.class));

        // Act & Assert: Finaliza os jogos e garante que o SMS não seja enviado
        assertThrows(RuntimeException.class, () -> gameService.finalizeGamesOfLastWeek());
        verify(gameRepositoryMock, times(1)).save(any(Game.class));
        verifyNoInteractions(smsServiceMock);
    }

}
