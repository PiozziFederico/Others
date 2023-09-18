package mino;

import java.awt.Color;
import java.awt.Graphics2D;

import main.KeyHandler;
import main.PlayManager;

public class Mino {
    
    public Block b[] = new Block[4];
    public Block tempB[] = new Block[4];
    int autoDropCounter = 0;
    public int direction = 1; // 4 directions (1/2/3/4)
    boolean leftCollision, rightCollision, bottomCollision;
    public boolean active = true;
    public boolean deactivating;
    int deactivatingCounter = 0;

    public void create(Color c) {
        for(int i = 0; i < 4; i++) {
            b[i] = new Block(c);
            tempB[i] = new Block(c);
        }
    }

    public void setXY(int x, int y) {}
    public void updateXY(int direction) {

        checkRotationCollision();

        if(!leftCollision && !rightCollision && !bottomCollision) {

            this.direction = direction;

            for(int i = 0; i < 4; i++) {
                b[i].x = tempB[i].x;
                b[i].y = tempB[i].y;
            }
        }
    }
    public void getDirection1() {}
    public void getDirection2() {}
    public void getDirection3() {}
    public void getDirection4() {}
    public void checkMovementCollision() {

        leftCollision= false;
        rightCollision = false; 
        bottomCollision = false;

        checkStaticBlockCollision();

        // left wall
        for(int i = 0; i < b.length; i++) {
            if(b[i].x == PlayManager.left_x) leftCollision = true;
        }
        // right wall
        for(int i = 0; i < b.length; i++) {
            if(b[i].x + Block.SIZE == PlayManager.right_x) rightCollision = true;
        }
        // bottom floor
        for(int i = 0; i < b.length; i++) {
            if(b[i].y + Block.SIZE == PlayManager.bottom_y) bottomCollision = true;
        }
    }
    public void checkRotationCollision() {
        leftCollision= false;
        rightCollision = false; 
        bottomCollision = false;

        checkStaticBlockCollision();

        // left wall
        for(int i = 0; i < b.length; i++) {
            if(tempB[i].x < PlayManager.left_x) leftCollision = true;
        }
        // right wall
        for(int i = 0; i < b.length; i++) {
            if(tempB[i].x + Block.SIZE > PlayManager.right_x) rightCollision = true;
        }
        // bottom floor
        for(int i = 0; i < b.length; i++) {
            if(tempB[i].y + Block.SIZE > PlayManager.bottom_y) bottomCollision = true;
        }
    }
    private void checkStaticBlockCollision() {

        for(int i = 0; i < PlayManager.staticBlocks.size(); i++) {

            int targetX = PlayManager.staticBlocks.get(i).x;
            int targetY = PlayManager.staticBlocks.get(i).y;

            // down
            for(int ii = 0; ii < b.length; ii++) {
                if(b[ii].y + Block.SIZE == targetY && b[ii].x == targetX){
                    bottomCollision = true;
                }
            }
            // left
            for(int ii = 0; ii < b.length; ii++) {
                if(b[ii].y == targetY && b[ii].x - Block.SIZE == targetX){
                    leftCollision = true;
                }
            }
            //right
            for(int ii = 0; ii < b.length; ii++) {
                if(b[ii].y == targetY && b[ii].x + Block.SIZE == targetX){
                    rightCollision = true;
                }
            }
        }
    }
    public void update() {

        if(deactivating) {
            deactivating();
        }

        // move mino
        // UP
        if(KeyHandler.upPressed) {

            KeyHandler.upPressed = false;

            switch(direction) {
                case 1: getDirection2();
                break;
                case 2: getDirection3();
                break;
                case 3: getDirection4();
                break;
                case 4: getDirection1();
                break;
            }
        }

        checkMovementCollision();

        // DOWN
        if(KeyHandler.downPressed) {

            if(!bottomCollision) {
                autoDropCounter = 0;
            
                for(int i = 0; i < 4; i++) {
                    b[i].y += Block.SIZE;
                }
            }
            KeyHandler.downPressed = false;
        }
        // LEFT
        if(KeyHandler.leftPressed) {

            if(!leftCollision){
                for(int i = 0; i < 4; i++) {
                    b[i].x -= Block.SIZE;
                }
            }
            KeyHandler.leftPressed = false;
        }
        // RIGHT
        if(KeyHandler.rightPressed) {

            if(!rightCollision) {
                for(int i = 0; i < 4; i++) {
                    b[i].x += Block.SIZE;
                }
            }
            KeyHandler.rightPressed = false;
        }

        if(bottomCollision) {
            deactivating = true;
        }
        else {
            autoDropCounter++;
            if(autoDropCounter == PlayManager.dropInterval) {
                // mino goes down
                for(int i = 0; i < 4; i++) {
                    b[i].y += Block.SIZE;
                }
                autoDropCounter = 0;
            }
        }
    }
    private void deactivating() {

        deactivatingCounter++;

        // wait 45 frame
        if(deactivatingCounter == 45) {
            deactivatingCounter = 0;
            checkMovementCollision(); // is still hitting?

            // if still hittinh set active false
            if(bottomCollision) {
                active = false;
            }
        }
    }
    public void draw(Graphics2D g2) {

        int margin = 2;
        int size = Block.SIZE - (margin*2);
        g2.setColor(b[0].c);

        for(int i = 0; i < 4; i++) {
            g2.fillRect(b[i].x + margin, b[i].y + margin, size, size);
        }
    }
}
