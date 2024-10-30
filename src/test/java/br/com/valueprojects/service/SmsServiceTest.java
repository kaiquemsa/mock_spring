// package br.com.valueprojects.mock_spring.service;

// import static org.mockito.Mockito.*;
// import static org.junit.jupiter.api.Assertions.*;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import br.com.valueprojects.mock_spring.model.Participante;

// public class SmsServiceTest {
// private SmsService smsService;
// private Participante participanteMock;

// @BeforeEach
// public void setUp() {
// smsService = mock(SmsService.class);
// participanteMock = mock(Participante.class);
// }

// @Test
// public void deveEnviarSmsComParticipanteCorreto() {
// when(participanteMock.getNome()).thenReturn("Participante 1");
// when(participanteMock.getNumeroDeTelefone()).thenReturn("+5511999999999");

// String mensagem = "Parabéns! Você é o vencedor!";
// smsService.enviarSms(participanteMock, mensagem);

// verify(smsService).enviarSms(participanteMock, mensagem);
// assertEquals("Participante 1", participanteMock.getNome());
// }

// @Test
// public void deveTratarParticipanteSemNome() {
// when(participanteMock.getNome()).thenReturn(null);
// when(participanteMock.getNumeroDeTelefone()).thenReturn(null);

// String mensagem = "Parabéns! Você é o vencedor!";
// smsService.enviarSms(participanteMock, mensagem);

// verify(smsService).enviarSms(participanteMock, mensagem);
// assertNull(participanteMock.getNome());
// }

// @Test
// public void deveEnviarSmsComMensagemCorreta() {
// when(participanteMock.getNome()).thenReturn("Participante 2");
// when(participanteMock.getNumeroDeTelefone()).thenReturn("+5511988888888");

// String mensagem = "Você alcançou o primeiro lugar!";
// smsService.enviarSms(participanteMock, mensagem);

// verify(smsService).enviarSms(participanteMock, mensagem);
// assertEquals("Você alcançou o primeiro lugar!", mensagem);
// }
// }
