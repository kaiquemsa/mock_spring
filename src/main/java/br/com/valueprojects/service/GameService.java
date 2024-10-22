package br.com.valueprojects.service;

import java.util.List;

import br.com.valueprojects.repository.GameRepository;

public class GameService {

    private final GameRepository gameRepository;
    private final SmsService smsService;

    public GameService(GameRepository gameRepository, SmsService smsService) {
        this.gameRepository = gameRepository;
        this.smsService = smsService;
    }

    public void finalizeGamesOfLastWeek() {
        // Busca os jogos da última semana
        List<Game> games = gameRepository.getGamesFromLastWeek();

        // Finaliza os jogos e salva no banco de dados
        for (Game game : games) {
            game.finalizeGame();
            gameRepository.save(game);

            // Envia o SMS ao vencedor após salvar
            smsService.send("Parabéns " + game.getWinner() + ", você venceu!");
        }
    }
}
