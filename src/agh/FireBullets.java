package agh;

public class FireBullets extends Bullets {
    /*
        Ten pocisk ma gravitacje czyli spada w dol
     */
    private long time = System.nanoTime();  //Szybkosc spadania w dol
    private Position old_position;
    private boolean falling = false;

    FireBullets(Position position,Position real_pos,Size size,MapDirection direction,WorldMap map){
        super(position,real_pos,size,direction,map);
        old_position = position;
    }

    public void shiftPosition(){
        shiftPos();
        int EoP = 900000 + 300*getUp_velocity();
        if(EoP < 900000) EoP = EoP - 300*getUp_velocity();
        if(System.nanoTime() - time > EoP){
            setPosition(change());
            setReal_position(changeReal());
            time = System.nanoTime();
        }
    }

    private Position change(){
        if(falling){
            subUp_velocity();
            return new Position(getPosition().getX(),getPosition().getY()+1);
        }
        else if(!falling && old_position.getY()-330 > getPosition().getY()){  //To 250 to wysokosc 'skoku'
            falling = true;
        }
        addUp_velocity();
        return new Position(getPosition().getX(),getPosition().getY()-1);
    }

    private Position changeReal(){
        if(falling){
            return new Position(getReal_position().getX(),getReal_position().getY()+1);
        }
        else if(!falling && old_position.getY()-250 > getPosition().getY()){  //To 250 to wysokosc 'skoku'
            falling = true;
        }
        return new Position(getReal_position().getX(),getReal_position().getY()-1);
    }
}
