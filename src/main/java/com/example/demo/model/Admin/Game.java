package com.example.demo.model.Admin;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class Game {

    public String name;

    public int id;

    public boolean started;

    public List<User> users = new ArrayList<>();

}
