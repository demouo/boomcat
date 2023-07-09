package com.boomcat.boomcat.service;

import com.boomcat.boomcat.domain.Card;
import com.boomcat.boomcat.domain.Game;
import com.boomcat.boomcat.domain.User;

import java.util.ArrayList;

public interface GameService {
    Game startGame(String type, ArrayList<User>users, ArrayList<Card>cards,int boomNumber,int initHoldNumber);

}
