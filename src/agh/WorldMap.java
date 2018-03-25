package agh;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

import java.util.ArrayList;
import java.util.List;

public class WorldMap {
    /*
        Przy kazdej zmianie liczby blockow trzeba to tez zrobic w JFrames
        Tablica all_... okresla wszystkie objekty danego typu na calej mapie
        Lista ...s posiada tylko obecnie wyswietlane objekty
        Lista intdex_... posiada odpowiednie indexy odpowiednich elementow w liscie powyzej
        Int all_.s_... pierwszy objekt z tablicy all_... ktory sie pojawi na mapie

        POZYCJA ZADNEGO BLOKA NIE MOZE BYC 1500 BO TO JEST KURWA ROZMIAR EKRANU
     */
    private boolean pause = false;
    private JFrames frame;
    private Mario mario;
    private Size size;
    //GROUND
    private Ground [] all_grounds = new Ground[7];
    private List<Ground> grounds = new ArrayList<>();
    private List<Integer> index_grounds = new ArrayList<>();
    private int all_grounds_index = 0;
    //BRICK
    private List<Brick> bricks= new ArrayList<>();
    private List<Integer> index_bricks = new ArrayList<>();
    private Brick [] all_bricks = new Brick[44];
    private int all_brick_index = 0;
    //BREAKBLOCK
    private List<BreakBlock> brake_blocks= new ArrayList<>();
    private List<Integer> index_brake_blocks = new ArrayList<>();
    private BreakBlock [] all_break_blocks = new BreakBlock[3];
    private int all_break_block_index = 0;
    //LUCKYBLOCK
    private LuckyBlock [] all_lucky = new LuckyBlock[1];
    private List<LuckyBlock> lucky = new ArrayList<>();
    private List<Integer> index_lucky = new ArrayList<>();
    private int all_lucky_index = 0;
    //BOUNUS
    private Bonus bonus = null;
    //BULLETS
    private List<FireBullets> fireBullets = new ArrayList<>();
    //TURTLE
    private Turtle [] all_turtles = new Turtle[2];
    private List<Turtle> turtles = new ArrayList<>();
    private List<Integer> index_turtles = new ArrayList<>();
    private int all_turtles_index = 0;
    //CANNON
    private Cannon [] all_cannons = new Cannon[2];
    private List<Cannon> cannons = new ArrayList<>();
    private List<Integer> index_cannons = new ArrayList<>();
    private int all_cannons_index = 0;
    //BLACK BULLET
    private List<BlackBullet> blackBullets = new ArrayList<>();
    //MOVING BLOCKS
    private List<Brick> movingBricks = new ArrayList<>();
    private List<Integer> mBIndex = new ArrayList<>();

    WorldMap(){
            this.addObjectsOnMap();
            mario = new Mario(new Position(100,500),this);
    }

    public void  setPause(){
        if(pause == true)
            pause = false;
        else {
            pause = true;

        }
    }
    private boolean re = true;
    public void reload(){
        this.re = false;
    }

    public int getHeight(){
        return size.getHeight();
    }

    public int getWidth(){
        return size.getWidth();
    }

    public void run() throws InterruptedException {
        this.loadMap();
        frame.addBackground();
        boolean x = false;
        while(true){
            if(x) System.out.println("NIC"); //????? PROBLEM
            if(!pause && re){
                mario.move();
                moveEnemys();
                WeSaluteYouFIREEE();
                moveBullets();
                moveBlocks();
            }
        }
    }

    private boolean damn = false;
    public void setDamn(){damn = false;}
    private MapDirection dir = MapDirection.West;
    public void moveBlocks(){
        for(int i = 0;i<movingBricks.size();i++){
            boolean czy = movingBricks.get(i).move();
            if(czy){
                if(movingBricks.get(i).getPosition().getX()+movingBricks.get(i).getSize().getWidth() < mario.getPosition().getX()-size.getWidth()/2) {
                    movingBricks.remove(i);
                    mBIndex.remove(i);
                }
                else {
                    if(czy && damn && mario.getPosition().getX() + mario.getSize().getWidth() > movingBricks.get(i).getPosition().getX() && mario.getPosition().getX() < movingBricks.get(i).getPosition().getX()+movingBricks.get(i).getSize().getWidth() && mario.getPosition().getY()+mario.getSize().getHeight()+10 > movingBricks.get(i).getPosition().getY() && mario.getPosition().getY() < movingBricks.get(i).getPosition().getY()+movingBricks.get(i).getSize().getHeight()){
                      //  if(dir != movingBricks.get(i).getDirection())
                     //       dir = movingBricks.get(i).getDirection();
                      //  mario.shiftPosition(dir);
                    }
                    frame.setBricks(movingBricks.get(i).getWritable_position(), mBIndex.get(i));
                }
            }
        }
    }

