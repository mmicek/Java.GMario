package agh;


import java.util.concurrent.TimeUnit;

public class Mario {
    private Size size;
    private JFrames frame;
    private WorldMap map;
    private Position position;
    private Position real_position;
    private MapDirection direction = MapDirection.Center;
    private MapDirection last_direction = MapDirection.East;
    private boolean jumped=false;

    private long invincibility;
    private int lives = 1;

    private int jump_range = 250;
    private boolean big_jump = false;
    private boolean big_mario = false;
    private long big_mario_timer;
    private boolean finished = false;

    private int real_next_position = 724;//pozycja gdzie trzeba stanac aby dzilalo i nie przesuwalo o 1 w lewo
    private boolean falling = false;
    private long time = System.nanoTime();
    private long time2 = System.nanoTime();
    private long gravity = 0;
    private int oldY;  //y z jakiego zaczolm skakac do gory

    Mario(Position position,WorldMap map){
        this.position = position;
        this.map = map;
        this.real_position = position;
        oldY = position.getY();
    }

    public void move() throws InterruptedException {
        if(big_mario && System.currentTimeMillis() - big_mario_timer > 90000){  //!!! ZROBIC NIE TIMER TYLKO KOLIZJE Z PRZECIWNIKAMI (wylaczanie bonusow)
            frame.setMarioPicture(0);
            big_mario = false;
        }
        if(position.getY() > map.getHeight()) System.exit(0);
        if(System.nanoTime() - time2 > (750000 + gravity*1000)){
            gravity = oldY - position.getY();                       //grawitacja jest szybsza jak spadasz bo oldY = ta sama pozycja
            if(jumped) jump(true);
            else jump(false);
            if(map.colision(this) == MapDirection.South) { //tu bedzie ze jak dotkie klocka to juz nie spada w dol czy jakis klocdek na GOrze
                jump(true);
                falling = true;
            }
           else if (map.colision(this) == MapDirection.North){ // (wyjrebac to pos .getY)
                oldY = position.getY()+200;
                falling = false;
                jump(true);
                jumped = false;
            }
            time2 = System.nanoTime();
            frame.getMario().setBounds(position.getX(), position.getY(),size.getWidth(), size.getHeight());
        }
        if ((System.nanoTime() - time) > 1300000) {
            time = System.nanoTime();
            real_position = real_position.shift(direction);
            position = position.shift(direction);
            if(map.colision(this) == direction.opposite() || position.getX()<0){
                real_position = real_position.shift(direction.opposite());
                position = position.shift(direction.opposite());
            }
            else if((this.position.getX() > map.getWidth()/2 - size.getWidth()/2 || this.position.getX() < 0)) {
                position = position.shift(direction.opposite());
            }
            if(real_position.getX()-1 == real_next_position && direction == MapDirection.East){
                map.move();
                real_next_position = real_position.getX();
            }
            frame.getMario().setBounds(position.getX(), position.getY(),size.getWidth(), size.getHeight());
        }
    }

    public void jump(boolean x){
            int y = 1;
            if(!x) y=-1;
            if (position.getY() > oldY - jump_range && !falling) {
                real_position = real_position.shift(MapDirection.Center,-y);
                position = position.shift(MapDirection.Center, -y);
            }
            else {
                real_position = real_position.shift(MapDirection.Center,y);
                position = position.shift(MapDirection.Center, y);
                falling = true;
            }
    }

    public void setJump() throws InterruptedException {
        if(!jumped) {
            map.setDamn();
            oldY = position.getY();
            real_position =  real_position.shift(MapDirection.South);
            if(map.colision(this) != MapDirection.Center) {
                jumped = true;
            }
            real_position = real_position.shift(MapDirection.North);
        }
    }

    public Position getPosition(){
        return real_position;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public Position getWritablePosition() {
        return position;
    }

    public void setFrame(JFrames frame){
        this.frame = frame;
        this.size = frame.getSizes(frame.getMario());
    }

    public void setFinished() {
        this.finished = true;
    }

    public void setDirection(MapDirection direction){
        this.direction = direction;
        if(direction != MapDirection.Center) {
            if(last_direction != direction){
                changeImage();
            }
            this.last_direction = direction;
        }
    }

    public Size getSize() {
        return size;
    }

    public void setBig_mario() throws InterruptedException {
        lives = 2;
        jump_range = 250; //!ZMIENIC NA 250
        big_jump = false;
        for(int i=0;i<2;i++) {
            frame.setMarioPicture(1);
            while(!finished){
                ;
            }
            finished = false;
            TimeUnit.MILLISECONDS.sleep(70);
            frame.setMarioPicture(0);
            while(!finished){
                ;
            }
            finished = false;
            TimeUnit.MILLISECONDS.sleep(70);
        }
        frame.setMarioPicture(1);
        while(!finished){
            ;
        }
        finished = false;
        big_mario = true;
        big_mario_timer = System.currentTimeMillis();
    }

    public void setBig_jump() throws InterruptedException {
        lives = 2;
        for(int i=0;i<2;i++) {
            frame.setMarioPicture(2);
            while(!finished){
                ;
            }
            finished = false;
            TimeUnit.MILLISECONDS.sleep(70);
            frame.setMarioPicture(0);
            while(!finished){
                ;
            }
            finished = false;
            TimeUnit.MILLISECONDS.sleep(70);
        }
        frame.setMarioPicture(2);
        while(!finished){
            ;
        }
        finished = false;
        big_mario = false;
        jump_range = 350;
    }

    public boolean flag=false;
    public void shoot(){
        if(big_mario == true){
            Position real_position = new Position(this.real_position.getX()+30,this.real_position.getY()+20);
            Position position = new Position(this.position.getX()+30,this.position.getY()+20);
            //...strzaly
            frame.addFireBullet(real_position);
            while(!flag){
                ;
            }
            flag = false;
            map.getFireBullets().add(new FireBullets(position,real_position,new Size(20,20),last_direction,map));
        }
    }

    public void gotHitByEnemy() throws InterruptedException {
        if(System.currentTimeMillis() - invincibility < 800) return;
        for(int i=0;i<2;i++) {
            frame.setMarioPicture(4);
            while(!finished){
                ;
            }
            finished = false;
            TimeUnit.MILLISECONDS.sleep(70);
            frame.setMarioPicture(0);
            while(!finished){
                ;
            }
            finished = false;
            TimeUnit.MILLISECONDS.sleep(70);
        }
        frame.setMarioPicture(0);
        while(!finished){
            ;
        }
        lives --;
        if(lives == 0) System.exit(0);
        finished = false;
        big_mario = false;
        big_jump = false;
        invincibility = System.currentTimeMillis();
    }

    public boolean getJumped(){
        return jumped;
    }

    public void changeImage(){
        frame.changeMarioPic();
    }

    public void shiftPosition(MapDirection direction){
        position = position.shift(direction);
        real_position = real_position.shift(direction);
    }
}
