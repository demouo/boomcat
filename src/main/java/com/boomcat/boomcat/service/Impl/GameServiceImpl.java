package com.boomcat.boomcat.service.Impl;

import com.boomcat.boomcat.domain.Card;
import com.boomcat.boomcat.domain.Game;
import com.boomcat.boomcat.domain.User;
import com.boomcat.boomcat.service.GameService;
import com.boomcat.boomcat.util.CardUtil;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.Math.random;

//合作模式就是提示机制不一样 没有冲突
@Service
public class GameServiceImpl implements GameService {

    /**
     * 初始化
     * @param type
     * @param users
     * @return
     */
    @Override
    public Game initGame(String type, ArrayList<User> users) {
        System.out.println(type);
        //one totally contains "initHoldNumber" cards including the 'REMOVE' card
        int initHoldNumber=5;
        //booms will be changed by different gameType
        int boomNumber = 0;
        Game game = new Game();
        //clockwise default
        game.setClockwise(true);
        //new the cards
        int playerNumber= users.size();
        ArrayList<Card> cards = CardUtil.generate(playerNumber);
        int cnt=0;
        //specify the type of game
        //when initialing, there is no 'BOOM' cards and then add them after finishing
        switch (type) {
            case "1v1.."->{
                boomNumber=4;
                //everyone adds 5 cards
                for (User user: users) {
                    //everyone has a 'REMOVE' firstly
                    user.setCards(new ArrayList<>());
                    user.getCards().add(Card.REMOVE);
                    for (int i = 0; i < initHoldNumber-1; i++){
                            user.getCards().add(cards.get(cnt));
                            cnt++;
                    }
                }
                //show result for user
                for (int i = 0; i < users.size(); i++){
//                    System.out.println("第"+(i+1)+"个用户的牌面为：");
                    System.out.println("用户”"+users.get(i).getNickname()+"“的牌面为：");
                    System.out.println(users.get(i).getCards());
                    System.out.println("牌面重复率为");
                    Set<Card>cardSet=new HashSet<>();
                    for(Card card: users.get(i).getCards()){
                        cardSet.add(card);
                    }
                    System.out.println((float) (users.get(i).getCards().size()-cardSet.size())/ users.get(i).getCards().size());
                    System.out.println("");
                }
                //the left cards
                cards= new ArrayList<>(cards.subList(cnt,cards.size()));
//                System.out.println("检查截取子列是否准确，看数量就行，还剩几张牌：");
//                System.out.println(cards.size());

                //insert booms randomly
                for(int i = 0; i < boomNumber; i++){
                    cards.add((int)(random()*(cards.size()+1)),Card.BOOM);
                }

            }
        }
        System.out.println("待抽取的牌为："+cards);
        System.out.println("初始爆炸率为：");
        System.out.println((float) boomNumber/cards.size());
        game.setAliveNumber(users.size());
        game.setParticipants(users);
        game.setTail(cards.size()-1);
        game.setFront(0);
        game.setCards(cards);
        game.setTime(new Date());
        //gameID

        return game;

    }

    /**
     *
     * @param card use which card
     * @param game game
     * @param currentUserIndex who use
     * @param pointUserIndex who may be pointed
     */
    public void preHandleCard(Card card, Game game, int currentUserIndex, int pointUserIndex){
        Integer aliveNumber = game.getAliveNumber();
        Integer front = game.getFront();
        Integer tail = game.getTail();
        ArrayList<Card> cards = game.getCards();
        ArrayList<User> users = game.getParticipants();

        User currentUser = users.get(currentUserIndex);
        User pointedUser = users.get(pointUserIndex);

        switch (card){
            case PASS -> {}
            case REVERSE -> {game.setClockwise(!game.isClockwise());}

            case SEE -> {}
            case PREDICT -> {}

            case BOOM -> {}
            case ASKFOR -> {
                if(pointedUser.getCards().size()>0){
                    //go to give event

                }
                //current user continue  to do

            }
            case ASSIGN -> {
                //turn to the pointed user to do
            }
            case ASSIGNTWICE -> {
                //like the above one
            }
            case BOTTOM -> {
                Card tmp = cards.get(tail);
                if(Card.BOOM.equals(tmp)){
                    //go to boom event
                }else{
                    //add
                    currentUser.getCards().add(tmp);
                }
            }
            case REMOVE -> {
                //go to remove event
            }
            case SHUFFLE -> {
                CardUtil.shuffle(cards,front,tail);
            }
            case EXCHANGE -> {
                ArrayList<Card>tmp;
                tmp=currentUser.getCards();
                currentUser.setCards(pointedUser.getCards());
                pointedUser.setCards(tmp);
            }
        }
    };

