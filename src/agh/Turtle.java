package agh;

public class Turtle extends Enemy {
    Turtle(Position position,Size size,MapDirection direction,WorldMap map,int i){
        super(position,size,direction,map,i);
    }
    Turtle(Position position,Size size,MapDirection direction,WorldMap map,int i,int x,int y){
        super(position,size,direction,map,i,x,y);
    }
}
