package br.com.valueprojects.mock_spring.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;

import org.junit.jupiter.api.Test;

import br.com.valueprojects.mock_spring.builder.*;
import br.com.valueprojects.mock_spring.model.*;
import br.com.valueprojects.mock_spring.service.SmsService;
import infra.JogoDao;
import infra.JogoDaoFalso;

public class JogoTest {

	@Test
	public void deveEnviarSmsAoVencedorApósFinalizarESalvarJogoDaSemanaAnteriorESalvarVencedor() {
		Calendar antiga = Calendar.getInstance();
		antiga.set(2022, 3, 28);

		Jogo jogo = new CriadorDeJogo().para("Squid Game").naData(antiga).constroi();

		jogo.anota(new Resultado(new Participante("Bill Gates"), 1500));
		jogo.anota(new Resultado(new Participante("Marta 10"), 5000));
		jogo.anota(new Resultado(new Participante("Ayrton Sena"), 20000));

		JogoDao jogoDaoFalso = mock(JogoDao.class);

		FinalizaJogo finalizador = new FinalizaJogo(jogoDaoFalso);

		finalizador.finaliza();

		jogoDaoFalso.salva(jogo);
		verify(jogoDaoFalso, times(1)).salva(jogo);

		Juiz juiz = new Juiz();
		juiz.julga(jogo);

		Participante vencedor = juiz.getTresMaiores().get(0).getParticipante();

		Participante participanteMock = mock(Participante.class);
		when(participanteMock.getNome()).thenReturn("Participante 1");
		when(participanteMock.getNumeroDeTelefone()).thenReturn("+123456789");

		SmsService smsServiceMock = mock(SmsService.class);
		String mensagem = "Parabéns, você é o vencedor!";
		smsServiceMock.enviarSms(participanteMock, mensagem);
		verify(smsServiceMock, times(1)).enviarSms(participanteMock, mensagem);
	}

	@Test
	public void deveTerJogoComUnicoParticipante() {
		Jogo jogo = new Jogo("Jogo de corrida");
		assertEquals(0, jogo.getResultados().size());

		jogo.anota(new Resultado(new Participante("Leonardo"), 150));

		assertEquals(1, jogo.getResultados().size());

		assertEquals(150.0, jogo.getResultados().get(0).getMetrica(), 0.00001);
	}

	@Test
	public void deveTerVariosResultados() {
		Jogo jogo = new CriadorDeJogo()
				.para("Cata moedas")
				.resultado(new Participante("Nelson"), 150.0)
				.resultado(new Participante("Pedro"), 200.0)
				.constroi();

		assertEquals(2, jogo.getResultados().size());
		assertEquals(150.0, jogo.getResultados().get(0).getMetrica(), 0.00001);
		assertEquals(200.0, jogo.getResultados().get(1).getMetrica(), 0.00001);

	}

	@Test
	public void naoDeveAceitarDoisResultadosDoMesmoParticipante() {

		Jogo jogo = new Jogo("Ca�a pe�as");
		Participante leonardo = new Participante("Leonardo");

		jogo.anota(new Resultado(leonardo, 500.0));
		jogo.anota(new Resultado(leonardo, 600.0));

		assertEquals(1, jogo.getResultados().size());
		assertEquals(500, jogo.getResultados().get(0).getMetrica(), 0.00001);
	}

}
