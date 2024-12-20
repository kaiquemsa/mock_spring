package br.com.valueprojects.repository;

import java.util.List;

import br.com.valueprojects.mock_spring.service.Game;

public interface GameRepository {
    List<Game> getGamesFromLastWeek();

    void save(Game game);
}
