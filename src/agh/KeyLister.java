package agh;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyLister implements KeyListener {
    private WorldMap map;
    private boolean left = true;
    private boolean right = true;

    KeyLister(WorldMap map){
        this.map = map;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_UP)
            try {
                map.getMario().setJump();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        if (key == KeyEvent.VK_LEFT) {
            map.getMario().setDirection(MapDirection.West);
            left = false;
        }
        if(key == KeyEvent.VK_RIGHT) {
            map.getMario().setDirection(MapDirection.East);
            right = false;
        }
        if(key == KeyEvent.VK_SPACE){
          map.getMario().shoot();
        }
        if(key == KeyEvent.VK_P){  //CZESTOLIWOSC STRZALOW
            map.setPause();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_UP)
            try {
                map.getMario().setJump();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        if (key == KeyEvent.VK_LEFT) {
            map.getMario().setDirection(MapDirection.West);
            left = false;
        }
        if(key == KeyEvent.VK_RIGHT) {
            map.getMario().setDirection(MapDirection.East);
            right = false;
        }
        if(key == KeyEvent.VK_SPACE){  //CZESTOLIWOSC STRZALOW
            map.getMario().shoot();
        }
        if(key == KeyEvent.VK_P){  //CZESTOLIWOSC STRZALOW
            map.setPause();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
            left = true;
        if(key == KeyEvent.VK_RIGHT)
            right = true;
        if (left && right)
            map.getMario().setDirection(MapDirection.Center);
    }
}
