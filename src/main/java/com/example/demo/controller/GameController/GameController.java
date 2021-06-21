package com.example.demo.controller.GameController;

import com.example.demo.dal.implementations.BoardDao;
import com.example.demo.dal.implementations.GameDao;
import com.example.demo.exceptions.DaoException;
import com.example.demo.exceptions.MappingException;
import com.example.demo.exceptions.ServiceException;
import com.example.demo.model.Board;
import com.example.demo.model.Player;
import com.example.demo.model.Space;
import com.example.demo.service.implementations.GameAdminService;
import com.example.demo.service.interfaces.IGameService;
import com.example.demo.util.mapping.DtoMapper;
import com.example.demo.util.mapping.IDtoMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GameController {
    private final IGameService gameService;
    private final IDtoMapper dtoMapper;
    private final GameAdminService gameAdminService;

    public GameController(IGameService gameService, IDtoMapper dtoMapper, GameAdminService gameAdminService) {
        this.gameAdminService = gameAdminService;
        this.gameService = gameService;
        this.dtoMapper = dtoMapper;
    }

    /**
     * Endpoint for getting board information
     * @param boardId the id of the board we want to get
     * @return the board with the associated boardId we provided
     */
    @GetMapping("/board/{boardId}")
    public ResponseEntity<BoardDto> getBoard(@PathVariable("boardId") int boardId) throws ServiceException, MappingException, DaoException {
        Board board = gameService.getBoard(boardId);
        return new ResponseEntity<>(dtoMapper.convertToDto(board), HttpStatus.OK);
    }


    /**
     * Get current player of a board
     * @param boardId The board we want to get the current player from
     * @return Current player
     */
    @GetMapping("/board/{boardId}/currentPlayer")
    public ResponseEntity<PlayerDto> getCurrentPlayer(@PathVariable("boardId") int boardId) throws ServiceException, MappingException, DaoException {
        Player currentPlayer = gameService.getCurrentPlayer(boardId);
        return new ResponseEntity<>(dtoMapper.convertToDto(currentPlayer), HttpStatus.OK);
    }

    /**
     * Add a player to a board
     *
     * @param boardId   the id of the board we want to add a player to
     * @param playerDto the player we want to add to the board
     * @return the id of the player we have added
     */
    @PostMapping("/board/{boardId}/player")
    public ResponseEntity<Integer> addPlayer(@PathVariable("boardId") int boardId, @RequestBody PlayerDto playerDto) throws ServiceException, MappingException, DaoException {
        Board board = gameService.getBoard(boardId);
        Player player = dtoMapper.convertToEntity(playerDto, board);
        int playerId = gameService.addPlayer(boardId, player);
        return new ResponseEntity<>(playerId, HttpStatus.CREATED);
    }

    /**
     * Endpoint for creating a new board
     *
     * @param boardDTO, a board dto describing the board we want to create
     * @return id of the newly created board
     */
    @PostMapping("/board")
    public ResponseEntity<Integer> createBoard(@RequestBody BoardDto boardDTO, @RequestBody int playernumber) throws ServiceException, DaoException {
        Board board = dtoMapper.convertToEntity(boardDTO);
        int boardId = gameService.saveBoard(board);
        Player player1 = new Player(gameService.getBoard(boardId), "red", "player1");
        Player player2 = new Player(gameService.getBoard(boardId), "blue", "player2");
        gameService.addPlayer(boardId, player1);
        gameService.setCurrentPlayer(boardId, player1.getPlayerId());
        gameService.moveCurrentPlayer(boardId, 1,1);
        gameService.addPlayer(boardId, player2);
        gameService.addPlayer(boardId, player2);
        gameService.movePlayer(board, 4,4, player2.getPlayerId());
        return new ResponseEntity<>(boardId, HttpStatus.CREATED);
    }

    @PostMapping("/newgame")
    public ResponseEntity<Void> createGame() throws ServiceException, DaoException {
        gameAdminService.createGame();
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Move the current player
     *
     * @param boardId  the board on which we want to move the current player
     * @param spaceDto the space we want to move the player upon
     * @return Staus code indicating whether it went well or not
     */
    @PutMapping("/board/{boardId}/move")
    public ResponseEntity<Void> moveCurrentPlayer(@PathVariable("boardId") int boardId, @RequestBody SpaceDto spaceDto) throws ServiceException, DaoException {
        Board board = gameService.getBoard(boardId);
        Space space = dtoMapper.convertToEntity(spaceDto, board);
        gameService.moveCurrentPlayer(boardId, space.x, space.y);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Set the current player
     *
     * @param boardId  the board we are working upon
     * @param playerId the player we want to set as the current player
     * @return nothing
     */
    @PutMapping("/board/{boardId}/currentPlayer/{playerId}")
    public ResponseEntity<Void> setCurrentPlayer(@PathVariable("boardId") int boardId, @PathVariable("playerId") int playerId) throws ServiceException, DaoException {
        gameService.setCurrentPlayer(boardId, playerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Switch to the next player
     *
     * @param boardId the board we are operating upon
     * @return nothing
     */
    @PutMapping("/board/{boardId}/switchplayer")
    public ResponseEntity<Void> switchPlayer(@PathVariable("boardId") int boardId) throws ServiceException, DaoException {
        gameService.switchCurrentPlayer(boardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
