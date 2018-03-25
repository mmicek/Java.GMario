package agh;

public class BreakBlock extends Block{
    BreakBlock(Position position,Size size,WorldMap map){
        super(position,size,map);
    }
    public void removes(int i){
        getMap().getFrame().getBrake_blocks()[i].setVisible(false);
        getMap().getFrame().getC().remove(getMap().getFrame().getBrake_blocks()[i]);
    }
}