    public void play(Game game){
        Integer aliveNumber = game.getAliveNumber();
        Integer front = game.getFront();
        Integer tail = game.getTail();
        ArrayList<Card> cards = game.getCards();
        ArrayList<User> users = game.getParticipants();

        int userNumber=users.size();
        //choose a user to start
        int userIndex=(int)(random()*userNumber);

        while (true){
            //current user alive
            if(users.get(userIndex).isAlive()){
                //take a card or use a card





            }

            if(game.isClockwise()){
                userIndex=(userIndex + 1)%userNumber;
            }else{
                userIndex=(userIndex - 1)%userNumber;
            }
        }




    }

    public static float boomRate(ArrayList<Card> cards,int begin,int end){
        int cnt = 0;
        for (int i=begin;i<=end;i++) {
            if (Card.BOOM.equals(cards.get(i)))cnt++;
        }
        return (float)cnt/(end-begin+1);
    }
    public static void showCards(ArrayList<Card> cards,int begin,int end){
        for (int i = begin; i <= end; i++) {
            if (i==end){
                System.out.print(cards.get(i)+"]\n");
                break;
            }
            if (i == begin) {
                System.out.print("["+cards.get(i)+",");
                continue;
            }
            System.out.print(cards.get(i)+",");
        }
    }
    public static void main(String[] args) {
        //how many people to play
        int playerNumber=2;
        //make users
        ArrayList<User>users = new ArrayList<>();
        for (int i = 0; i <playerNumber;i++ ) {
            User user = new User("用户"+(i+1));
            user.setCards(new ArrayList<>());
            users.add(user);
        }
        //makes cards
//        ArrayList<Card> cards = CardUtil.generate(playerNumber);
        System.out.println("本场是"+playerNumber+"人局");
//        System.out.println("总牌数："+cards.size());
        GameService gameService = new GameServiceImpl();
        //makes game
        final int boomNumber=3;
        final int initHoldNumber=5;
        Game game = gameService.initGame("1v1..", users);
        System.out.println("本局游戏参数："+game);
        //go to game
        Integer front = game.getFront();
        Integer tail = game.getTail();
        ArrayList<Card> cards = game.getCards();
        users= game.getParticipants();
        boolean clockwise = game.isClockwise();
        int userNumber=users.size();

        //random user to start the game
        int currIndex=(int)(random()*userNumber);
        System.out.println("游戏从"+users.get(currIndex).getNickname()+"开始");
        //for assign :shuai guo
        int times=0;
        //normal turn for clockwise
        boolean normalTurn=true;
        //for special turn
        int preNumber=0;
        //total times in game
        int totalCnt=0;
        //to imitate real persons
        Scanner scanner = new Scanner(System.in);
        //latest used card
        Card latestUsedCard = null;
        //when there is more than one user being alive
        while(game.getAliveNumber()>1){
            User currentUser = users.get(currIndex);
            if (!currentUser.isAlive()) {
                if(clockwise){
                    currIndex=(currIndex + 1)%userNumber;
                }else{
                    currIndex=(currIndex +userNumber-1)%userNumber;
                }
                continue;
            }
            totalCnt++;
            System.out.println("当前顺序为"+(clockwise?"顺时针":"逆时针"));
            System.out.println("本轮爆炸率为"+boomRate(cards,front,tail));
            System.out.println("上一张被出的牌是"+latestUsedCard);
            System.out.println("当前出牌用户是"+currentUser.getNickname());

//            //its choice randomly
//            int random=(int)(random()*100);
//            boolean chooseToTakeACard= random % 2 == 0;

             //real choice
            ArrayList<Card> yourCard = currentUser.getCards();
            System.out.println("您的牌面为"+yourCard);

            System.out.println("请输入您的选择（1:拿牌，2：出牌）");
            int choice = scanner.nextInt();
            boolean chooseToTakeACard=(choice==1);

            if(chooseToTakeACard){
                System.out.println("您选择了拿牌");
                //take a card
                Card currentCard = cards.get(front);
                System.out.println("拿到了一张"+currentCard+"牌");
                front++;
                //a boom
                if(Card.BOOM.equals(currentCard)){
                   if(currentUser.getCards().contains(Card.REMOVE)){
//                       System.out.println("他选择了拆除");
                       System.out.println("是否使用拆除牌？（1：是； 2：否）");
                       int useREMOVE = scanner.nextInt();
                       if (useREMOVE==1){
                           currentUser.getCards().remove(Card.REMOVE);
                           latestUsedCard=Card.REMOVE;
                       }else{
                           //dead
                           currentUser.setAlive(false);
                           //aliveNumber--
                           game.setAliveNumber(game.getAliveNumber()-1);
                           //random to place the boom
                           int pos=(int)(random()*(tail-front+2));
                           cards.add(pos+front,Card.BOOM);
                           continue;
                       }
                       //where to place the boom
//                       int pos=(int)(random()*(tail-front+2));
                       System.out.println("请选择炸弹放置的位置（第1、2、3、4、5、6（牌底）、7（随机））");
                       int pos=scanner.nextInt();
                       if(pos==6){
                           pos=tail-front+1;
                       }else if(pos==7){
                           pos=(int)(random()*(tail-front+2));
                       }else{
                           pos--;
                       }
                       cards.add(pos+front,Card.BOOM);
                       //when adding sth, the tail++
                       tail++;
                       System.out.println("炸弹放进了第"+(pos+1)+"张");
                       //show the left cards
                       System.out.println("当前总牌为：");
                       showCards(cards,front,tail);
                   }else{
                       System.out.println("您无拆除牌，"+currentUser.getNickname()+"（您）已出局");
                       //dead
                       currentUser.setAlive(false);
                       //aliveNumber--
                       game.setAliveNumber(game.getAliveNumber()-1);
                       //random to place the boom
                       int pos=(int)(random()*(tail-front+2));
                       cards.add(pos+front,Card.BOOM);
                       //when adding sth, the tail++
                       tail++;
                       System.out.println("炸弹放进了第"+(pos+1)+"张");
                       //show the left cards
                       System.out.println("当前总牌为：");
                       showCards(cards,front,tail);
                   }
                }else{
                    currentUser.getCards().add(currentCard);
                    System.out.println("您当前牌面为"+currentUser.getCards());
                }
            }else{
//                System.out.println("您选择了出牌");
                //use a card
                //choose a card
                System.out.println("请选择出第几张牌：");
                int cardIndex = scanner.nextInt()-1;
//                int cardIndex=(int)(random()*yourCard.size());
                Card usedCard = yourCard.get(cardIndex);
                while (usedCard.equals(Card.REMOVE)){
                    System.out.println("不能出REMOVE，请重新选择第几张：");
                    cardIndex = scanner.nextInt()-1;
//                    cardIndex=(int)(random()*yourCard.size());
                    usedCard = yourCard.get(cardIndex);
                }
                System.out.println("您出的牌是"+usedCard);
                latestUsedCard=usedCard;
                yourCard.remove(cardIndex);
                switch (usedCard){
                    case PASS -> {
                        latestUsedCard=Card.PASS;
                    }
                    case REVERSE -> {
                        clockwise=!clockwise;
                        latestUsedCard=Card.REVERSE;
                    }
                    case SEE -> {
                        int tmp=front;
                        Card[]see3Cards=new Card[3];
                        for (int i = 0; i < 3&&tmp<=tail; i++,tmp++) {
                            see3Cards[i]=cards.get(tmp);
                        }
                        System.out.println("前三张牌是："+Arrays.toString(see3Cards));
                        latestUsedCard=Card.SEE;
                        continue;
                    }
                    case PREDICT -> {
                        for(int i = front; i <= tail; i++){
                            if(cards.get(i) .equals(Card.BOOM)){
                                System.out.println("预言结果是：");
                                System.out.println("第"+(i-front+1)+"张");
                            break;
                            }
                        }
                        latestUsedCard=Card.PREDICT;
                        continue;
                    }
                    case ASKFOR -> {
                        //choose a pointed man not you
                        System.out.println("请选择索要的对象是第几个（共"+userNumber+"个）：");
                        int pointedIndex = scanner.nextInt()-1;
//                        int pointedIndex=(int)(random()*userNumber);
                        while (pointedIndex==currIndex||(!users.get(pointedIndex).isAlive())||users.get(pointedIndex).getCards().size() == 0){
//                            pointedIndex=(int)(random()*userNumber);
                            System.out.println("第"+(pointedIndex+1)+"个无法索取！请重新选择（共"+userNumber+"个）：");
                            pointedIndex = scanner.nextInt()-1;
                        }
                        User pointedUser = users.get(pointedIndex);
                        System.out.println("被索要的用户是"+pointedUser.getNickname());
                        ArrayList<Card> pointedCards = pointedUser.getCards();
                        //go to give event
                        //give 1st card default
                        int givenIndex=(int)(random()*pointedCards.size());
                        Card givenCard = pointedCards.get(givenIndex);
                        pointedCards.remove(givenIndex);
                        yourCard.add(givenCard);
//                        System.out.println(currentUser.getNickname()+"获得的牌是"+givenCard);
                        System.out.println("您获得的牌是"+givenCard);
                        System.out.println("您现在的牌面是"+yourCard);
                        latestUsedCard=Card.ASKFOR;
                        continue;
                    }
                    case ASSIGN -> {
                        //turn to the user to do
//                        currIndex=(int)(random()*userNumber);
                        System.out.println("请选择甩锅的对象是第几个（共"+userNumber+"个）：");
                        currIndex = scanner.nextInt()-1;
                        while (!users.get(currIndex).isAlive()){
                            System.out.println("第"+(currIndex+1)+"个无法甩锅！请重新选择（共"+userNumber+"个）：");
//                            currIndex=(int)(random()*userNumber);
                            currIndex = scanner.nextInt()-1;
                        }
                        System.out.println("被甩锅的用户是"+users.get(currIndex).getNickname());
                        if(preNumber>0){
                            times=preNumber+1;
                            preNumber=times;
                        }else{
                            preNumber=1;
                            times+=1;
                        }
                        normalTurn=false;
                        latestUsedCard=Card.ASSIGN;
                    }
                    case ASSIGNTWICE -> {
                        //like the above one
                        System.out.println("请选择甩锅的对象是第几个（共"+userNumber+"个）：");
                        currIndex = scanner.nextInt()-1;
                        while (!users.get(currIndex).isAlive()){
                            System.out.println("第"+(currIndex+1)+"个无法甩锅！请重新选择（共"+userNumber+"个）：");
                            currIndex = scanner.nextInt()-1;
                        }
                        System.out.println("被甩锅的用户是"+users.get(currIndex).getNickname());
                        if(preNumber>0){
                            times=preNumber+2;
                            preNumber=times;
                        }else{
                            preNumber=2;
                            times+=2;
                        }
                        normalTurn=false;
                        latestUsedCard=Card.ASSIGNTWICE;
                    }
                    case BOTTOM -> {
                        Card tmp = cards.get(tail);
                        tail--;
                        System.out.println("您抽到的牌为"+tmp);
                        if(Card.BOOM.equals(tmp)){
                            if(currentUser.getCards().contains(Card.REMOVE)){
//                       System.out.println("他选择了拆除");
                                System.out.println("是否使用拆除牌？（1：是； 2：否）");
                                int useREMOVE = scanner.nextInt();
                                if (useREMOVE==1){
                                    yourCard.remove(Card.REMOVE);
                                    latestUsedCard=Card.REMOVE;
                                }else{
                                    //dead
                                    currentUser.setAlive(false);
                                    //aliveNumber--
                                    game.setAliveNumber(game.getAliveNumber()-1);
                                    //random to place the boom
                                    int pos=(int)(random()*(tail-front+2));
                                    cards.add(pos+front,Card.BOOM);
                                    continue;
                                }
                                //where to place the boom
//                       int pos=(int)(random()*(tail-front+2));
                                System.out.println("请选择炸弹放置的位置（第1、2、3、4、5、6（牌底）、7（随机））");
                                int pos=scanner.nextInt();
                                if(pos==6){
                                    pos=tail-front+1;
                                }else if(pos==7){
                                    pos=(int)(random()*(tail-front+2));
                                }else{
                                    pos--;
                                }
                                cards.add(pos+front,Card.BOOM);
                                //when adding sth, the tail++
                                tail++;
                                System.out.println("炸弹放进了第"+(pos+1)+"张");
                                //show the left cards
                                System.out.println("当前总牌为：");
                                showCards(cards,front,tail);
                            }else{
                                System.out.println("您无拆除牌，"+currentUser.getNickname()+"（您）已出局");
                                //dead
                                currentUser.setAlive(false);
                                //aliveNumber--
                                game.setAliveNumber(game.getAliveNumber()-1);
                                //random to place the boom
                                int pos=(int)(random()*(tail-front+2));
                                cards.add(pos+front,Card.BOOM);
                                //when adding sth, the tail++
                                tail++;
                                System.out.println("炸弹放进了第"+(pos+1)+"张");
                                //show the left cards
                                System.out.println("当前总牌为：");
                                showCards(cards,front,tail);
                            }
                        }else{
                            //add
                            yourCard.add(tmp);
                            latestUsedCard=Card.BOTTOM;
                        }
                    }
                    case SHUFFLE -> {
                        System.out.println("原牌面:");
                        showCards(cards, front, tail);
                        CardUtil.shuffle(cards,front,tail);
                        System.out.println("洗牌后:");
                        showCards(cards, front, tail);
                        latestUsedCard=Card.SHUFFLE;
                    }
                    case EXCHANGE -> {
                        //choose a pointed man not you
                        System.out.println("请选择交换的对象是第几个（共"+userNumber+"个）：");
                        int pointedIndex = scanner.nextInt()-1;
//                        int pointedIndex=(int)(random()*userNumber);
                        while (pointedIndex==currIndex||(!users.get(pointedIndex).isAlive())){
//                            pointedIndex=(int)(random()*userNumber);
                            System.out.println("第"+(pointedIndex+1)+"个无法交换！请重新选择（共"+userNumber+"个）：");
                            pointedIndex = scanner.nextInt()-1;
                        }
                        User pointedUser = users.get(pointedIndex);
                        System.out.println(pointedUser.getNickname()+"被换牌");
                        System.out.println("您原来的牌面为");
                        System.out.println(currentUser.getCards());
//                        System.out.println("目标牌面");
//                        System.out.println(pointedUser.getCards());
                        ArrayList<Card>tmp=currentUser.getCards();
                        currentUser.setCards(pointedUser.getCards());
                        pointedUser.setCards(tmp);
//                        System.out.println("您现在的牌面为");
//                        System.out.println(currentUser.getCards());
//                        System.out.println("对手牌面");
//                        System.out.println(pointedUser.getCards());
                        latestUsedCard=Card.EXCHANGE;
                    }
                }
                System.out.println("您现在的牌面变为"+yourCard);
            }
            //special turn
            if(times>=1){
                System.out.println("一轮结束，现在是甩锅阶段x"+times+" 到"+users.get(currIndex).getNickname());
                System.out.println("");
                preNumber=times;
                times--;
            }else{
                System.out.println("现在是顺逆牌序");
                normalTurn=true;
                preNumber=0;
            }
            if(normalTurn){
                if(clockwise){
                    currIndex=(currIndex + 1)%userNumber;
                }else{
                    currIndex=(currIndex +userNumber-1)%userNumber;
                }
                System.out.println("您的回合已结束，按序到"+users.get(currIndex).getNickname());
                System.out.println("");
            }

            if(front>tail){
                System.out.println("没牌了，平局吧");
                break;
            }
        }
        System.out.println("结束啦，最后的赢家是： "+users.get(currIndex).getNickname());
        System.out.println("总回合数"+totalCnt);
        System.out.println("本轮游戏结束，欢迎下次再来");
    }
}
