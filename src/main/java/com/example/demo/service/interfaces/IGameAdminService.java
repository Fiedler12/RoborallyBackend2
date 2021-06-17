package com.example.demo.service.interfaces;

import com.example.demo.exceptions.DaoException;
import com.example.demo.exceptions.ServiceException;
import com.example.demo.model.Admin.Game;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IGameAdminService {

    List<Game> getGames() throws DaoException, ServiceException;

}