    public void WeSaluteYouFIREEE(){
        for(int i=0;i<cannons.size();i++)
            cannons.get(i).shoot();
    }

    public void moveEnemys() throws InterruptedException {
        for(int i=0;i<turtles.size();i++){
            if(turtles.get(i).getPosition().getY() > size.getHeight()) {
                turtles.remove(i);  // usuwanie z mapy i frama
                frame.removeTurtle(i);
            }
            else if(turtles.get(i).move(i)) frame.setTurtle(turtles.get(i).getWritable_position(),i);
        }
    }

    public void move() throws InterruptedException {
        /*
            Zmienia pozycje na mapie o 1 w lewo kazdego objektu.
            Te ktore wypadaja to usuwa a te ktore pojawiaja sie dodaje.
         */

        //BULLETS
        moveBulletsByMap();
        //BRICK
        if(bricks.size()!=0 && bricks.get(0).getPosition().getX()+bricks.get(0).getSize().getWidth() < mario.getPosition().getX()-size.getWidth()/2) {
            bricks.remove(0);
            index_bricks.remove(0);
            if(bricks.get(0).isMoving()) {
                movingBricks.remove(0);
                mBIndex.remove(0);
            }
        }
        if(all_brick_index  < all_bricks.length){
            while(all_brick_index <all_bricks.length && mario.getWritablePosition().getX() + (all_bricks[all_brick_index].getPosition().getX() - mario.getPosition().getX()) == size.getWidth()){
                all_bricks[all_brick_index].setWritablePosition(new Position(size.getWidth(),all_bricks[all_brick_index].getPosition().getY()));
                if(all_bricks[all_brick_index].isMoving()){
                    mBIndex.add(all_brick_index);
                    movingBricks.add(all_bricks[all_brick_index]);
                }
                bricks.add(all_bricks[all_brick_index]);
                index_bricks.add(all_brick_index);
                all_brick_index++;
            }
        }
        for(int i=0;i<bricks.size();i++){
            bricks.get(i).subWritPos();
            frame.setBricks(bricks.get(i).getWritable_position(),index_bricks.get(i));
        }
        //GROUNDS
       if(grounds.size()!=0 && grounds.get(0).getPosition().getX()+grounds.get(0).getSize().getWidth() < mario.getPosition().getX()-size.getWidth()/2) {
           grounds.remove(0);
           index_grounds.remove(0);
       }
       if(all_grounds_index  < all_grounds.length){
           while(all_grounds_index <all_grounds.length && mario.getWritablePosition().getX() + (all_grounds[all_grounds_index].getPosition().getX() - mario.getPosition().getX()) == size.getWidth()){
                all_grounds[all_grounds_index].setWritablePosition(new Position(size.getWidth(),all_grounds[all_grounds_index].getPosition().getY()));
                grounds.add(all_grounds[all_grounds_index]);
                index_grounds.add(all_grounds_index);
                all_grounds_index++;
            }
        }
        for(int i=0;i<grounds.size();i++){
            Ground b = grounds.get(i);
            b.subWritPos();
            frame.setGrounds(b.getWritable_position(),index_grounds.get(i));
        }
        //BREAKBLOCKS
        if(brake_blocks.size()!=0 && brake_blocks.get(0).getPosition().getX()+brake_blocks.get(0).getSize().getWidth() < mario.getPosition().getX()-size.getWidth()/2) {
            brake_blocks.remove(0);
            index_brake_blocks.remove(0);
        }
        if(all_break_block_index  < all_break_blocks.length){
            while(all_break_block_index <all_break_blocks.length && mario.getWritablePosition().getX() + (all_break_blocks[all_break_block_index].getPosition().getX() - mario.getPosition().getX())== size.getWidth()){
                all_break_blocks[all_break_block_index].setWritablePosition(new Position(size.getWidth(),all_break_blocks[all_break_block_index].getPosition().getY()));
                brake_blocks.add(all_break_blocks[all_break_block_index]);
                index_brake_blocks.add(all_break_block_index);
                all_break_block_index++;
            }
        }
        for(int i=0;i<brake_blocks.size();i++){
            brake_blocks.get(i).subWritPos();
            frame.setBreakBlock(brake_blocks.get(i).getWritable_position(),index_brake_blocks.get(i));
        }
        //LUCKYBLOCK
        if (lucky.size()!=0 && lucky.get(0).getPosition().getX()+lucky.get(0).getSize().getWidth() < mario.getPosition().getX()-size.getWidth()/2) {
            lucky.remove(0);
            index_lucky.remove(0);
        }
        if(all_lucky_index < all_lucky.length){
            if(mario.getWritablePosition().getX() + (all_lucky[all_lucky_index].getPosition().getX() - mario.getPosition().getX())== size.getWidth()){
                all_lucky[all_lucky_index].setWritablePosition(new Position(size.getWidth(),all_lucky[all_lucky_index].getPosition().getY()));
                lucky.add(all_lucky[all_lucky_index]);
                index_lucky.add(all_lucky_index);
                all_lucky_index++;
            }
        }
        for(int i=0;i<lucky.size();i++){
            LuckyBlock b = lucky.get(i);
            b.subWritPos();
            frame.setLucky(b.getWritable_position(),index_lucky.get(i));
        }
        //BONUS
        if(bonus != null && frame.getBonus().isVisible()){
            bonus.subWritPos();
            frame.setBonus(bonus.getWritable_position());
        }
        if(bonus != null && bonus.getWritable_position().getX() < 0){
            frame.getBonus().setVisible(false);
            bonus = null;
        }
        //TURTLES
        if(all_turtles_index < all_turtles.length){
            while(all_turtles_index <all_turtles.length && mario.getWritablePosition().getX() + (all_turtles[all_turtles_index].getPosition().getX() - mario.getPosition().getX())== size.getWidth()){ //<= mario.getPosition().getX()+mario.getSize().getWidth()/2+size.getWidth()/2
                all_turtles[all_turtles_index].setWritablePosition(new Position(size.getWidth(),all_turtles[all_turtles_index].getPosition().getY())); //musi byc bo jak jest w tablicy to wrtipos sie nie zmienia
                turtles.add(all_turtles[all_turtles_index]);
                frame.addTurtle(all_turtles[all_turtles_index].getWritable_position());
                index_turtles.add(all_turtles_index);
                all_turtles_index++;
            }
        }
        for(int i=0;i<turtles.size();i++){
            Turtle b = turtles.get(i);
            b.subWritPos();
            frame.setTurtle(b.getWritable_position(),index_turtles.get(i));
        }
        //CANNONS
        if (cannons.size()!=0 && cannons.get(0).getPosition().getX()+cannons.get(0).getSize().getWidth() < mario.getPosition().getX()-size.getWidth()/2) {
            cannons.remove(0);
            index_cannons.remove(0);
            frame.removeCannon(0);
        }
        if(all_cannons_index < all_cannons.length){
            while(all_cannons_index <all_cannons.length && mario.getWritablePosition().getX() + (all_cannons[all_cannons_index].getPosition().getX() - mario.getPosition().getX())== size.getWidth()+all_cannons[all_cannons_index].getDistance()){ //<= mario.getPosition().getX()+mario.getSize().getWidth()/2+size.getWidth()/2
                all_cannons[all_cannons_index].setWritablePosition(new Position(size.getWidth()+all_cannons[all_cannons_index].getDistance(),all_cannons[all_cannons_index].getPosition().getY())); //musi byc bo jak jest w tablicy to wrtipos sie nie zmienia
                cannons.add(0,all_cannons[all_cannons_index]);
                frame.addCannon();
                index_cannons.add(all_cannons_index);
                all_cannons_index++;
            }
        }
        for(int i=0;i<cannons.size();i++){
            Cannon b = cannons.get(i);
            b.subWritPos();
            frame.setCannons(cannons.get(i).getWritable_position(),index_cannons.get(i));
        }

    }
    private void loadMap(){
        /*
            Laduje te elementy ktore ssa na mapie po kliknieciu START.
         */

        //BRICK
        for(int i=0;i<all_bricks.length;i++){
            if(all_bricks[i].getPosition().getX()<size.getWidth()) {
                bricks.add(all_bricks[i]);
                index_bricks.add(i);
                all_brick_index = i+1;
                frame.setBricks(bricks.get(i).getPosition(), i);
            }
            else{
                all_brick_index = i;
                break;
            }
        }
        //GROUND
        for(int i=0;i<all_grounds.length;i++){
            if(all_grounds[i].getPosition().getX()<size.getWidth()) {
                grounds.add(all_grounds[i]);
                index_grounds.add(i);
                all_grounds_index = i+1;
                frame.setGrounds(grounds.get(i).getPosition(), i);
            }
            else {
                all_grounds_index = i;
                break;
            }
        }
        //BREAKBLOCK
        for(int i=0;i<all_break_blocks.length;i++){
            if(all_break_blocks[i].getPosition().getX()<size.getWidth()) {
                brake_blocks.add(all_break_blocks[i]);
                all_break_block_index = i+1;
                index_brake_blocks.add(i);
                frame.setBreakBlock(brake_blocks.get(i).getPosition(), i);
            }
            else{
                all_break_block_index = i;
                break;
            }
        }
        //LUCKY
        for(int i=0;i<all_lucky.length;i++){
            if(all_lucky[i].getPosition().getX()<size.getWidth()) {
                lucky.add(all_lucky[i]);
                all_lucky_index = i+1;
                index_lucky.add(i);
                frame.setLucky(lucky.get(i).getPosition(), i);
            }
            else{
                all_lucky_index = i;
                break;
            }
        }
        //TURTLES
        for(int i=0;i<all_turtles.length;i++){
            if(all_turtles[i].getPosition().getX()<size.getWidth()) {
                turtles.add(all_turtles[i]);
                frame.addTurtle(all_turtles[i].getWritable_position());
                all_turtles_index = i+1;
                index_turtles.add(i);
            }
            else{
                all_turtles_index = i;
                break;
            }
        }
        //CANNON
        for(int i=0;i<all_cannons.length;i++){
            if(all_cannons[i].getPosition().getX()<=size.getWidth()+all_cannons[i].getDistance()) {
                cannons.add(0,all_cannons[i]);
                all_cannons_index = i+1;
                frame.addCannon();
                frame.setCannons(new Position(cannons.get(i).getPosition().getX()+all_cannons[i].getDistance(),cannons.get(i).getPosition().getY()), i);
            }
            else{
                all_cannons_index = i;
                break;
            }
        }

    }

