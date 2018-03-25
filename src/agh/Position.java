package agh;

public class Position {
    private final int x;
    private final int y;
    Position(int x,int y){
        this.x=x;
        this.y=y;
    }
    public boolean smaller(Position other) {
        return this.x <= other.x && this.y <= other.y;
    }
    public boolean bigger(Position other){
        return this.x>=other.x && this.y>=other.y;
    }
    public Position add(Position other){
        return new Position(this.x+other.x,this.y+other.y);
    }
    public boolean equals(Object other){
        if(other==this) return true;
        if(!(other instanceof Position)) return false;
        Position that=(Position) other;
        return that.x==this.x && that.y==this.y;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public Position shift(MapDirection direction){
        switch(direction){
            case West:
                return new Position(this.x-1,this.y);
            case East:
                return new Position(this.x+1,this.y);
            case North:
                return new Position(this.x,this.y-1);  //!!! Dodaje 2 pola!!!
            case South:
                return new Position(this.x,this.y+1);
        }
        return this;
    }

    public Position shift(MapDirection direction,int y){
        return shift(direction).add(new Position(0,y));
    }
}
