package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
    
    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3;

    final int tileSize = originalTileSize * scale; // 48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;

    final int screenWidth = tileSize * maxScreenCol; // 768 pixels
    final int screenHeight = tileSize * maxScreenRow; // 576 pixels

    /**
     * A thread is a thread of execution in a program. 
     * The Java Virtual Machine allows an application to have multiple threads of execution running concurrently.
     * Every thread has a priority. 
     * Threads with higher priority are executed in preference to threads with lower priority.
     */
    Thread gameThread;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
    }

    public void startGameThread() {
        
        gameThread = new Thread(this);
        gameThread.start(); // automatically call the run method

    }

    public void run() { // from Runnable

        while(gameThread != null ){ 
            
            //System.out.println("loop");

            update();

            repaint(); // call paintComponent method
        }

    }

    public void update() {

    }

    public void paintComponent( Graphics g ){

        super.paintComponent(g);

        /*
         * Graphics2D class extends the Graphic class to provide more sophisticated control over geometry, 
         * coordinate transformations, color management, and text layout
         */
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.white);

        g2.fillRect(100, 100, tileSize, tileSize);

        
        g2.dispose(); // dispose of this graphics context and release any system resources that it is using (good practice to save some memory)
    }


}
