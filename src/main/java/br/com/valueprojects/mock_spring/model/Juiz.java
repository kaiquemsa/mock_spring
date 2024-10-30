package br.com.valueprojects.mock_spring.model;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

public class Juiz {

	private double maisPontos = Double.NEGATIVE_INFINITY;
	private double menosPontos = Double.POSITIVE_INFINITY;
	private List<Resultado> tresMaiores = new ArrayList<>();

	public void julga(Jogo jogo) {
		if (jogo.getResultados().isEmpty()) {
			throw new RuntimeException("Sem resultados, não há julgamento!");
		}

		for (Resultado resultado : jogo.getResultados()) {
			if (resultado.getMetrica() > maisPontos) {
				maisPontos = resultado.getMetrica();
			}
			if (resultado.getMetrica() < menosPontos) {
				menosPontos = resultado.getMetrica();
			}
		}

		tresMaiores = jogo.getResultados().stream()
				.sorted(Comparator.comparingDouble(Resultado::getMetrica).reversed())
				.limit(3)
				.collect(Collectors.toList());
	}

	public double getPrimeiroColocado() {
		return maisPontos;
	}

	public double getUltimoColocado() {
		return menosPontos;
	}

	public List<Resultado> getTresMaiores() {
		return tresMaiores;
	}
}
