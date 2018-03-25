package agh;

public class Brick extends Block {
    //size to jest 60 x 60
    private long time = System.nanoTime();
    private int x = -1;
    private int y = -1;
    MapDirection direction = MapDirection.East;

    Brick(Position position,Size size,WorldMap map){
        super(position,size,map);
    }

    Brick(Position position,Size size,WorldMap map,int x,int y){
        super(position,size,map);
        this.x = x;
        this.y = y;
    }

    public boolean move() {
        if (x != -1 && y != -1) {
            if (System.nanoTime() - time > 6650000) {
                if(position.getX() < x || position.getX() > y){
                   direction=direction.opposite();
                }
                this.position = position.shift(direction);
                this.writable_position = writable_position.shift(direction);
                time = System.nanoTime();
                return true;
            }
        }
        return false;
    }

    public boolean isMoving(){
        return x != -1;
    }

    public MapDirection getDirection() {
        return direction;
    }
}
