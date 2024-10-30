package br.com.valueprojects.mock_spring.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import br.com.valueprojects.mock_spring.builder.CriadorDeJogo;
import br.com.valueprojects.mock_spring.model.Jogo;
import br.com.valueprojects.mock_spring.model.Juiz;
import br.com.valueprojects.mock_spring.model.Participante;
import br.com.valueprojects.mock_spring.model.Resultado;
import java.util.Calendar;
import java.util.List;

public class JuizTest {

    @Test
    public void deveClassificarVencedorCorretamente() {
        Calendar data = Calendar.getInstance();
        data.set(2022, 3, 28);

        Jogo jogo = new CriadorDeJogo().para("Squid Game").naData(data).constroi();
        jogo.anota(new Resultado(new Participante("Bill Gates"), 1500));
        jogo.anota(new Resultado(new Participante("Marta 10"), 5000));
        jogo.anota(new Resultado(new Participante("Ayrton Senna"), 20000));

        Juiz juiz = new Juiz();
        juiz.julga(jogo); // Certifique-se de chamar julga para preencher tresMaiores

        Participante vencedor = juiz.getTresMaiores().get(0).getParticipante();
        assertEquals("Ayrton Senna", vencedor.getNome());
    }

    @Test
    public void deveListarOsTresMaioresParticipantes() {
        Calendar data = Calendar.getInstance();
        data.set(2022, 3, 28);

        Jogo jogo = new CriadorDeJogo().para("Squid Game").naData(data).constroi();
        jogo.anota(new Resultado(new Participante("Bill Gates"), 1500));
        jogo.anota(new Resultado(new Participante("Marta 10"), 5000));
        jogo.anota(new Resultado(new Participante("Ayrton Senna"), 20000));
        jogo.anota(new Resultado(new Participante("Participante Extra"), 1000));

        Juiz juiz = new Juiz();
        juiz.julga(jogo); // Chama julga antes de acessar tresMaiores

        List<Resultado> tresMaiores = juiz.getTresMaiores();
        assertEquals(3, tresMaiores.size());
        assertEquals("Ayrton Senna", tresMaiores.get(0).getParticipante().getNome());
        assertEquals("Marta 10", tresMaiores.get(1).getParticipante().getNome());
        assertEquals("Bill Gates", tresMaiores.get(2).getParticipante().getNome());
    }

}
