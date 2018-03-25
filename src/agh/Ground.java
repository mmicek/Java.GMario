package agh;

public class Ground extends Block {
        /*
            Wysokosc to standardowo jest 200
         */

    Ground(Position position,Size size,WorldMap map){
        super(position,size,map);
        setAdd();
    }
}
