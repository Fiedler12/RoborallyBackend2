package com.example.demo.dal.implementations;

import com.example.demo.controller.GameController.GameController;
import com.example.demo.dal.interfaces.IGameDao;
import com.example.demo.exceptions.DaoException;
import com.example.demo.model.Board;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;

@Repository
public class GameDao implements IGameDao {

    @Override
    public Collection<Board> getGames() throws DaoException {
        return BoardDao.boards.values();
    }
}
