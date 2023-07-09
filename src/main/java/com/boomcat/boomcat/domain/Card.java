package com.boomcat.boomcat.domain;

import lombok.Data;

public enum Card {
    REMOVE(0,"拆除"),
    //boom is coming
    BOOM(1,"炸弹"),
    //pass to next one
    PASS(2,"跳过"),
    //pre-see the nearest boom's order from top
    PREDICT(3,"预言"),
    //see-top3
    SEE(4,"透视"),
    //reverse the order
    REVERSE(5,"转向"),
    //choose to assign to others
    ASSIGN(6,"甩锅x1"),
    ASSIGNTWICE(7,"甩锅x2"),
    //the best dog-card
    EXCHANGE(8,"交换"),
    //take from bottom
    BOTTOM(9,"抽底"),
    //ask for only one card from others
    ASKFOR(10,"索要"),
    //shuffle the card
    SHUFFLE(11,"洗牌");

    Card(int type,String description) {
    this.type=type;
    this.description = description;
    }
    private int type;
    private String description;

}
