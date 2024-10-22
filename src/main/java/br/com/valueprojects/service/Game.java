package br.com.valueprojects.service;

import java.time.LocalDate;

public class Game {
    private int id;
    private String teamA;
    private String teamB;
    private String winner;
    private LocalDate date;

    public Game(int id, String teamA, String teamB, String winner, LocalDate date) {
        this.id = id;
        this.teamA = teamA;
        this.teamB = teamB;
        this.winner = winner;
        this.date = date;
    }

    public String getWinner() {
        return winner;
    }

    public LocalDate getDate() {
        return date;
    }

    public void finalizeGame() {
        System.out.println("Jogo finalizado! Vencedor: " + winner);
    }
}
