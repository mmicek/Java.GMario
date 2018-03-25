package agh;

import java.util.Map;

public class Cannon extends Enemy{
    /*
        W map jak bedzie kolizja to odejmuje mu jedno zycie i patrzy czy ma > 0
        Jesli nie to w MAP usuwa NATYCHMIASTOWO ten objekt nie tutaj
     */

    private int frequency;
    private int distance;


    Cannon(Position position,Size size ,MapDirection direction,WorldMap map,int lives,int frequency,int distance){
        super(position,size,direction,map,lives);
        this.frequency = frequency;
        this.distance = distance;
    }
    Cannon(Position position,Size size,WorldMap map,int lives,int frequency,int distance){
        super(position,size,MapDirection.East,map,lives);
        this.frequency = frequency;
        this.distance = distance;
    }

    public void shoot(){
        if(System.nanoTime() - getTime() > 3900000*frequency){
            map.getBlackBullets().add(new BlackBullet(this.getWritable_position().add(new Position(-45,7)),this.getPosition().add(new Position(-45,7)),new Size(28,45),getDirection(),map));
            map.getFrame().addBlackBullet(this.getWritable_position().add(new Position(-45,7)), getDirection());
            setTime();
        }
    }

    public int getDistance() {
        return distance;
    }
}
