package agh;

public class Size {
    private final int wyskosc;
    private final int szerokosc;

    Size(int wys,int szer){
        this.wyskosc = wys;
        this.szerokosc = szer;
    }

    public int getWidth(){
        return szerokosc;
    }

    public int getHeight(){
        return wyskosc;
    }
}
