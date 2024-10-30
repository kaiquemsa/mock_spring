package br.com.valueprojects.mock_spring.service;

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
        List<Game> games = gameRepository.getGamesFromLastWeek();

        for (Game game : games) {
            game.finalizeGame();
            gameRepository.save(game);

            smsService.enviarSms(null, "Parabéns " + game.getWinner() + ", você venceu!");
        }
    }
}