    public MapDirection colision(Object object) throws InterruptedException {
        /*
            Wykrywa kolizje z kazdym objetem
         */

        //BLACK BULLET
        if(object instanceof Mario){
            for(int i =0;i<blackBullets.size();i++)
                if(blackBullets.get(i).colision(object) != MapDirection.Center) {
                    mario.gotHitByEnemy();
                       return MapDirection.North;
                }
        }
        if(object instanceof FireBullets){
            for(int i=0;i<blackBullets.size();i++){
                if(blackBullets.get(i).colision(object) != MapDirection.Center){
                    blackBullets.get(i).gotHit();
                    if(!blackBullets.get(i).isAlive()){
                        frame.removeBlackBullet(i);
                        blackBullets.remove(i);
                        return MapDirection.East;
                    }
                    return blackBullets.get(i).colision(object);
                }
            }
        }
        //CANNON
        if(object instanceof Mario) {
            for (int i = 0; i < cannons.size();i++){
                if(cannons.get(i).colision(object)!= MapDirection.Center)
                    return cannons.get(i).colision(object);
            }
        }
        if(object instanceof FireBullets){
            for(int i=0;i<cannons.size();i++){
                if(cannons.get(i).colision(object) != MapDirection.Center){
                    cannons.get(i).gotHit();
                    if(!cannons.get(i).isAlive()) {
                        frame.removeCannon(i); ///>>>>>>>>>>>>>??????????
                        cannons.remove(i);
                        return MapDirection.East;
                    }
                    else
                        return cannons.get(i).colision(object);
                }
            }
        }
        if(object instanceof BlackBullet){
            for(int i = 0;i<cannons.size();i++){
                if(cannons.get(i).colision(object) != MapDirection.Center){
                    return MapDirection.East;
                }

            }
        }
        //TURTLES
        if(object instanceof Mario) {
            for (int i = 0; i < turtles.size();i++){
                if(turtles.get(i).colision(object)!=MapDirection.Center)
                    mario.gotHitByEnemy();
            }
        }
        if(object instanceof FireBullets){
            for(int i=0;i<turtles.size();i++){
                if(turtles.get(i).colision(object) != MapDirection.Center){
                    turtles.get(i).gotHit();
                    return turtles.get(i).colision(object);
                }
            }
        }
        //BREAKBLOCK
        for(int i=0;i<brake_blocks.size();i++){
            if((brake_blocks.get(i).colision(object) == MapDirection.South) && object instanceof Mario){  /////////???????????????????????????????
                brake_blocks.get(i).removes(index_brake_blocks.get(i));
                brake_blocks.remove(i);
                index_brake_blocks.remove(i);
                return MapDirection.South;
            }
            if(brake_blocks.get(i).colision(object) != MapDirection.Center) return brake_blocks.get(i).colision(object);
        }
        //BRICK
        for(int i=0;i<bricks.size();i++) {
            if (bricks.get(i).colision(object) != MapDirection.Center){
                if(bricks.get(i).isMoving()) {
                    damn = true;
                    dir = bricks.get(i).getDirection();
                }
                return bricks.get(i).colision(object);
            }
        }
        //GROUND
       for(int i=0;i<grounds.size();i++)
            if(grounds.get(i).colision(object)!=MapDirection.Center) return grounds.get(i).colision(object);
        //LUCKY
        for(int i=0;i<lucky.size();i++) {
            if(lucky.get(i).colision(object) == MapDirection.South){
                if(!lucky.get(i).isBonus_granted()) {
                    bonus = new Bonus(new Position(lucky.get(i).getPosition().getX(), lucky.get(i).getPosition().getY() - 60),new Position(lucky.get(i).getWritable_position().getX(), lucky.get(i).getWritable_position().getY() - 60), new Size(60, 60), this);
                    frame.setBonus(bonus.getWritable_position());    //to + 60 to rozmiar bonusu
                    frame.getBonus().setVisible(true);
                    lucky.get(i).setBonus_granted();
                }
            }
            if(lucky.get(i).colision(object) != MapDirection.Center) return lucky.get(i).colision(object);
        }
        if(bonus!=null && bonus.colision(object) != MapDirection.Center){
            frame.getBonus().setVisible(false);
            bonus.getBonus();
            bonus = null;
        }
        return MapDirection.Center;
    }

