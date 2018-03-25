package agh;

import java.util.ArrayList;
import java.util.List;

public class MarioGame {
    public static void main(String[] args) throws InterruptedException {
        WorldMap map = new WorldMap();
        JFrames frame = new JFrames(map);
        map.setFrame(frame);
        map.run();
    }
}

