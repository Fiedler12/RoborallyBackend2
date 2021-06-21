package com.example.demo.service.implementations;

import com.example.demo.dal.interfaces.IBoardDao;
import com.example.demo.exceptions.DaoException;
import com.example.demo.exceptions.ServiceException;
import com.example.demo.model.Admin.Game;
import com.example.demo.model.Admin.User;
import com.example.demo.model.Board;
import com.example.demo.model.Player;
import com.example.demo.service.interfaces.IGameAdminService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class GameAdminService implements IGameAdminService {
    private final IBoardDao boardDao;
    private final GameService gameService;

    public GameAdminService(IBoardDao boardDao, GameService gameService) {
        this.boardDao = boardDao;
        this.gameService = gameService;
    }


    @Override
    public List<Game> getGames() throws DaoException, ServiceException {
        List<Game> result = new ArrayList<>();
        for (Board board: boardDao.getBoards()) {
            Game game = new Game();
            game.name = board.boardName;
            game.id = board.getGameId();
            result.add(game);
            game.started = board.getPlayersNumber() > 1;

            for (int i = 0; i < board.getPlayersNumber(); i++) {
                Player player = board.getPlayer(i);
                User user = new User();
                user.playerId = player.getPlayerId();
                user.playerName = player.getName();
                game.users.add(user);
            }
        }
        return result;
    }

    public void createGame() throws ServiceException, DaoException {
        Board board = new Board(8, 8, "board " + getGames().size() + 1);
        String[] colors = new String[2];
        colors[0] = "red";
        colors[1] = "blue";
        for (int i = 0; i < 2; i++) {
            Player player = new Player(board, colors[i], "player " + board.getPlayersNumber() + 1);
            board.addPlayer(player);
        }
        gameService.saveBoard(board);
    }
}
