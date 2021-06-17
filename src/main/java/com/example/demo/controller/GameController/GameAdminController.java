package com.example.demo.controller.GameController;


import com.example.demo.exceptions.DaoException;
import com.example.demo.exceptions.MappingException;
import com.example.demo.exceptions.ServiceException;
import com.example.demo.model.Admin.Game;
import com.example.demo.model.Board;
import com.example.demo.service.interfaces.IGameAdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class GameAdminController {


    private final IGameAdminService gameAdminService;

    public GameAdminController(IGameAdminService gameAdminService) {
        this.gameAdminService = gameAdminService;
    }

    @GetMapping("/games")
    public ResponseEntity<List<Game>> getGames() throws ServiceException, DaoException, MappingException {
        List<Game> games = gameAdminService.getGames();
        return new ResponseEntity<>(games, HttpStatus.OK);
    }
}
