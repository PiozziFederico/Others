package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public static boolean upPressed, downPressed, leftPressed, rightPressed, pausePressed;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        switch(e.getKeyCode()) {
            case KeyEvent.VK_W: upPressed = true; 
            break;
            case KeyEvent.VK_A: leftPressed = true;
            break;
            case KeyEvent.VK_S: downPressed = true;
            break;
            case KeyEvent.VK_D: rightPressed = true;
            break;
            case KeyEvent.VK_SPACE: 
                if(pausePressed) pausePressed = false;
                else pausePressed = true; 
            break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    
}
