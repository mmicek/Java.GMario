package agh;

public abstract class Block {
    /*
        Position to relana pozycja na mapie.
        Writable_position to zmienna mowiaca w gdzie aktualnie w okinku jest wyswietlany klocek
        Size rozmiar a writable_position to pozycja w okienku jframu

        Kazda nowo dodana klase dziedziczaca po tej klasie trzeba dodac do metod:
        -klasy JFrames
        -colision
        -loadmap
        -move
     */

    protected Position position;
    private Size size;
    protected Position writable_position;
    private WorldMap map;
    int add = 1;

    Block(Position position,Size size,WorldMap map){
        this.position = position;
        this.map = map;
        this.writable_position = position;
        this.size = size;
    }

    public void setAdd(){
        add = 0;
    }

    public MapDirection colision(Object object){  //!!!POPRAWIC ZE JAK BOKIEM SPADA TO TEZ NISZCZY BLOCK
        /*
            Oblicza kolizje dwoch prostokotow tego i mario (czy zachodza na siebie).
            Jesli nie ma kolizji to zwraca Center.
            W innym przypadku odpowiednio z ktorej strony bloku wysapila kolizja
         */

        if(object instanceof Mario) { //wartosc zakazana kiedy sa doklanie na wysokossci glowy lub nog
            Mario that = (Mario) object;
            ///przyspieszenie kolowe zrobic
            if(this instanceof BreakBlock && that.getPosition().getY() + 1 == position.getY() + size.getHeight() ){
                if(that.getPosition().getY() + 1 == position.getY() + size.getHeight() && that.getPosition().getX()+that.getSize().getWidth() > position.getX()+40 && that.getPosition().getX() < position.getX()+(60-40))
                    return MapDirection.South;
                else
                    return MapDirection.Center;
            }
            if (that.getPosition().getY() + 1 == position.getY() + size.getHeight() && that.getPosition().getX() > position.getX() - that.getSize().getWidth()+2 && that.getPosition().getX() < position.getX() + size.getWidth()-1 && map.getMario().getJumped()) {
                return MapDirection.South;
            }
            if (that.getPosition().getY() + that.getSize().getHeight() - 1 == position.getY() && that.getPosition().getX() > position.getX() - that.getSize().getWidth()+2 && that.getPosition().getX() < position.getX() + size.getWidth()-add) {
                return MapDirection.North;
            }
            if (that.getPosition().getX() + that.getSize().getWidth() - 1 >= position.getX() && that.getPosition().getX() < position.getX() + size.getWidth() / 3 && that.getPosition().getY()-1 > position.getY() - that.getSize().getHeight() && that.getPosition().getY() < position.getY() + size.getHeight())
                return MapDirection.West;
            if (that.getPosition().getX() + 1 <= position.getX() + size.getWidth() && that.getPosition().getX() > position.getX() + 2 * size.getWidth() / 3 && that.getPosition().getY()-1 > position.getY() - that.getSize().getHeight() && that.getPosition().getY() < position.getY() + size.getHeight())
                return MapDirection.East;
        }
        if(object instanceof FireBullets){
            FireBullets that = (FireBullets) object;
            if (that.getReal_position().getY() + 1 == position.getY() + size.getHeight() && that.getReal_position().getX() > position.getX() - that.getSize().getWidth() && that.getReal_position().getX() < position.getX() + size.getWidth()) {
                return MapDirection.South;
            }
            if (that.getReal_position().getY() + that.getSize().getHeight() - 1 == position.getY() && that.getReal_position().getX() > position.getX() - that.getSize().getWidth() && that.getReal_position().getX() < position.getX() + size.getWidth()) {
                return MapDirection.North;
            }
            if (that.getReal_position().getX() + that.getSize().getWidth() - 1 >= position.getX() && that.getReal_position().getX() < position.getX() + size.getWidth() / 3 && that.getReal_position().getY() > position.getY() - that.getSize().getHeight() && that.getReal_position().getY() < position.getY() + size.getHeight())
                return MapDirection.West;
            if (that.getReal_position().getX() + 1 <= position.getX() + size.getWidth() && that.getReal_position().getX() > position.getX() + 2 * size.getWidth() / 3 && that.getReal_position().getY() > position.getY() - that.getSize().getHeight() && that.getReal_position().getY() < position.getY() + size.getHeight())
                return MapDirection.East;
        }
        if(object instanceof BlackBullet){
            BlackBullet that = (BlackBullet) object;
            if (that.getReal_position().getY() + 1 == position.getY() + size.getHeight() && that.getReal_position().getX() > position.getX() - that.getSize().getWidth() && that.getReal_position().getX() < position.getX() + size.getWidth()) {
                return MapDirection.South;
            }
            if (that.getReal_position().getY() + that.getSize().getHeight() - 1 == position.getY() && that.getReal_position().getX() > position.getX() - that.getSize().getWidth() && that.getReal_position().getX() < position.getX() + size.getWidth()) {
                return MapDirection.North;
            }
            if (that.getReal_position().getX() + that.getSize().getWidth() - 1 >= position.getX() && that.getReal_position().getX() < position.getX() + size.getWidth() / 3 && that.getReal_position().getY() > position.getY() - that.getSize().getHeight() && that.getReal_position().getY() < position.getY() + size.getHeight())
                return MapDirection.West;
            if (that.getReal_position().getX() + 1 <= position.getX() + size.getWidth() && that.getReal_position().getX() > position.getX() + 2 * size.getWidth() / 3 && that.getReal_position().getY() > position.getY() - that.getSize().getHeight() && that.getReal_position().getY() < position.getY() + size.getHeight())
                return MapDirection.East;
        }
        if(object instanceof Enemy){
            Enemy that = (Enemy) object;
            if(that.getPosition().getY()+that.getSize().getHeight() == position.getY() && that.getPosition().getX()+1 == position.getX())
                return MapDirection.West;
            if(that.getPosition().getY()+that.getSize().getHeight() == position.getY() && that.getPosition().getX()+that.getSize().getWidth()-1 == position.getX()+size.getWidth())
                return MapDirection.East;
        }
        return MapDirection.Center;
    }

    public Size getSize(){
      return size;
  }

    public Position getWritable_position() {
        return writable_position;
    }

    public WorldMap getMap() {
        return map;
    }

    public void setWritablePosition(Position writable_position){
        this.writable_position = writable_position;
    }

    public void subWritPos(){
        /*
            Wykozystywane przez mape WorldMap.
            Cofa objekt o jedna pozycje X w lewo.
         */
        this.writable_position = new Position(this.writable_position.getX()-1,this.writable_position.getY());
    }

    public Position getPosition() {
        return position;
    }

}
