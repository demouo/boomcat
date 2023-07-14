package com.boomcat.boomcat.service;

import com.boomcat.boomcat.domain.Card;
import com.boomcat.boomcat.domain.Game;
import com.boomcat.boomcat.domain.User;

import java.util.ArrayList;

public interface GameService {
    Game initGame(String type, ArrayList<User>users);

}
