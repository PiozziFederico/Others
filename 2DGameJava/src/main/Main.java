package main;

/**
 * JFrame is a class of the javax.
 * swing package that is extended by java. awt. frame. 
 * This is the top-level window, with border and a title bar
 */
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args){

        JFrame window = new JFrame();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("2DAdventure");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }

}
