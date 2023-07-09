package com.boomcat.boomcat.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class Game {
    /**
     * a specific game
     * made by random sequence by start-time and type
     */
    private String gameID;
    // who plays this game and their rank
    private ArrayList<User>participants;
    //current alive users' nums
    private Integer aliveNumber;
    private Date time;
    //types
    private enum type{Two_two_two,Six,Friend};
    //cards
    private ArrayList<Card>cards;
    //use two-point ptr to take a card
    private Integer tail;
    private Integer front;
    private boolean clockwise;

}
