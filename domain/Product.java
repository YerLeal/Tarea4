package domain;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

public class Product extends Character {

    private SynchronizedBuffer synchronizedBuffer;

    public Product(SynchronizedBuffer shared, int x, int y) throws FileNotFoundException {
        super(x, y);
        this.synchronizedBuffer = shared;
        setSprite();
    } // constructor

    public void setSprite() throws FileNotFoundException {
        ArrayList<Image> sprite = super.getSprite();
        sprite.add(new Image(new FileInputStream("src/assets/producto.png")));
    } // setSprite

    @Override
    public void run() {
        Image image = super.getSprite().get(0);
        while (true) {
            try {
                sleep(1);
                if (synchronizedBuffer.isPaint()) {
                    super.setImage(image);
                    setX(557);
                } else {
                    super.setImage(image);
                    setX(1500);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Product.class.getName()).log(Level.SEVERE, null, ex);
            }
        } // while
    } //  run

} // fin de la clase
