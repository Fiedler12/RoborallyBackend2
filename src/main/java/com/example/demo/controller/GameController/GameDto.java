package com.example.demo.controller.GameController;

import com.example.demo.dal.implementations.GameDao;
import com.example.demo.model.Board;

import java.util.Collection;

public class GameDto {
    private Collection<Board> games;
    private int numberOfGames;

    public Collection<Board> getGames() {
        return games;
    }

    public void setGames(Collection<Board> games) {
        this.games = games;
    }

    public int getNumberOfGames() {
        return numberOfGames;
    }

    public void setNumberOfGames(int numberOfGames) {
        this.numberOfGames = numberOfGames;
    }
}
