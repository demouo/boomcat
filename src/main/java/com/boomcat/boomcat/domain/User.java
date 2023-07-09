package com.boomcat.boomcat.domain;

import lombok.Data;

import java.util.ArrayList;

@Data
public class User {
    //user's name online
    private String nickname;
    //user's account
    private String phoneNumber;
    //user's face online
    private String avatar;
    /**
     * two sates: alive and dead
     */
    private boolean alive;
    // user's cards
    private ArrayList<Card>cards;

    public User(String nickname){
        this.nickname=nickname;
        alive=true;
    }
}
