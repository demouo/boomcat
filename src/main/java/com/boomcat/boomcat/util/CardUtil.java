package com.boomcat.boomcat.util;

import com.boomcat.boomcat.domain.Card;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Math.random;

public class CardUtil {

    public static void exchangeOnEvenIndex(ArrayList<Card> cards){
        int length=cards.size();
        Card tmp;
        for (int i = length-1; i >length/2; i--) {
            if(i%2==0){
                tmp= cards.get(length-1-i);
                cards.set(length-1-i, cards.get(i));
                cards.set(i, tmp);
            }
        }
    }
    public static void shuffle(ArrayList<Card> cards,int begin,int end) {
        Card tmp;
//        System.out.println("洗牌前：");
//        System.out.println(cards);
        for (int i = end-1; i >=begin; i--) {
            int pos = (int)(random() * (i+1-begin)+begin);
            tmp= cards.get(pos);
            cards.set(pos, cards.get(i));
            cards.set(i, tmp);
        }
//        System.out.println("洗牌后：");
//        System.out.println(cards);
    }

    public static ArrayList<Card> generate(int players){
        ArrayList<Card>cards = new ArrayList<>();
        switch (players){
            case 2->{
                Card[]_cards={
                        Card.ASKFOR,Card.ASKFOR,
                        Card.ASSIGN,Card.ASSIGN,
                        Card.BOTTOM,Card.BOTTOM,
                        Card.ASSIGNTWICE,
                        Card.PASS,Card.PASS,
                        Card.PREDICT,Card.PREDICT,
                        Card.REVERSE,Card.REVERSE,
                        Card.SEE,Card.SEE,
                        Card.SHUFFLE, Card.SHUFFLE,
                        Card.REMOVE,
                };
                cards= new ArrayList<>(Arrays.asList(_cards));
            }
            case 6->{
                Card[]_cards={
                        Card.ASKFOR,Card.ASKFOR,Card.ASKFOR,Card.ASKFOR,Card.ASKFOR,
                        Card.ASSIGN,Card.ASSIGN,Card.ASSIGN,Card.ASSIGN,Card.ASSIGN,Card.ASSIGN,Card.ASSIGN,
                        Card.BOTTOM,Card.BOTTOM,Card.BOTTOM,Card.BOTTOM,Card.BOTTOM,Card.BOTTOM,
                        Card.ASSIGNTWICE,Card.ASSIGNTWICE,Card.ASSIGNTWICE,Card.ASSIGNTWICE,
                        Card.EXCHANGE,Card.EXCHANGE,
                        Card.PASS,Card.PASS,Card.PASS,Card.PASS,Card.PASS,Card.PASS,
                        Card.PREDICT,Card.PREDICT,Card.PREDICT,Card.PREDICT,Card.PREDICT,
                        Card.REVERSE,Card.REVERSE,Card.REVERSE,Card.REVERSE,Card.REVERSE,Card.REVERSE,
                        Card.SEE,Card.SEE,Card.SEE,Card.SEE,Card.SEE,
                        Card.SHUFFLE, Card.SHUFFLE, Card.SHUFFLE,Card.SHUFFLE,
                        Card.REMOVE,Card.REMOVE,Card.REMOVE,
                };
                cards= new ArrayList<>(Arrays.asList(_cards));
            }
        }

        int size=cards.size();
        //first, make mess but orderly mess. then shuffle
        exchangeOnEvenIndex(cards);
        shuffle(cards,0,size);
        return cards;
    }
}
