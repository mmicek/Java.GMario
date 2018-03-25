package agh;

public abstract class Enemy {
    /*
        NIE MOZE BYC BLOKOWANIE NA KLOCKU
        POWINNO USUWAC Z LISTY JAK WYJDZIE POZA EKRAN
     */
    private Position position;  //ta co wypisuje na mapie
    private Position real_position;
    private int timer = 3900000;
    private Size size;
    private MapDirection direction;
    WorldMap map;
    private long time = System.nanoTime();
    private int lives = 1;

    private int x = -1;  //Zamiast kolizji z grounds mozna podac wartosci miedzy ktorymi ma zawracac wrog
    private int y = -1;


    Enemy(Position position,Size size,MapDirection direction,WorldMap map,int lives){
        this.position = position;
        this.map = map;
        this.direction = direction;
        this.real_position = position;
        this.size = size;
        this.lives = lives;
    }
    Enemy(Position position,Size size,MapDirection direction,WorldMap map,int lives,int x,int y){
        this.position = position;
        this.map = map;
        this.direction = direction;
        this.real_position = position;
        this.size = size;
        this.lives = lives;
        this.x = x;
        this.y = y;
    }


    public boolean move(int i) throws InterruptedException {
        if((System.nanoTime() - time) > timer){
            if(isAlive()) {
                if(x != -1){
                    if(between(real_position.shift(direction).getX())){
                        shiftDirection(i);
                        shift();
                    }
                    else{
                        shift();
                    }
                }
                else if (map.colision(this) != MapDirection.Center) {
                    shiftDirection(i);
                    shift();
                } else {
                    shift();
                }
            }
            else{
                timer = 390000;
                real_position = real_position.shift(MapDirection.South);
                position = position.shift(MapDirection.South);
            }
            time = System.nanoTime();
           return true;
        }
        return false;
    }

    public void shift(){
        this.position = position.shift(direction);
        this.real_position = real_position.shift(direction);
    }

    public void shiftDirection(int i){
        this.direction = direction.opposite();
        map.getFrame().changeImageTurtle(direction,i);
    }

    public void setTime(){
        this.time = System.nanoTime();
    }

    public long getTime(){
        return time;
    }

    public MapDirection colision(Object object){
        /*

         */

        if(object instanceof Mario) { //wartosc zakazana kiedy sa doklanie na wysokossci gloy lub nog
            Mario that = (Mario) object;
            ///przyspieszenie kolowe zrobic

            if (that.getPosition().getY() + 1 == real_position.getY() + size.getHeight() && that.getPosition().getX() > real_position.getX() - that.getSize().getWidth() && that.getPosition().getX() < real_position.getX() + size.getWidth() && map.getMario().getJumped()) {
                return MapDirection.South;
            }
            if (that.getPosition().getY() + that.getSize().getHeight() - 1 == real_position.getY() && that.getPosition().getX() > real_position.getX() - that.getSize().getWidth() && that.getPosition().getX() < real_position.getX() + size.getWidth()) {
                return MapDirection.North;
            }
            if (that.getPosition().getX() + that.getSize().getWidth() - 1 >= real_position.getX() && that.getPosition().getX() < real_position.getX() + size.getWidth() / 3 && that.getPosition().getY() > real_position.getY() - that.getSize().getHeight() && that.getPosition().getY() < real_position.getY() + size.getHeight())
                return MapDirection.West;
            if (that.getPosition().getX() + 1 <= real_position.getX() + size.getWidth() && that.getPosition().getX() > real_position.getX() + 2 * size.getWidth() / 3 && that.getPosition().getY() > real_position.getY() - that.getSize().getHeight() && that.getPosition().getY() < real_position.getY() + size.getHeight())
                return MapDirection.East;
        }
        if(object instanceof FireBullets){
            FireBullets that = (FireBullets) object;
            if (that.getReal_position().getY() + that.getSize().getHeight() - 1 == real_position.getY() && that.getReal_position().getX() > real_position.getX() - that.getSize().getWidth() && that.getReal_position().getX() < real_position.getX() + size.getWidth()) {
                return MapDirection.North;
            }
            if (that.getReal_position().getX() + that.getSize().getWidth() - 1 >= real_position.getX() && that.getReal_position().getX() < real_position.getX() + size.getWidth() / 3 && that.getReal_position().getY() > real_position.getY() - that.getSize().getHeight() && that.getReal_position().getY() < real_position.getY() + size.getHeight())
                return MapDirection.West;
            if (that.getReal_position().getX() + 1 <= real_position.getX() + size.getWidth() && that.getReal_position().getX() > real_position.getX() + 2 * size.getWidth() / 3 && that.getReal_position().getY() > real_position.getY() - that.getSize().getHeight() && that.getReal_position().getY() < real_position.getY() + size.getHeight())
                return MapDirection.East;
        }
        if(object instanceof BlackBullet){
            BlackBullet that = (BlackBullet) object;
            if (that.getReal_position().getX() + that.getSize().getWidth() - 1 >= real_position.getX() && that.getReal_position().getX() < real_position.getX() + size.getWidth() / 3 && that.getReal_position().getY() > real_position.getY() - that.getSize().getHeight() && that.getReal_position().getY() < real_position.getY() + size.getHeight())
                return MapDirection.West;
            if (that.getReal_position().getX() + 1 <= real_position.getX() + size.getWidth() && that.getReal_position().getX() > real_position.getX() + 2 * size.getWidth() / 3 && that.getReal_position().getY() > real_position.getY() - that.getSize().getHeight() && that.getReal_position().getY() < real_position.getY() + size.getHeight())
                return MapDirection.East;
        }
        return MapDirection.Center;
    }

    public void subWritPos(){
        /*
            Wykozystywane przez mape WorldMap.
            Cofa objekt o jedna pozycje X w lewo.
         */
        this.position = new Position(this.position.getX()-1,this.position.getY());
    }

    public Position getPosition() {
        return real_position;
    }

    public Position getWritable_position() {
        return position;
    }

    public void gotHit(){
        lives--;
    }

    public Size getSize() {
        return size;
    }

    public void setWritablePosition(Position position){
        this.position = position;
    }

    public boolean between(int i){
        if(i<x || i+size.getWidth()>y) return true;
        return false;
    }

    public boolean isAlive(){
        return lives>0;
    }

    public MapDirection getDirection() {
        return direction;
    }
}
