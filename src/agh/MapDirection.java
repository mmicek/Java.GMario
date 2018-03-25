package agh;
public enum MapDirection {
    North,East,South, West,Center;
    public MapDirection next(){
            return MapDirection.values()[(this.ordinal()+1)%4];
        }
    public MapDirection prevous() {
        int ord = this.ordinal();
        if (ord == 0) ord = this.values().length - 2;
        else ord = ord - 1;
        return MapDirection.values()[ord];
    }
    public MapDirection opposite(){
        return MapDirection.values()[(this.ordinal()+2)%4];
    }
}

