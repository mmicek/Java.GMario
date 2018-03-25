package agh;

public class LuckyBlock extends Block {
    private boolean bonus_granted = false;

    LuckyBlock(Position position,Size size,WorldMap map){
        super(position,size,map);
    }

    public void setBonus_granted(){
        bonus_granted = true;
    }
    public boolean isBonus_granted(){
        return bonus_granted;
    }
}
