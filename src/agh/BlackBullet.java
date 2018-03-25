package agh;

public class BlackBullet extends Bullets {
    private int lives = 1;

    BlackBullet(Position position,Position real_position,Size size,MapDirection direction,WorldMap map,int lives){
        super(position,real_position,size,direction,map);
        this.lives = lives;
    }
    BlackBullet(Position position,Position real_position,Size size,MapDirection direction,WorldMap map){
        super(position,real_position,size,direction,map);
    }

    public void shiftPosition(){
        shiftPos();
    }

    public void gotHit(){
        this.lives--;
    }

    public boolean isAlive(){
        return lives > 0;
    }
}
