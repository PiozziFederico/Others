package object;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Boots extends SuperObject {

    public OBJ_Boots() {
        name = "Boots";
        
        // load image
        try {

            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/boots.png"));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