    private void moveBulletsByMap() throws InterruptedException {  //move przez poruszanie sie mapy
        for(int i = 0;i<fireBullets.size();i++){
            fireBullets.get(i).subWritPos();
            frame.setFireBullets(fireBullets.get(i).getPosition(),i); //!!!!!!!!!zmiana pozycji na ekranie
        }
        for(int i = 0;i<blackBullets.size();i++){
            blackBullets.get(i).subWritPos();
            frame.setBlackBullets(blackBullets.get(i).getPosition(),i);
        }
    }

    public void moveBullets() throws InterruptedException {  //to jest juz niezaleznie przez mape
        //FIRE BULLETS
        for(int i = 0;i<fireBullets.size();i++){
            fireBullets.get(i).shiftPosition();
            frame.setFireBullets(fireBullets.get(i).getPosition(),i);
            if(fireBullets.get(i).getPosition().getX()<0-fireBullets.get(i).getSize().getWidth() || fireBullets.get(i).getPosition().getX()>size.getWidth() || fireBullets.get(i).getPosition().getY()>size.getHeight()) {
                fireBullets.remove(i);
                frame.removeFireBullets(i);
            }
            else if(colision(fireBullets.get(i))!= MapDirection.Center) {
                frame.getFire_bullets().get(i).setVisible(false);
                fireBullets.remove(i);
                frame.removeFireBullets(i);
            }
        }
        //BLACK BULLETS
        System.out.printf("");
        for(int i = 0;i<blackBullets.size();i++){
            blackBullets.get(i).shiftPosition();
            frame.setBlackBullets(blackBullets.get(i).getPosition(),i);
            if(blackBullets.get(i).getPosition().getX()<0-blackBullets.get(i).getSize().getWidth()) {
                blackBullets.remove(i);
                frame.removeBlackBullet(i);
            }
            else if(colision(blackBullets.get(i))!= MapDirection.Center) {
                blackBullets.remove(i);
                frame.removeBlackBullet(i);
            }
        }
    }

