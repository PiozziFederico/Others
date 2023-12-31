package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
    
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;

    public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    public final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    // FPS
    final int FPS = 60;

    // SYSTEM
    TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();

    /**
     * A thread is a thread of execution in a program. 
     * The Java Virtual Machine allows an application to have multiple threads of execution running concurrently.
     * Every thread has a priority. 
     * Threads with higher priority are executed in preference to threads with lower priority.
     */
    Thread gameThread;

    // ENTITY AND OBJECT
    public Player player = new Player(this, keyH);
    public Entity obj[] = new Entity[10]; // we can display ten objects at the same time
    public Entity npc[] = new Entity[10];
    public Entity monster[] = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();

    // GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;




    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setupGame() {

        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        gameState = titleState;
    }

    public void startGameThread() {
        
        gameThread = new Thread(this);
        gameThread.start(); // automatically call the run method

    }

    public void run() { // from Runnable

        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        long timer = 0;
        int drawCount = 0;

        while(gameThread != null ){ 
            
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if(delta >= 1) {
                update();
                repaint(); // call paintComponent method
                delta--;
                drawCount++;
            }

            if( timer >= 1000000000){
                System.out.println("FPS:"+ drawCount);
                drawCount = 0;
                timer = 0;
            }

        }

    }

    public void update() {

        if(gameState == playState) {
            // PLAYER
            player.update();
            // NPC
            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    npc[i].update();
                }
            }
            // MONSTER
            for(int i = 0; i < monster.length; i++){
                if(monster[i] != null) {
                    monster[i].update();
                }
            }
        } else if(gameState == pauseState){
            // nothing
        }
        

    }

    public void paintComponent( Graphics g ){

        super.paintComponent(g);

        /*
         * Graphics2D class extends the Graphic class to provide more sophisticated control over geometry, 
         * coordinate transformations, color management, and text layout
         */
        Graphics2D g2 = (Graphics2D)g;

        // DEBUG
/*         long drawStart = 0;
        if(keyH.checkDrawTime) {
            drawStart = System.nanoTime();
        } */

        // TITLE SCREEN
        if(gameState == titleState) {
            ui.draw(g2);
        }
        // OTHERS
        else {
            // TILE
            tileM.draw(g2);

            // ADD ENTITIES TO THE LIST
            entityList.add(player);
            
            for(int i = 0; i < npc.length; i++) {
                if(npc[i] != null) {
                    entityList.add(npc[i]);
                }
            }

            for(int i = 0; i < obj.length; i++) {
                if(obj[i] != null) {
                    entityList.add(obj[i]);
                }
            }

            for(int i = 0; i < monster.length; i++) {
                if(monster[i] != null) {
                    entityList.add(monster[i]);
                }
            }
            // SORT BY WORLDY
            Collections.sort(entityList, new Comparator<Entity>() {

                @Override
                public int compare(Entity e1, Entity e2) {
                    
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
                
            });

            // DRAW ENTITIES
            for(int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            entityList.clear();

            // UI
            ui.draw(g2);
        }

        

        // DEBUG
/*         if(keyH.checkDrawTime) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time: " + passed, 10, 400);
            System.out.println("Draw Time: "+passed);
        } */

        
        g2.dispose(); // dispose of this graphics context and release any system resources that it is using (good practice to save some memory)
    }
    
    public void playMusic(int i) {

        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {

        music.stop();
    }
    public void playSE(int i) {

        se.setFile(i);
        se.play();
    }
}
