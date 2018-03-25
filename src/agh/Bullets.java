package agh;

public abstract class Bullets {
    private Position position;  //Pozycja na ekranie przy kolizji pobierac writable position z klockow
    private Position real_position;
    private MapDirection direction;  //WEST OR EAST
    private int up_velocity = 50;
    private Size size;
    private long time = System.nanoTime();
    private WorldMap map;

    Bullets(Position position,Position real_position,Size size,MapDirection direction,WorldMap map){
        this.position = position;
        this.direction = direction;
        this.real_position = real_position;
        this.size = size;
        this.map = map;
    }

    public void shiftPos(){
        if(System.nanoTime() - time > 2100000) { //!!!!!!!!tu tez trzeba zmienic na mapie bo to jest nie zaleznie od mapy
            position = position.shift(direction);
            real_position = real_position.shift(direction);
            time = System.nanoTime();
        }
    }

    public void subWritPos(){
        /*
            Wykozystywane przez mape WorldMap.
            Cofa objekt o jedna pozycje X w lewo.
         */
        this.position = new Position(this.position.getX()-1,this.position.getY());
    }

    public Position getPosition() {
        return position;
    }

    public MapDirection getDirection() {
        return direction;
    }

    protected int getUp_velocity(){
        return  up_velocity;
    }

    protected void subUp_velocity(){
        up_velocity = up_velocity - 13;
    }

    protected void addUp_velocity(){
        up_velocity = up_velocity +  13;
    }

     protected void setPosition(Position position){
        this.position = position;
    }

    protected void setReal_position(Position position){
         this.real_position = position;
    }

    public Size getSize(){
         return this.size;
    }

    public Position getReal_position() {
        return real_position;
    }

    public MapDirection colision(Object object){  //!!!POPRAWIC ZE JAK BOKIEM SPADA TO TEZ NISZCZY BLOCK
        /*
            Oblicza kolizje dwoch prostokotow tego i mario (czy zachodza na siebie).
            Jesli nie ma kolizji to zwraca Center.
            W innym przypadku odpowiednio z ktorej strony bloku wysapila kolizja
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
            if (that.getPosition().getX() + 1 <= position.getX() + size.getWidth() && that.getPosition().getX() > real_position.getX() + 2 * size.getWidth() / 3 && that.getPosition().getY() > real_position.getY() - that.getSize().getHeight() && that.getPosition().getY() < real_position.getY() + size.getHeight())
                return MapDirection.East;
        }
        if(object instanceof FireBullets){
            FireBullets that = (FireBullets) object;
            if (that.getReal_position().getY() + 1 == real_position.getY() + size.getHeight() && that.getReal_position().getX() > real_position.getX() - that.getSize().getWidth() && that.getReal_position().getX() < real_position.getX() + size.getWidth()) {
                return MapDirection.South;
            }
            if (that.getReal_position().getY() + that.getSize().getHeight() - 1 == real_position.getY() && that.getReal_position().getX() > real_position.getX() - that.getSize().getWidth() && that.getReal_position().getX() < real_position.getX() + size.getWidth()) {
                return MapDirection.North;
            }
            if (that.getReal_position().getX() + that.getSize().getWidth() - 1 >= real_position.getX() && that.getReal_position().getX() < real_position.getX() + size.getWidth() / 3 && that.getReal_position().getY() > real_position.getY() - that.getSize().getHeight() && that.getReal_position().getY() < real_position.getY() + size.getHeight())
                return MapDirection.West;
            if (that.getReal_position().getX() + 1 <= real_position.getX() + size.getWidth() && that.getReal_position().getX() > real_position.getX() + 2 * size.getWidth() / 3 && that.getReal_position().getY() > real_position.getY() - that.getSize().getHeight() && that.getReal_position().getY() < real_position.getY() + size.getHeight())
                return MapDirection.East;
        }
        if(object instanceof Enemy){
            Enemy that = (Enemy) object;
            if(that.getPosition().getY()+that.getSize().getHeight() == real_position.getY() && that.getPosition().getX()+1 == real_position.getX())
                return MapDirection.West;
            if(that.getPosition().getY()+that.getSize().getHeight() == real_position.getY() && that.getPosition().getX()+that.getSize().getWidth()-1 == real_position.getX()+size.getWidth())
                return MapDirection.East;
        }
        return MapDirection.Center;
    }
}