    public Mario getMario() {
        return mario;
    }

    public void setFrame(JFrames frame){
        /*
            Ustawia frame dla wszystkich objektow tworzonych przez konstruktor WorldMap
         */

        this.frame = frame;
        mario.setFrame(frame);
    }

    public List<BlackBullet> getBlackBullets() {
        return blackBullets;
    }

    public JFrames getFrame() {
        return frame;
    }

    public void setSize(int height, int width){
        size = new Size(height,width);
    }

    public List<FireBullets> getFireBullets() {
        return fireBullets;
    }

    private void addObjectsOnMap() {
        /*
            !!!!!!!TRZEBA DODAWAC POSORTOWANE PO X ROSNACO
            Dodaje objekty na mape.
            Tu mozna tworzyc mape wlasna.      (Dorobic generator z pliku)
            Size w konstruktorze to rozmiar obrazka
         */

        //KLOCKI
        all_bricks[0] = new Brick(new Position(350, 646), new Size(60, 60), this);
        all_bricks[1] = new Brick(new Position(590, 586), new Size(60, 60), this);
        all_bricks[2] = new Brick(new Position(590, 526), new Size(60, 60), this);
        all_bricks[3] = new Brick(new Position(590, 466), new Size(60, 60), this);
        all_bricks[4] = new Brick(new Position(590, 406), new Size(60, 60), this);
        all_bricks[5] = new Brick(new Position(590, 346), new Size(60, 60), this);  ///zmienic y na 346
        all_bricks[6] = new Brick(new Position(650, 586), new Size(60, 60), this);
        all_bricks[7] = new Brick(new Position(710, 586), new Size(60, 60), this);
        all_bricks[8] = new Brick(new Position(770, 586), new Size(60, 60), this);
        all_bricks[10] = new Brick(new Position(890, 586), new Size(60, 60), this);
        all_bricks[9] = new Brick(new Position(830, 766), new Size(60, 60), this);
        all_bricks[15] = new Brick(new Position(950, 586), new Size(60, 60), this);
        all_bricks[11] = new Brick(new Position(950, 646), new Size(60, 60), this);
        all_bricks[12] = new Brick(new Position(950, 706), new Size(60, 60), this);
        all_bricks[13] = new Brick(new Position(950, 766), new Size(60, 60), this);
        all_bricks[14] = new Brick(new Position(950, 826), new Size(60, 60), this);

        all_bricks[16] = new Brick(new Position(1210, 706), new Size(60, 60), this,1160,1550);
        mBIndex.add(16);
        movingBricks.add(all_bricks[16]);

        all_bricks[17] = new Brick(new Position(1640, 706), new Size(60, 60), this);
        all_bricks[18] = new Brick(new Position(1700, 706), new Size(60, 60), this);

        all_bricks[19] = new Brick(new Position(1850, 706), new Size(60, 60), this,1830,2270);


        all_bricks[20] = new Brick(new Position(2530, 440), new Size(60, 60), this);
        all_bricks[21] = new Brick(new Position(2530, 720), new Size(60, 60), this);

        all_bricks[22] = new Brick(new Position(2770, 500), new Size(60, 60), this);
        all_bricks[23] = new Brick(new Position(2770, 260), new Size(60, 60), this);
        all_bricks[24] = new Brick(new Position(2830, 500), new Size(60, 60), this);
        all_bricks[25] = new Brick(new Position(2830, 260), new Size(60, 60), this);
        all_bricks[26] = new Brick(new Position(2890, 500), new Size(60, 60), this);
        all_bricks[27] = new Brick(new Position(2890, 260), new Size(60, 60), this);
        all_bricks[28] = new Brick(new Position(2950, 500), new Size(60, 60), this);
        all_bricks[29] = new Brick(new Position(2950, 260), new Size(60, 60), this);
        all_bricks[30] = new Brick(new Position(3010, 500), new Size(60, 60), this);
        all_bricks[31] = new Brick(new Position(3010, 260), new Size(60, 60), this);
        all_bricks[32] = new Brick(new Position(3070, 500), new Size(60, 60), this);
        all_bricks[33] = new Brick(new Position(3130, 500), new Size(60, 60), this);
        all_bricks[34] = new Brick(new Position(3190, 500), new Size(60, 60), this);

        all_bricks[35] = new Brick(new Position(3190, 706), new Size(60, 60), this);
        all_bricks[36] = new Brick(new Position(3190, 826), new Size(60, 60), this);
        all_bricks[37] = new Brick(new Position(3190, 560), new Size(60, 60), this);

        all_bricks[38] = new Brick(new Position(3190, 260), new Size(60, 60), this);
        all_bricks[39] = new Brick(new Position(3250, 500), new Size(60, 60), this);
        all_bricks[40] = new Brick(new Position(3250, 260), new Size(60, 60), this);
        all_bricks[41] = new Brick(new Position(3250, 320), new Size(60, 60), this);
        all_bricks[42] = new Brick(new Position(3250, 440), new Size(60, 60), this);
        all_bricks[43] = new Brick(new Position(3550, 740), new Size(60, 60), this);


        //GROUNDS
        all_grounds[0] = new Ground(new Position(0, 886), new Size(67, 400), this);
        all_grounds[1] = new Ground(new Position(350, 886), new Size(67, 400), this);
        all_grounds[2] = new Ground(new Position(700, 886), new Size(67, 400), this);
        all_grounds[3] = new Ground(new Position(790, 886), new Size(67, 400), this);
        all_grounds[4] = new Ground(new Position(2290, 886), new Size(67, 400), this);
        all_grounds[5] = new Ground(new Position(2530, 886), new Size(67, 400), this);
        all_grounds[6] = new Ground(new Position(2910, 886), new Size(67, 400), this);

        //BREAKBLOCKS
        all_break_blocks[0] = new BreakBlock(new Position(830, 586), new Size(60, 60), this);
        all_break_blocks[1] = new BreakBlock(new Position(2770, 200), new Size(60, 60), this);//200
        all_break_blocks[2] = new BreakBlock(new Position(2770, 140), new Size(60, 60), this);//140

        //LUCKYBLOCKS
        all_lucky[0] = new LuckyBlock(new Position(2890, 100), new Size(60, 60), this);
        //2830

        //TURTLES   Mozna dodac na koncu x i y to jest zakres ruchu zolwia
        all_turtles[0] = new Turtle(new Position(590,831),new Size(55,37),MapDirection.East,this,1,590,950);
        //all_turtles[1] = new Turtle(new Position(2770,445),new Size(55,37),MapDirection.East,this,1,2770,3250);
        all_turtles[1] = new Turtle(new Position(2700,831),new Size(55,37),MapDirection.East,this,1,2700,3190);
        //445

        //CANNONS
        all_cannons[0] = new Cannon(new Position(3190,646),new Size(60,60),MapDirection.West,this,1,400,500);
        all_cannons[1] = new Cannon(new Position(3190,766),new Size(60,60),MapDirection.West,this,1,550,500);
        //all_cannons[2] = new Cannon(new Position(3250,380),new Size(60,60),MapDirection.West,this,1,550,500);
    }
}

