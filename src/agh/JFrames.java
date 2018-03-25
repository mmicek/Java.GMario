package agh;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class JFrames extends JFrame {
    /*
        Dla kazdego nowego objektu trzeba utworzyc metode setBounds.
     */
    private JLabel background = new JLabel(new ImageIcon("background.jpg"));
    private Position bPos = new Position(0,0);
    private Size bsize = new Size(1000,1500);

    private  Container c = getContentPane();
    private JLabel bricks [] = new JLabel[44];
    private JLabel grounds [] = new JLabel[7];
    private JLabel brake_blocks [] = new JLabel[3];
    private JLabel lucky [] = new JLabel[1];
    private JLabel bonus = new JLabel();
    private JLabel [] pictures = new JLabel[3];
    private ImageIcon f_bullet = new ImageIcon("firebullets.png");  //...setIcon(f_bulet);  firebullets.png
    private ImageIcon turtE = new ImageIcon("turtle.png");  //zmienic
    private ImageIcon turtW = new ImageIcon("turtle2.png");
    private ImageIcon bBImage1 = new ImageIcon("blackb1.png");
    private ImageIcon bBImage2 = new ImageIcon("blackb2.png");
    private ArrayList <JLabel> fire_bullets = new ArrayList<>();
    private ArrayList<JLabel> turtles = new ArrayList<>();
    private ArrayList<JLabel> blackBullets = new ArrayList<>();
    private ArrayList<JLabel> cannons = new ArrayList<>();

    private JLabel marios[] = new JLabel[5];
    private ImageIcon Lmarios[] = new ImageIcon[5];
    private ImageIcon Rmarios[] = new ImageIcon[5];
    private int which_image = 0;
    private int which_mario = 0;

    private WorldMap map;

    public JFrames(WorldMap map){
        this.map = map;
        addKeyListener(new KeyLister(map));

        loadImages(c);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //!!!POMYSL ZROBIC KAZDEJ KLASIE STATICA Z IMAGE ICON

        //BONUS PICTURES  //Tu dopisac i w Bonus klasie
        pictures[0] = new JLabel(new ImageIcon("bonus.png"));
        pictures[1] = new JLabel(new ImageIcon("bonus1.png"));
        pictures[2] = new JLabel(new ImageIcon("bonus2.png"));
        //MARIO PICTURES
        marios[0] = new JLabel(new ImageIcon("mario2.png"));
        marios[1] = new JLabel(new ImageIcon("mario3.png"));
        marios[2] = new JLabel(new ImageIcon("mario1.png"));
        marios[3] = new JLabel(new ImageIcon("mario2.png"));
        marios[4] = new JLabel(new ImageIcon("mario2.png"));

        Lmarios[0] = new ImageIcon("Lmario2.png");
        Lmarios[1] = new ImageIcon("Lmario3.png");
        Lmarios[2] = new ImageIcon("Lmario1.png");
        Lmarios[3] = new ImageIcon("Lmario2.png");
        Lmarios[4] = new ImageIcon("Lmario2.png");

        Rmarios[0] = new ImageIcon("mario2.png");
        Rmarios[1] = new ImageIcon("mario3.png");
        Rmarios[2] = new ImageIcon("mario1.png");
        Rmarios[3] = new ImageIcon("mario2.png");
        Rmarios[4] = new ImageIcon("mario2.png");



        c.setLayout(null);
        c.add(marios[0]);
        marios[0].setBounds(54, 100, 54, 72);
        c.add(marios[1]);
        marios[1].setBounds(54, 100, 54, 72);
        marios[1].setVisible(false);
        c.add(marios[2]);
        marios[1].setBounds(54, 100, 54, 72);
        marios[1].setVisible(false);

        setLocation(800, 10);
        setVisible(true);
        setLocation(300,30);
        setSize(1500,1000);
        map.setSize(getHeight(),getWidth());
    }

    private void loadImages(Container c){
        for(int i=0;i<bricks.length;i++){
            bricks[i] = new JLabel(new ImageIcon("Brick_Block.png"));
            c.add(bricks[i]);
        }
        for(int i=0;i<grounds.length;i++){
            grounds[i] = new JLabel(new ImageIcon("Ground1.png"  /*+map.getGrounds(i).getSize().getWidth() +".png"*/));
            c.add(grounds[i]);
        }
        for(int i=0;i<brake_blocks.length;i++){
            brake_blocks[i] = new JLabel(new ImageIcon("Brick_Block.png"  /*+map.getGrounds(i).getSize().getWidth() +".png"*/));
            c.add(brake_blocks[i]);
        }
        for(int i=0;i<lucky.length;i++) {
            lucky[i] = new JLabel(new ImageIcon("lucky.png"  /*+map.getGrounds(i).getSize().getWidth() +".png"*/));
            c.add(lucky[i]);
        }

    }


    public void removeTurtle(int i){
        c.remove(turtles.get(i));
        turtles.remove(i);
    }

    public void changeImageTurtle(MapDirection direction,int i){
        if(direction==MapDirection.East){
            turtles.get(i).setIcon(turtW);
        }
        else{
            turtles.get(i).setIcon(turtE);
        }
    }

    public void addTurtle(Position position){
        JLabel label = new JLabel();
        c.add(label,0);
        label.setIcon(turtW);
        label.setBounds(position.getX(),position.getY(),37,55); //rozmiar zlowia
        turtles.add(label);
        //map.getMario().flag=true;  //jesli bedzie sei scinac dodac
    }

    public void setTurtle(Position position,int i){
        turtles.get(i).setBounds(position.getX(),position.getY(),37,55);
    }

    public void setMarioPicture(int i){
        marios[i].setBounds(marios[which_mario].getBounds());
        marios[which_mario].setVisible(false);
        marios[i].setVisible(true);
        which_mario = i;
        map.getMario().setFinished();
    }

    public Size getSizes(JLabel object){
        return new Size(object.getBounds().height,object.getBounds().width);
    }

    public JLabel getMario(){
        return marios[which_mario];
    }

    public void setBricks(Position position,int i){
        bricks[i].setBounds(position.getX(),position.getY(),60,60);
    }

    public void setCannons(Position position,int i){
        cannons.get(i).setBounds(position.getX(),position.getY(),60,60); //!!!ROZMIAR ARMATY
    }

    public void setGrounds(Position position,int i){
        grounds[i].setBounds(position.getX(),position.getY(),400,67);
    }

    public void setBreakBlock(Position position,int i){
        brake_blocks[i].setBounds(position.getX(),position.getY(),60,60);
    }

    public JLabel[] getBrake_blocks() {
        return brake_blocks;
    }

    public JLabel[] getBricks() {
        return bricks;
    }

    public JLabel[] getGrounds() {
        return grounds;
    }

    public Container getC() {
        return c;
    }

    public JLabel[] getLucky() {
        return lucky;
    }

    public void setLucky(Position position, int i){
        lucky[i].setBounds(position.getX(),position.getY(),60,60);
    }

    public JLabel getBonus() {
        return bonus;
    }

    public void setBonus(Position position){
        bonus.setBounds(position.getX(),position.getY(),60,60);
    }

    public void setBonusPicture(int i){
        bonus = pictures[i];
        c.add(bonus,0);
    }

    public void addFireBullet(Position position){
        JLabel label = new JLabel();
        c.add(label,0);
        label.setIcon(f_bullet);
        label.setBounds(position.getX(),position.getY(),20,20);
        fire_bullets.add(label);
        map.getMario().flag=true;
    }
    public void setFireBullets(Position position,int i){
        fire_bullets.get(i).setBounds(position.getX(),position.getY(),20,20);
    }

    public void removeFireBullets(int i){
        c.remove(fire_bullets.get(i));
        fire_bullets.remove(i);
    }

    public ArrayList<JLabel> getFire_bullets() {
        return fire_bullets;
    }

    public void removeCannon(int i){
        cannons.get(i).setVisible(false);
        c.remove(cannons.get(i));
        cannons.remove(i);
    }

    public void addCannon(){
        JLabel label = new JLabel();
        c.add(label,0);
        label.setIcon(new ImageIcon("cannon.png"));
        cannons.add(0,label);
    }

    public void addBackground(){
        background.setBounds(bPos.getX(),bPos.getY(),bsize.getWidth(),bsize.getHeight());
        c.add(background);
    }

    public void setBackground(){
        //bPos = bPos.shift(MapDirection.East);
       // background.setBounds(bPos.getX(),bPos.getY(),bsize.getWidth(),bsize.getHeight());
    }

    public void changeMarioPic(){
        if(which_image == 0) {
            for (int i=0;i<marios.length-1;i++)
                marios[i].setIcon(Lmarios[i]);
            which_image = 1;
        }
        else{
            for (int i=0;i<marios.length- 1;i++)
                marios[i].setIcon(Rmarios[i]);
            which_image = 0;
        }
    }

    public void removeBlackBullet(int i){
        blackBullets.get(i).setVisible(false);
        c.remove(blackBullets.get(i));
        blackBullets.remove(i);
    }

    public void addBlackBullet(Position position,MapDirection direction){
        JLabel label = new JLabel();
        c.add(label,0);
        if(direction == MapDirection.West)
            label.setIcon(bBImage1);  //zmienic zdjecie
        else
            label.setIcon(bBImage2);
        label.setBounds(position.getX(),position.getY(),45,28); //ROZMIAR ZMIENIC
        blackBullets.add(label);
    }
    public void setBlackBullets(Position position,int i){
        blackBullets.get(i).setBounds(position.getX(),position.getY(),45,28); //ZMIENIC TEZ
    }
}

