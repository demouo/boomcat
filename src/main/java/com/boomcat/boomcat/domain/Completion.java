package com.boomcat.boomcat.domain;

import lombok.Data;

@Data
public class Completion {

    //for which user
    private String phoneNumber;
    //records of all games by gameID
    private Integer totalGames;
    //records of loser games by gameID
    private Integer loserGames;

}
