package com.example.demo.controller.GameController;

import com.example.demo.dal.implementations.GameDao;
import com.example.demo.model.Board;
import com.example.demo.model.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameDto {
    int id;
    public List<PlayerDto> players = new ArrayList<PlayerDto>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<PlayerDto> getPlayers() {
        return (ArrayList<PlayerDto>) players;
    }

    public void setPlayers(ArrayList<PlayerDto> players) {
        this.players = players;
    }
}
