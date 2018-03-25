package agh;

import javax.swing.*;

public class Bonus extends Block{
    private static int number_of_bonuses = 1; //Dodac zdjecie w JFrame
    private int bonus_number;

    Bonus(Position position,Position writable_pos,Size size,WorldMap map){
        super(position,size,map);
        setWritablePosition(writable_pos);
        bonus_number = writable_pos.getX()%(number_of_bonuses);
        map.getFrame().setBonusPicture(bonus_number);
    }
    Bonus(Position position,Position writable_pos,Size size,WorldMap map,int bounus_number){
        super(position,size,map);
        setWritablePosition(writable_pos);
        this.bonus_number = bounus_number;
        map.getFrame().setBonusPicture(bonus_number);
    }

    public void getBonus() throws InterruptedException {
        getMap().getMario().setBig_mario();
//                switch(bonus_number){
//            case 1:
//                getMap().getMario().setBig_mario();
//                break;
//            case 2:
//                getMap().getMario().setBig_jump();
//                break;
//        }
    }

}
